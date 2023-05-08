package com.project.literatureclub.repositories;

import com.project.literatureclub.dtos.LikesResponse;
import com.project.literatureclub.entities.UserLikesArticles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLikesArticlesRepository extends JpaRepository<UserLikesArticles,Long> {

     List<UserLikesArticles> findByUserId(Long id);

    Optional<UserLikesArticles> findUserLikesArticlesByArticleIdAndUserId(Long articleId,Long userId);

    @Query(nativeQuery = true,value = "SELECT article.id, article.title ,article_id,text,like_count,user.first_name,user.last_name FROM user_likes_article INNER JOIN article ON user_likes_article.article_id = article.id INNER JOIN user on article.user_id = user.id where article.user_id = :id ")
    List<LikesResponse>  FindLikes(@Param("id") Long id);
}
