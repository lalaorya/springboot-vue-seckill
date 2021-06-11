package com.hhj.seckill.mq;

import cn.hutool.core.io.FileUtil;
import com.hhj.seckill.common.excetion.MyException;
import com.hhj.seckill.config.RabbitMqConfig;
import com.hhj.seckill.service.SecKillService;
import com.hhj.seckill.service.SecOrderService;
import com.hhj.seckill.vo.SecKillOrder;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;;import java.io.File;
import java.io.IOException;


/**
 * @Author virtual
 * @Date 2021/6/5 23:15
 * @Version 1.0
 */
@Component
@Slf4j
public class MqConsumer {
    @Autowired
    SecOrderService secOrderService;

    @Autowired
    SecKillService secKillService;

    /**
     * 接收秒杀订单信息
     * 手动签收
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitMqConfig.SEC_QUEUE_ORDER)
    public void receiveOrder(@Payload SecKillOrder secKillOrder, Message message, Channel channel) throws IOException {
        System.out.println(message);
        try {
            secKillService.seckill(secKillOrder);
//            message.getMessageProperties().getPublishSequenceNumber();
            log.info("队列请求号{}秒杀操作成功",message.getMessageProperties().getPublishSequenceNumber());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        }catch (Exception e){
            log.info("生成订单失败，拒绝签收，给爷重新发");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,true);
        }

    }
}
