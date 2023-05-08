package com.project.literatureclub.controllers;

import com.project.literatureclub.dtos.UserResponseDTO;
import com.project.literatureclub.entities.*;
import com.project.literatureclub.services.ArticleService;
import com.project.literatureclub.services.JwtService;
import com.project.literatureclub.services.UserFollowsUserService;
import com.project.literatureclub.services.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final JwtService jwtService;

    private final UserService userService;

    private final UserFollowsUserService userFollowsUserService;

    private final ArticleService articleService;

    @GetMapping
    public List<UserResponseDTO> getFollowedUsers(@RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        List<User> users = userService.findFollowedUsers(u.getId());
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for(User user:users){
            Long posts = articleService.posts(user.getId());
            UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .dateOfBirth(user.getDateOfBirth())
                    .description(user.getDescription())
                    .lastName(user.getLastName())
                    .MainGenre(user.getMainGenre())
                    .firstName(user.getFirstName())
                    .isLiked(true)
                    .posts(posts)
                    .build();
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }

    @PostMapping("/{id}")
    public boolean like(@PathVariable Long id, @RequestHeader("Authorization") String token){
        System.out.println("porcec liki");
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        User writer = userService.get(id);
        if(writer.getRole()!= Role.AUTHOR){
            throw new RuntimeException();
        }
        UserFollowsUser userFollowsUser = new UserFollowsUser();
        userFollowsUser.setUser1Id(id);
        userFollowsUser.setUser2Id(u.getId());
        UserFollowsUser ufu= userFollowsUserService.save(userFollowsUser);
        return ufu.getId()!=0 && ufu.getUser1Id()==id && ufu.getUser2Id()==u.getId();
        //.setLikeCount(a.getLikeCount()+1);
    }

    @DeleteMapping("/{id}")
    public boolean dislike(@PathVariable Long id, @RequestHeader("Authorization") String token){
        System.out.println("pordzec disliki");
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        User a = userService.get(id);
        try {
            UserFollowsUser userLikesAuthor = userFollowsUserService.findUserFollowsUserByUser1IdAndUser2Id(a.getId(),
                    u.getId()).orElseThrow(RuntimeException::new);
            userFollowsUserService.delete(userLikesAuthor.getId());
            return true;
        }catch (RuntimeException e){
            return false;
        }
    }
}
