package com.project.literatureclub.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

public class LikesResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private  String lastName;


}
