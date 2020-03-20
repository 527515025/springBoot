package cn.abel.response;


import cn.abel.code.*;

import java.util.HashMap;

/**
 * Created by guoshan on 2018/3/21.
 * <p>
 * 服务端返回给前端或客户端使用
 */
public class ResponseEntity {
    int status = 200;

    String message = "ok";

    Object data = new HashMap();


    private ResponseEntity() {
    }

    public static ResponseEntity ok(Object data) {
        ResponseEntity ResponseEntity = new ResponseEntity();
        ResponseEntity.setData(data);
        return ResponseEntity;
    }

    public static ResponseEntity error(InfoCode infoCode) {
        ResponseEntity ResponseEntity = new ResponseEntity();
        ResponseEntity.setStatus(infoCode.getStatus());
        ResponseEntity.setMessage(infoCode.getMsg());
        return ResponseEntity;
    }

    public static ResponseEntity ok() {
        ResponseEntity ResponseEntity = new ResponseEntity();
        return ResponseEntity;
    }

    public static ResponseEntity error(int code, String msg) {
        ResponseEntity ResponseEntity = new ResponseEntity();
        ResponseEntity.setStatus(code);
        ResponseEntity.setMessage(msg);
        return ResponseEntity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data == null ? new HashMap() : data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
