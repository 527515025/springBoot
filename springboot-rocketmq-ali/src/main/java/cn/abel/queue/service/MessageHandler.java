package cn.abel.queue.service;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author yyb
 * @time 2020/3/25
 */
@Component
public class MessageHandler implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        String msgStr = new String(message.getBody(), StandardCharsets.UTF_8);
        //接收到消息
        logger.info("Receive message msgId:{} retryTimes:{} body:{}", message.getMsgID(),
                message.getReconsumeTimes(), msgStr);
        //提交
        return Action.CommitMessage;
    }
}
