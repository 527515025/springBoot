package cn.abel.queue.config;

import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author yyb
 * @time 2020/3/25
 */
@Component
public class ALiProducerClient {
    @Autowired
    private ALiMqConfig aLiMqConfig;

    @Bean(name = "producer", initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean build() {
        ProducerBean bean = new ProducerBean();
        bean.setProperties(aLiMqConfig.getMqProperties());
        return bean;
    }

    @Autowired
    public ALiProducerClient(ALiMqConfig aLiMqConfig) {
        this.aLiMqConfig = aLiMqConfig;
    }
}