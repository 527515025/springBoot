package cn.abel.queue.controller;

import cn.abel.queue.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyb
 * @time 2020/6/29
 */
@RestController
@RequestMapping("/rocket")
public class ControllerTest {
    @Autowired
    private ProducerService producerService;

//    @RequestMapping(value = "/send", method = RequestMethod.POST)
//    private String send(String msg) {
//        producerService.sendMessage("asdfsd");
//        return null;
//    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    private String send() {
        producerService.sendMessage("asdfsd");
        return null;
    }
}
