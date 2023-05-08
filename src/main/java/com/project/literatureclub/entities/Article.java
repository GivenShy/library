package com.project.literatureclub.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Entity
public class Article {

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
    @Column(name = "user_id")
    private Long userId;

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private Long likeCount;
}
