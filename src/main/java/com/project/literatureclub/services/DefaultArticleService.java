package com.project.literatureclub.services;

import com.project.literatureclub.dtos.ArticleResponseDTO;
import com.project.literatureclub.entities.Article;
import com.project.literatureclub.exceptions.ArticleNotFoundException;
import com.project.literatureclub.exceptions.UserNotFoundException;
import com.project.literatureclub.repositories.ArticleRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultArticleService implements ArticleService {
    private final ArticleRepository repository;

    public DefaultArticleService(ArticleRepository repository){
        this.repository = repository;
    }

    @Override
    public Article get(Long id) {
        Optional<Article> user = repository.findById(id);
        return user.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Article save(Article user) {
        repository.save(user);
        return user;
    }


    @Override
    public void delete(Long id) {
        Optional<Article> author = repository.findById(id);
        repository.delete(author.orElseThrow(ArticleNotFoundException::new));
    }

    @Override
    public List<Article> getArticlesByUserId(Long id) {
        return repository.getArticlesByUserId(id);
    }

    @Override
    public Article update(Article article) {
         return repository.save(article);
    }

    @Override
    public List<Article> getAll() {
        return repository.findAll();
    }

    public List<Article> search(String str){
        return repository.search(str.toLowerCase());
    }

    public Long posts(Long id){
        return repository.postsOfAuthor(id);
    }

    public List<Article> likedArticles(Long id){
        return repository.likedArticles(id);
    }

}
