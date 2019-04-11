package cn.abel.service.prehandle;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yyb on 2018/12/10.
 */

@Component
public class KafkaConsumerService {

    @Autowired
    private SplitService splitService;

    @KafkaListener(topics = "${log.statistical.kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void processMessage(List<ConsumerRecord<?, ?>> records) {

        for (ConsumerRecord<?, ?> record : records) {
            String message = (String) record.value();
            splitService.saveAndSplitLog(message);
        }
    }
}
