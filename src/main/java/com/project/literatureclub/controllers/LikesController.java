package com.project.literatureclub.controllers;

import com.project.literatureclub.dtos.ArticleResponseDTO;
import com.project.literatureclub.dtos.LikesResponse;
import com.project.literatureclub.entities.Article;
import com.project.literatureclub.entities.UserLikesArticles;
import com.project.literatureclub.entities.User;
import com.project.literatureclub.services.ArticleService;
import com.project.literatureclub.services.JwtService;
import com.project.literatureclub.services.UserLikesArticleService;
import com.project.literatureclub.services.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikesController {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserLikesArticleService userLikesArticleService;
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleResponseDTO> getLikedBooks(@RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        List<Article> articles = articleService.likedArticles(u.getId());
        List<ArticleResponseDTO> articleResponseDTOS = new ArrayList<>();
        ArticleResponseDTO articleResponseDTO;
        List<ArticleResponseDTO> dtos = new ArrayList<>();
        for(Article article:articles) {
            User user = userService.get(article.getUserId());
            articleResponseDTO = new ArticleResponseDTO();
            articleResponseDTO.setLikeCount(article.getLikeCount());
            articleResponseDTO.setText(article.getText());
            articleResponseDTO.setId(article.getId());
            articleResponseDTO.setUserId(article.getUserId());
            articleResponseDTO.setTitle(article.getTitle());
            articleResponseDTO.setLiked(true);
            articleResponseDTO.setAuthorName(user.getFirstName() + " " + user.getLastName());
            dtos.add(articleResponseDTO);
        }
        return dtos;

    }
    @PostMapping("/{id}")
    public boolean like(@PathVariable Long id, @RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        Article a = articleService.get(id);
        UserLikesArticles userLikesArticles = new UserLikesArticles();
        userLikesArticles.setArticleId(id);
        userLikesArticles.setUserId(u.getId());
        userLikesArticleService.save(userLikesArticles);
        a.setLikeCount(a.getLikeCount()+1);
        articleService.update(a);
        return userLikesArticles.getId()!=0 && userLikesArticles.getArticleId()==id && userLikesArticles.getUserId()==u.getId();
    }

    @DeleteMapping("/{id}")
    public boolean dislike(@PathVariable Long id, @RequestHeader("Authorization") String token){
        System.out.println("dislike en anum");
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        Article a = articleService.get(id);
        try {
            UserLikesArticles userLikesArticles = userLikesArticleService.findUserLikesArticlesByArticleIdAndUserId(a.getId(),
                    u.getId()).orElseThrow(RuntimeException::new);

            userLikesArticleService.delete(userLikesArticles.getId());
            if (a.getLikeCount() > 0)
                a.setLikeCount(a.getLikeCount() - 1);
            articleService.update(a);


            return true;
        }
        catch (RuntimeException e){
            return false;
        }
    }


}
