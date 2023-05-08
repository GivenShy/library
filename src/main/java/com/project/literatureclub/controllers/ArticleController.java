package com.project.literatureclub.controllers;


import com.project.literatureclub.dtos.ArticleResponseDTO;
import com.project.literatureclub.dtos.CreateArticleRequestDTO;
import com.project.literatureclub.dtos.CreateArticleResponseDTO;
import com.project.literatureclub.entities.Article;
import com.project.literatureclub.entities.User;
import com.project.literatureclub.entities.UserLikesArticles;
import com.project.literatureclub.services.ArticleService;
import com.project.literatureclub.services.JwtService;
import com.project.literatureclub.services.UserLikesArticleService;
import com.project.literatureclub.services.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserLikesArticleService userLikesArticleService;

    @PostMapping
    public boolean createArticleDTO(@RequestBody CreateArticleRequestDTO article,@RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String role = (String) claims.get("role");
        if(!role.equals("AUTHOR")){
            throw new RuntimeException();
        }
        String username = claims.getSubject();
        User user = userService.findByEmail(username).get();
        Article article1= new Article();
        article1.setTitle(article.getTitle());
        article1.setText(article.getText());
        article1.setUserId(user.getId());
        article1.setLikeCount(0L);
        Article a = articleService.save(article1);
        System.out.println(a.getId());
        CreateArticleResponseDTO carDTO = new CreateArticleResponseDTO();
        carDTO.setUserId(a.getUserId());
        carDTO.setId(a.getId());
        carDTO.setText(a.getText());
        carDTO.setTitle(a.getTitle());
        carDTO.setLikeCount(a.getLikeCount());

        return a.getId()!=0 && a.getUserId()==user.getId();
    }

    @GetMapping("/{id}")
    public ArticleResponseDTO getArticleById(@PathVariable Long id,@RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();
        Optional<UserLikesArticles> ul = userLikesArticleService.findUserLikesArticlesByArticleIdAndUserId(id,u.getId());
        Article article = articleService.get(id);
        articleResponseDTO.setLikeCount(article.getLikeCount());
        articleResponseDTO.setText(article.getText());
        articleResponseDTO.setId(article.getId());
        articleResponseDTO.setUserId(article.getUserId());
        articleResponseDTO.setTitle(article.getTitle());
        System.out.println(ul.get());
        articleResponseDTO.setLiked(ul.isPresent());
        User a = userService.get(article.getUserId());
        articleResponseDTO.setAuthorName(a.getFirstName()+ " " + a.getLastName());
        return articleResponseDTO;
    }

    @GetMapping("my")
    public List<ArticleResponseDTO> getMyArticles(@RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        return getArticlesByUserId(u.getId());
    }

    @GetMapping("author/{id}")
    public List<ArticleResponseDTO> getArticlesByUserId(@PathVariable Long id){
        User user = userService.get(id);
        List<Article> articles = articleService.getArticlesByUserId(id);
        ArticleResponseDTO articleResponseDTO;
        List<ArticleResponseDTO> dtos = new ArrayList<>();
        for(Article article:articles) {
            articleResponseDTO = new ArticleResponseDTO();
            articleResponseDTO.setLikeCount(article.getLikeCount());
            articleResponseDTO.setText(article.getText());
            articleResponseDTO.setId(article.getId());
            articleResponseDTO.setUserId(article.getUserId());
            articleResponseDTO.setTitle(article.getTitle());
            articleResponseDTO.setLiked(userLikesArticleService.findUserLikesArticlesByArticleIdAndUserId(article.getId(),user.getId()).isPresent());
            articleResponseDTO.setAuthorName(user.getFirstName() + " " + user.getLastName());
            dtos.add(articleResponseDTO);
        }
        return dtos;
    }

   @GetMapping
    public List<ArticleResponseDTO> getAllArticles(@RequestHeader("Authorization") String token){
       Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
       String username = claims.getSubject();
       User u= userService.findByEmail(username).get();
       ArticleResponseDTO articleResponseDTO;
       List<Article> articles = articleService.getAll();
       List<ArticleResponseDTO> dtos = new ArrayList<>();
       for(Article article:articles) {
           articleResponseDTO = new ArticleResponseDTO();
           articleResponseDTO.setLikeCount(article.getLikeCount());
           articleResponseDTO.setText(article.getText());
           articleResponseDTO.setId(article.getId());
           articleResponseDTO.setUserId(article.getUserId());
           articleResponseDTO.setTitle(article.getTitle());
           articleResponseDTO.setLiked(userLikesArticleService.findUserLikesArticlesByArticleIdAndUserId(article.getId(),u.getId()).isPresent());
           User a = userService.get(article.getUserId());
           articleResponseDTO.setAuthorName(a.getFirstName() + " " + a.getLastName());
           dtos.add(articleResponseDTO);
       }
       return dtos;
   }


}
