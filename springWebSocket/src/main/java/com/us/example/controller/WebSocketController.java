package com.us.example.controller;



import com.us.example.bean.Message;
import com.us.example.bean.Response;
import com.us.example.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by yangyibo on 16/12/29.
 *
 */
@CrossOrigin
@Controller
public class WebSocketController {
    @Autowired
    private WebSocketService ws;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RequestMapping(value = "/login")
    public String login(){
        return  "login";
    }
    @RequestMapping(value = "/ws")
    public String ws(){
        return  "ws";
    }
    @RequestMapping(value = "/chat")
    public String chat(){
        return  "chat";
    }


    //TODO  springboot-WebSocket
    // 第一是  博客 spring boot ＋WebSocket 广播式（一）地址：http://blog.csdn.net/u012373815/article/details/54375195  中所示代码
    // 第二是  博客 spring boot ＋WebSocket 广播式（二）地址：http://blog.csdn.net/u012373815/article/details/54377937   中所示代码
    // 第三是  博客 spring boot ＋WebSocket（三） 点对点式 地址： http://blog.csdn.net/u012373815/article/details/54380476  中所示代码


    /**
     * 广播式(一)：利用@SendTo注解实现
     * 即服务器端有消息时，会将消息发送给所有链接了当前endpoint 的浏览器。也就是打开多个浏览器tab。链接上websocket 以后一个tab发送信息，其他链接的tab 也能收到。
     * @param message
     * @return
     * @throws Exception
     */
    //http://localhost:8080/ws
    @MessageMapping("/welcome")//浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
    @SendTo("/topic/getResponse")//服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
    public Response say(Message message) throws Exception {
        Thread.sleep(1000);
        return new Response("Welcome, " + message.getName() + "!");
    }

    /**
     * 广播式(二)：使用 spring 的SimpMessagingTemplate 类，实现广播式推送
     * @return
     * @throws Exception
     */
    //http://localhost:8080/Welcome1
    @RequestMapping("/Welcome1")
    @ResponseBody
    public String say2()throws Exception
    {
        ws.sendMessage();
        return "is ok";
    }

    /**
     * （三） 点对点式
     * 广播式有自己的应用场景，但是广播式不能解决我门一个常见的场景，即消息由谁发送、由谁接收的问题。
     * 本例中演示了一个简单的聊天室程序。例子中只有两个用户，互相发送消息给彼此，因需要用户相关内容，所以这里引入了最简单的spring Security相关内容。
     * @param principal
     * @param message
     */
    @MessageMapping("/chat")
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
    public void handleChat(Principal principal, Message message) {

        /**
         * 此处是一段硬编码。如果发送人是wyf 则发送给 wisely 如果发送人是wisely 就发送给 wyf。
         * 通过当前用户,然后查找消息,如果查找到未读消息,则发送给当前用户。
         */
        if (principal.getName().equals("admin")) {
            //通过convertAndSendToUser 向用户发送信息,
            // 第一个参数是接收消息的用户,第二个参数是浏览器订阅的地址,第三个参数是消息本身

            messagingTemplate.convertAndSendToUser("abel",
                    "/queue/notifications", principal.getName() + "-send:"
                            + message.getName());
            /**
             * 72 行操作相等于 
             * messagingTemplate.convertAndSend("/user/abel/queue/notifications",principal.getName() + "-send:"
             + message.getName());
             */
        } else {
            messagingTemplate.convertAndSendToUser("admin",
                    "/queue/notifications", principal.getName() + "-send:"
                            + message.getName());
        }
    }
}
