package com.telran.contacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundRuntimeException extends RuntimeException {

    public UserNotFoundRuntimeException(String message) {
        super(message);
    }


}
