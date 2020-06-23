package cn.abel.service;

import cn.abel.config.KafkaTemplate2Client;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yyb
 * @time 2020/6/4
 */
@Component
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    /**
     * 配置的第二个来源
     */
    @Autowired
    @Qualifier(KafkaTemplate2Client.NAME)
    private KafkaTemplate producer2;


    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value(value = "${test.kafka.topic}")
    private String topic;

    public void sendMsg(String msg) {
        logger.info("kafka send msg {}", JSON.toJSONString(msg));
        kafkaTemplate.send(topic, JSON.toJSONString(msg));
        producer2.send(topic, JSON.toJSONString("producer2: " + msg));
    }
}
