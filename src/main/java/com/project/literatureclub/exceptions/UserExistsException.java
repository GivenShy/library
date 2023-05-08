package com.project.literatureclub.exceptions;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User already exists");
    }
}
