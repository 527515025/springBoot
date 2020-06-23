package cn.abel.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费消息
 * Created by yyb on 2018/12/10.
 */

@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "${test.kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void processMessage(List<ConsumerRecord<?, ?>> records) {

        for (ConsumerRecord<?, ?> record : records) {
            String message = (String) record.value();
            System.out.println("receive message： " + message);
        }
    }
}
