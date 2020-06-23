package cn.abel.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 多个 Producer 时可以通过这样配置，单个producer 时只需要在 配置文件中配置  spring.kafka.bootstrap-servers 参数即可自动装配 Producer
 * @author yyb
 * @time 2020/6/23
 */
@Configuration
public class KafkaTemplate2Client {
    public static final String NAME = "KafkaTemplate2Client";

    /**
     * kafka地址
     */
    @Value("${test.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name = NAME)
    public KafkaTemplate getRestClient() {
        ProducerFactory producerFactory = new DefaultKafkaProducerFactory(producerConfigs());
        KafkaTemplate kafkaTemplate = new KafkaTemplate(producerFactory);
        return kafkaTemplate;
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

}
