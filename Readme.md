### 未优化之前QPS（纯MySQL）：

<img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210603223606621.png" alt="image-20210603223606621" style="zoom:200%;" />

秒杀操作一共要发两个请求，第一个为请求真正秒杀接口，第二个为秒杀

一万个用户抢一千个商品，平均qps为三百多。

- 好消息是没有超卖，业务逻辑没有问题

- 坏消息是QPS太太太低了，商品没被抢光前第二个请求的qps只有四十多。平均请求要12秒，但是http请求6s就超时了

分析原因：

- 一方面是没有使用缓存以及消息队列实现异步下单、削峰填谷

- 另一方面秒杀操作是一个事务，先减库存后写订单

  “当前读”的事务操作获取的是最新记录，需要对记录加行级锁，**行级锁的释放要等待事务提交才会释放**。但是控制事务提交回滚并非MySQL服务端，而是由Java客户端控制的。因此会产生很多不必要的网络延时和GC时延。

  因此我们优化的目的就是减少事务操作加锁的时间，降低行级锁到commit的时间。

  - 优化思路一：

    因为Mysql锁协议是二段锁协议，要用到的时候才加锁，但是要等待事务提交锁才释放。因此并发度高的操做要放在事务的最后面，以此来降低锁的操纵。

    因此我们把原来的先降低库存后写订单调整为先写订单后减库存（减库存并发度 >> 写订单，因为减库存针对同一行记录，同一个行级锁。写订单并发度没有那么高）

  - 优化思路二//TODO

    把控制事务提交回滚交给MySQL来做，消除网络延迟和GC对TPS的影响。具体实现是通过MySQL的存储过程


### 优化一：MySQL优化

- 因为秒杀操作有一条高频SQL是`update secgood set stock=stock-1 where id=#{id} and stock>0`。因为为`ID`和`STOCK`这两个字段建立联合索引，可以实现索引下推，减少需要回表的次数

- SQL语句统一不使用`select * from ...`，要哪些字段显示的写清楚。这样做有以下几个原因：一是`select *`会取出此行所有字段，但是有时候我们仅仅需要其中几个，这无疑是对服务器资源的浪费。二是`select *`不会走索引，使用全表扫描，效率很低。三是便于后期维护

- druid数据库连接池参数的调整

  ```YAML
      druid:
        max-active: 100
  #      初始化连接池大小
        initial-size: 10
        max-wait: 60000
  #      最小连接数
        min-idle: 10
  #      间隔多久进行一次检测来关闭空闲的连接
        time-between-eviction-runs-millis: 4000
  #      配置一个连接在连接池中最小生存时间
        min-evictable-idle-time-millis: 600000
        max-evictable-idle-time-millis: 900000
        test-while-idle: true
        test-on-borrow: false
        test-on-return: false
        pool-prepared-statements: true
        max-open-prepared-statements: 20
        validation-query: "select 1"
  ```

> explain语句可以分析此sql的具体执行流程
>
> `explain update secgood set stock=stock-1 where id=23 and stock>0;`
>
> ![image-20210604164815557](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210604164815557.png)
>
> 关键看用了哪些索引和type，走的是全表查询还是索引查询等等

优化后TPS有所提升，但不明显，也有可能和我的测试方法，机器配置有关

#### 联合索引的死锁问题//TODO

secgood表上有一个（id，stock）联合索引，这个索引引发了死锁问题。当时没解决，但是后续莫名其妙又正常了

### 优化二：Redis缓存优化

#### redis最佳实践:`redisUtil + redisTemlete`


这部分实现不多说，就是把高并发又不需要实时更新的数据（如秒杀商品的exposer信息，商品库存）放进缓存。

- 使用 redis判断并减库存操作 。因为这是两个命令`get`和`desc`，redis 不保证原子性。为了防止超卖问题，使用lua脚本解决，保证两条命令也保证原子性。

  ```java
  long stock = redisUtil.luaStock(SEC_KILL_STOCK + secKillOrder.getSecId());
  log.info("当前库存为{}",stock);
  
  if(stock < 0)    throw new MyException(ErrorEnum.STOCK_ZERT);
  ```

  ```JAVA
      private final String LUA="if redis.call(\"get\",KEYS[1] )> ARGV[1] then\n" +
              "\treturn redis.call(\"decr\",KEYS[1])\n" +
              "else\n" +
              "\treturn 0\n" +
              "end";    
  public long luaStock(String key){
          DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(LUA,Long.class);
          Object execute = redisTemplate.execute(longDefaultRedisScript, Collections.singletonList(key),0);
          return (Long)execute;
      }
  ```

- 使用redis判断是否重复消费。这里的实现借鉴了redis实现分布式锁的思想，使用`setnx`命令。redis中不存在某个key才能把订单放进消息队列，避免了重复消费。

  ```JAVA
          StringBuffer append = new StringBuffer().append(SEC_kill_USER + secKillOrder.getSecId()).append(":").append(secKillOrder.getUserId());
          if(! redisUtil.setnx(append.toString(), 1, 2 )){
              // 重复购买了
              throw new MyException(ErrorEnum.REPEAT);
          };
  ```
  

TPS进一步提升

![image-20210605212742273](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210605212742273.png)

### 优化三：消息队列实现异步、削峰填谷

消息队列的好处有：

- 异步：秒杀订单信息入队即可返回，不需要等待订单写入数据库
- 削峰填谷：把高并发请求放进消息队列，消费者慢慢消费，提高并发能力。另外，可以控制消息队列的长度，没入对的直接返回秒杀失败，进一步提高并发能力

但是使用消息队列有可能会出现消息可靠性无法保证，出现订单消息丢失的情况，从而导致少卖。

RabbitMQ保证消息可靠性的机制有：生产者confirm | 生产者return | 消费者ack、Nack 

**// TODO 消息可靠性这部分没有做好，目前可能会出现少卖的情况**

目前关于消息可靠性实现的两个idea

- 如果发现消息未投递成功，回滚redis中的秒杀商品库存，原订单作废
- 如果发现消息未投递成功，重新发送消息

优化完TPS明显提升：

![image-20210611101941858](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210611101941858.png)

### 优化四：前端接口限流

- 通过图形验证码、答题方式进行限流
- 针对恶意防刷，可以通过令牌桶算法或者redis缓存，规定每5秒内一个用户只能发一个秒杀请求，其他秒杀请求会被自动丢弃。

### TODO

- 废弃10分钟内未付款的订单，回滚库存，避免恶意用户下订单但不付款。通过延迟队列实现
- 秒杀逻辑有待优化，目前的逻辑是一个用户只能发一个秒杀请求，如果成功入队就保证一定能成功生成订单。

### 后续优化想法

### 前端

- 静态页面采用CDN加速
- 秒杀按钮加个图形验证码，进行前端限流

### 后端

- 优化MySQL表结构，分库分表提高并发度
- 服务器集群，nginx负载均衡
- Redis集群，主从分离





