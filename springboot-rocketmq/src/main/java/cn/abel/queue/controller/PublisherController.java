package cn.abel.queue.controller;

import cn.abel.queue.config.JmsConfig;
import cn.abel.queue.service.ProducerService;
import cn.abel.queue.service.ReceiverService;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyb
 * @time 2019/8/13
 */

@RestController
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    private ProducerService producerService;
    @Autowired
    private ReceiverService receiverService;

    private List<String> mesList;

    /**
     * 初始化消息
     */
    public PublisherController() {
        mesList = new ArrayList<>();
        mesList.add("小小");
        mesList.add("爸爸");
        mesList.add("妈妈");
        mesList.add("爷爷");
        mesList.add("奶奶");

    }

    @RequestMapping("/text/rocketmq")
    public Object callback() throws Exception {
        //总共发送五次消息
        for (String s : mesList) {
            //创建生产信息
            Message message = new Message(JmsConfig.TOPIC, "testtag", ("小小一家人的称谓:" + s).getBytes());
            //发送
            SendResult sendResult = producerService.getProducer().send(message);
            System.out.println(String.format("输出生产者信息={ %s }",sendResult));
        }
        return "成功";
    }
}
