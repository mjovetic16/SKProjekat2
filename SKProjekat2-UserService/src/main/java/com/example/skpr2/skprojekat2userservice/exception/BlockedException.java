package com.example.skpr2.skprojekat2userservice.exception;

import org.springframework.http.HttpStatus;

public class BlockedException extends CustomException{
    public BlockedException(String message) {
        super(message, ErrorCode.BLOCKED_USER, HttpStatus.FORBIDDEN);
    }
}
