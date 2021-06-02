package com.hhj.seckill.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**暴力秒杀地址的信息
 * @Author virtual
 * @Date 2021/6/2 16:11
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exposer {
    /**
     * 是否开启秒杀
     */
    private boolean exposed;

    /**
     * 对秒杀地址加密措施 防止秒杀接口提前暴露
     */
    private String md5;

    /**
     * id为seckillId的商品的秒杀地址
     */
    private long seckillId;

    /**
     * 系统当前时间（毫秒）
     */
    private long now;

    /**
     * 秒杀的开启时间
     */
    private long start;

    /**
     * 秒杀结束时间
     */
    private long end;
}
