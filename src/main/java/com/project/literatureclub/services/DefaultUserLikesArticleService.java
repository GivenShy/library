package com.project.literatureclub.services;

import com.project.literatureclub.dtos.LikesResponse;
import com.project.literatureclub.entities.UserLikesArticles;
import com.project.literatureclub.repositories.UserLikesArticlesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserLikesArticleService implements UserLikesArticleService{
    private final UserLikesArticlesRepository repo;

    public DefaultUserLikesArticleService(UserLikesArticlesRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserLikesArticles get(Long id) {
        Optional<UserLikesArticles> temp = repo.findById(id);
        return temp.orElseThrow(RuntimeException::new);
    }

    @Override
    public UserLikesArticles save(UserLikesArticles temp) {
        repo.save(temp);
        return temp;
    }

    @Override
    public void delete(Long id) {
        Optional<UserLikesArticles> temp = repo.findById(id);
        repo.delete(temp.orElseThrow(RuntimeException::new));
    }

    @Override
    public List<UserLikesArticles> findByUserId(Long id) {
        return repo.findByUserId(id);
    }

    @Override
    public Optional<UserLikesArticles> findUserLikesArticlesByArticleIdAndUserId(Long articleId, Long userId) {
        return repo.findUserLikesArticlesByArticleIdAndUserId(articleId,userId);
    }

    @Override
    public List<LikesResponse> FindLikes(Long id) {
        return repo.FindLikes(id);
    }


}

