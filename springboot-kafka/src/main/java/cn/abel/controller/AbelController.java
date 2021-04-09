package cn.abel.controller;

import cn.abel.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyb
 * @time 2020/6/4
 */
@RestController
@RequestMapping("/kafka")
public class AbelController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public ResponseEntity sendMsg(String msg) {
        kafkaProducerService.sendMsg(msg);
        return ResponseEntity.ok("ok");
    }
}
