package com.kaishengit.exception;

/**
 * Created by jiahao0 on 2017/2/20.
 * 业务异常
 */
public class ServiceException extends RuntimeException {

    public ServiceException(){}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message,Throwable th) {
        super(message,th);
    }

    public ServiceException(Throwable th) {
        super(th);
    }
}
