package com.neuedu.boot002.util;

import lombok.Data;

@Data
public class ServeResponse<T> {


    private  Integer code;
    private  String message;
    private T data;
    public ServeResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ServeResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static  <T>ServeResponse success(Integer code, String message, T data){
        return new ServeResponse(code,message,data);
    }

    public static  <T>ServeResponse success(Integer code, String message){
        return new ServeResponse(code,message);
    }

    public static  <T>ServeResponse success(String message, T data){
            return new ServeResponse(ResponseEnum.SUCCESS.getCode(), message,data);
    }

    public static  <T>ServeResponse success(String message){
        return new ServeResponse(ResponseEnum.SUCCESS.getCode(),message);
    }


    public static  <T>ServeResponse error(Integer code, String message, T data){
        return new ServeResponse(code,message,data);
    }

    public static  <T>ServeResponse error(Integer code, String message){
        return new ServeResponse(code,message);
    }

    public static  <T>ServeResponse error(String message, T data){
        return new ServeResponse(ResponseEnum.ERROR.getCode(), message,data);
    }

    public static  <T>ServeResponse error(String message){
        return new ServeResponse(ResponseEnum.ERROR.getCode(),message);
    }


}
