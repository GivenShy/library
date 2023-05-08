package com.project.literatureclub.dtos;


import lombok.Getter;
import lombok.Setter;

public class CreateArticleResponseDTO {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private Long userId;


    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Long likeCount;
}
