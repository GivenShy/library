package com.project.literatureclub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Entity
@RequiredArgsConstructor
@Table(name = "user_likes_article")
public class UserLikesArticles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;


    @Getter
    @Setter
    @Column(name = "user_id")
    private Long userId;

    @Getter
    @Setter
    @Column(name = "article_id")
    private Long articleId;

}
