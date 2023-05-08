package com.project.literatureclub.exceptions;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User is not found");
    }
}
