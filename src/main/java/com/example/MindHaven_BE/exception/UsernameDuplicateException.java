package com.example.MindHaven_BE.exception;

public class UsernameDuplicateException extends RuntimeException {
    public UsernameDuplicateException(String message) {
        super(message);
    }
}
