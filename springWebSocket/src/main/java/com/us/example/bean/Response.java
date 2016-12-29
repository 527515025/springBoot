package com.us.example.bean;

/**
 * Created by yangyibo on 16/12/29.
 */
public class Response {
    private String responseMessage;
    public Response(String responseMessage){
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage(){
        return responseMessage;
    }
}