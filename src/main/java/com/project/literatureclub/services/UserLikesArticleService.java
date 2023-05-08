package com.project.literatureclub.services;

import com.project.literatureclub.dtos.LikesResponse;
import com.project.literatureclub.entities.UserLikesArticles;

import java.util.List;
import java.util.Optional;

public interface UserLikesArticleService {
    UserLikesArticles get(Long id);

    UserLikesArticles save( UserLikesArticles temp);


    void delete(Long id) ;

    List<UserLikesArticles> findByUserId(Long id);

    Optional<UserLikesArticles> findUserLikesArticlesByArticleIdAndUserId(Long articleId, Long userId);

    List<LikesResponse>  FindLikes(Long id);

}
