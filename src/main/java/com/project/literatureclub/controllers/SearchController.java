package com.project.literatureclub.controllers;

import com.project.literatureclub.dtos.ArticleResponseDTO;
import com.project.literatureclub.dtos.SearchRequestDTO;
import com.project.literatureclub.dtos.SearchResponseDTO;
import com.project.literatureclub.dtos.UserResponseDTO;
import com.project.literatureclub.entities.Article;
import com.project.literatureclub.entities.User;
import com.project.literatureclub.entities.UserFollowsUser;
import com.project.literatureclub.services.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchController {
    private final UserService userService;
    private final ArticleService articleService;
    private final UserLikesArticleService userLikesArticleService;
    private final JwtService jwtService;
    private final UserFollowsUserService userFollowsUserService;

    @GetMapping("/search")
    public SearchResponseDTO search(@RequestBody SearchRequestDTO requestDTO,@RequestHeader("Authorization") String token){
        System.out.println("Pordzec search anel");
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        List<User> users = userService.search(requestDTO.getSearch().toLowerCase());

        List<UserResponseDTO> ur = new ArrayList<>();
        for (User us : users) {
            Long posts = articleService.posts(us.getId());
            Optional<UserFollowsUser> userFollowsUser = userFollowsUserService.findUserFollowsUserByUser1IdAndUser2Id(us.getId(),u.getId());
            UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                    .firstName(us.getFirstName())
                    .lastName(us.getLastName())
                    .email(us.getEmail())
                    .id(us.getId())
                    .description(us.getDescription())
                    .posts(posts)
                    .dateOfBirth(us.getDateOfBirth())
                    .MainGenre(us.getMainGenre())
                    .isLiked(userFollowsUser.isPresent())
                    .build();
            ur.add(userResponseDTO);
        }
        List<Article> articles = articleService.search(requestDTO.getSearch().toLowerCase());
        SearchResponseDTO sr = new SearchResponseDTO();
        List<ArticleResponseDTO> dtos = new ArrayList<>();
        for(Article article:articles) {

            User user = userService.get(article.getUserId());
            ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();
            articleResponseDTO.setLikeCount(article.getLikeCount());
            articleResponseDTO.setText(article.getText());
            articleResponseDTO.setId(article.getId());
            articleResponseDTO.setUserId(article.getUserId());
            articleResponseDTO.setTitle(article.getTitle());
            articleResponseDTO.setLiked(userLikesArticleService.findUserLikesArticlesByArticleIdAndUserId(article.getId(),u.getId()).isPresent());
            articleResponseDTO.setAuthorName(user.getFirstName() + " " + user.getLastName());
            dtos.add(articleResponseDTO);
        }
        sr.setArticleList(dtos);
        sr.setUserList(ur);
        return sr;
    }
}
