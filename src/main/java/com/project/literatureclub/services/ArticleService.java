package com.project.literatureclub.services;

import com.project.literatureclub.dtos.ArticleResponseDTO;
import com.project.literatureclub.entities.Article;
import com.project.literatureclub.entities.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleService {

    Article get(Long id);

    Article save(Article article);

    void delete(Long id);

    List<Article> getArticlesByUserId(Long id);

    Article update(Article article);

    List<Article> getAll();

    List<Article> search(String str);

    Long posts(Long id);

    List<Article> likedArticles(Long id);
}
