package com.us.example.bean;

/**
 * Created by yangyibo on 16/12/29.
 * 服务器向浏览器发送的此类消息。
 */
public class Response {
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    private String responseMessage;
    public Response(String responseMessage){
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage(){
        return responseMessage;
    }
}