package cn.abel.queue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @author yyb
 * @time 2019/8/13
 */

@Service
public class ProducerService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String sendMessage(String name) {
        try {
            redisTemplate.opsForList().leftPush("quere", name);
            return "消息发送成功了";

        } catch (Exception e) {
            e.printStackTrace();
            return "消息发送失败了";
        }
    }

}