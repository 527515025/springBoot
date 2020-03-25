package cn.abel.queue.config;

import cn.abel.queue.service.MessageHandler;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yyb
 * @time 2020/3/25
 */

@Configuration
public class ALiConsumerClient {
    private static final Logger logger = LoggerFactory.getLogger(ALiConsumerClient.class);
    /**
     * topic tag。
     */
    private static final String TOPIC_TAG = "*";
    /**
     * 消费者线程数。
     */
    private static final int CONSUMER_THREAD_COUNT = 10;
    /**
     * 重新消费次数。
     */
    private static final int RECONSUME_TIMES = 2;

    private MessageHandler handler;

    @Value("${ali.rocket.mq.access-key}")
    private String accessKey;
    @Value("${ali.rocket.mq.secret-key}")
    private String secretKey;
    @Value("${ali.rocket.mq.name-srv-addr}")
    private String nameSrvAddr;
    @Value("${ali.rocket.mq.test-topic}")
    private String topic;
    @Value("${ali.rocket.mq.test-group-id}")
    private String group;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, nameSrvAddr);
        properties.setProperty(PropertyKeyConst.GROUP_ID, group);
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, String.valueOf(CONSUMER_THREAD_COUNT));
        properties.setProperty(PropertyKeyConst.MaxReconsumeTimes, String.valueOf(RECONSUME_TIMES));
        consumerBean.setProperties(properties);

        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
        Subscription subscription = new Subscription();
        subscription.setTopic(topic);
        subscription.setExpression(TOPIC_TAG);
        subscriptionTable.put(subscription, handler);
        consumerBean.setSubscriptionTable(subscriptionTable);
        logger.info("初始化rocketMq完成 config:{}");
        return consumerBean;
    }

    @Autowired
    public ALiConsumerClient(MessageHandler handler) {
        this.handler = handler;
    }
}
