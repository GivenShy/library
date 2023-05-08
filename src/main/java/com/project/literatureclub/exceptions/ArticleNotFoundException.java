package com.project.literatureclub.exceptions;

public class ArticleNotFoundException extends NotFoundException {
    public ArticleNotFoundException(){
        super("Article is not found");
    }
}
