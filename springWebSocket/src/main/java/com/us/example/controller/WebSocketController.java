package com.us.example.controller;



import com.us.example.bean.Message;
import com.us.example.bean.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by yangyibo on 16/12/29.
 *
 */
@Controller
public class WebSocketController {

    @MessageMapping("/welcome")//浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
    @SendTo("/topic/getResponse")//服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
    public Response say(Message message) throws Exception {
        Thread.sleep(1000);
        return new Response("Welcome, " + message.getName() + "!");
    }

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;//1
//
//    @MessageMapping("/chat")
//    public void handleChat(Principal principal, String msg) { //2
//        if (principal.getName().equals("wyf")) {//3
//            messagingTemplate.convertAndSendToUser("wisely",
//                    "/queue/notifications", principal.getName() + "-send:"
//                            + msg);
//        } else {
//            messagingTemplate.convertAndSendToUser("wyf",
//                    "/queue/notifications", principal.getName() + "-send:"
//                            + msg);
//        }
//    }
}
