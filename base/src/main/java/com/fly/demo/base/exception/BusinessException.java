package com.fly.demo.base.exception;

public class BusinessException extends RuntimeException{

    private String msg;

    public BusinessException(String msg) {
        super(msg);
    }
}
