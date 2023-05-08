package com.project.literatureclub.repositories;

import com.project.literatureclub.dtos.ArticleResponseDTO;
import com.project.literatureclub.dtos.LikesResponse;
import com.project.literatureclub.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query(nativeQuery = true,value = "SELECT article.id, article.title ,like_count,user.first_name,user.last_name FROM  article  INNER JOIN user on article.user_id = user.id  where user.id = :id ORDER BY like_count DESC ")
    List<ArticleResponseDTO> getArticlesByAuthorId(@Param("id")Long id);


    List<Article> getArticlesByUserId(Long id);
    @Query(nativeQuery = true,value = "SELECT article.id, article.title ,like_count,user.id,user.first_name,user.last_name FROM  article  INNER JOIN user on article.user_id = user.id ORDER BY like_count DESC")
    List<ArticleResponseDTO> findAllArticles();

    @Query(nativeQuery = true,value = "SELECT article.id, article.user_id,article.title,text,like_count FROM article INNER JOIN user on article.user_id = user.id where LOWER(title) like %:s% ")
    List<Article> search(@Param("s") String str);


    @Query(nativeQuery = true,value = "SELECT COUNT(*) FROM article where user_id = :id ")
    Long postsOfAuthor(@Param("id") Long authorId);

    @Query(nativeQuery = true,value = "SELECT article.id,article.title,article.user_id,article.text,article.like_count FROM article INNER JOIN user_likes_article ula on article.id = ula.article_id where ula.user_id = :id ")
    List<Article> likedArticles(@Param("id")Long id);


}
