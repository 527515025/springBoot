package cn.abel.queue.service;

import cn.abel.queue.config.ALiMqConfig;
import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;


/**
 * @author yyb
 * @time 2019/8/13
 */

@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private ALiMqConfig aLiMqConfig;
    @Autowired
    private ProducerBean producer;


    /**
     * 调用此方法 发送消息，o 为自定义的消息体
     */
    public void sendMessage(Object o) {
        Message message = new Message(aLiMqConfig.getStockTopic(), "test", JSON.toJSON(o).toString().getBytes(StandardCharsets.UTF_8));
        //向mq发送消息
        sendAsync(ProducerBean::isStarted, producer, message);
    }

    /**
     * 向阿里云rocket mq发送消息。
     *
     * @param predicate 断言
     * @param bean      发送消息bean
     * @param message   需要发送的消息
     */
    private static void sendAsync(Predicate<ProducerBean> predicate, ProducerBean bean, Message message) {
        if (predicate.test(bean)) {
            bean.sendAsync(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    logger.info("向mq推送库存消息成功，消息是：{}", sendResult.toString());
                }
                @Override
                public void onException(OnExceptionContext e) {
                    logger.error("向mq推送库存消息失败，消息id 为 {} 错误是：{}", e.getMessageId(), e.getException().getMessage());
                }
            });
        } else {
            logger.error("mq库存生产端启动失败！！！消息是：{}", message.toString());
        }
    }

}