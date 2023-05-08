package com.project.literatureclub.controllers;

import com.project.literatureclub.dtos.UserResponseDTO;
import com.project.literatureclub.dtos.CreateUserDTO;
import com.project.literatureclub.dtos.UpdateUserDTO;
import com.project.literatureclub.entities.User;
import com.project.literatureclub.entities.UserFollowsUser;
import com.project.literatureclub.services.ArticleService;
import com.project.literatureclub.services.JwtService;
import com.project.literatureclub.services.UserFollowsUserService;
import com.project.literatureclub.services.UserService;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    private final UserFollowsUserService userFollowsUserService;

    private final ArticleService articleService;

    private final JwtService jwtService;
    private final ModelMapper mapper;

    public UserController(UserService authorService, UserFollowsUserService userFollowsUserService, ArticleService articleService, JwtService jwtService, ModelMapper mapper) {
        this.userService = authorService;
        this.userFollowsUserService = userFollowsUserService;
        this.articleService = articleService;
        this.jwtService = jwtService;
        this.mapper = mapper;
    }

    @GetMapping("/api/user")
    public List<UserResponseDTO> getAll(@RequestHeader("Authorization") String token) {
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        //List<User> users = userService.findAllAuthors();
        List<User> users = userService.findAllExcept(u.getId());
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
        return ur;
    }

    @GetMapping("/api/myUser")
    public UserResponseDTO getMe(@RequestHeader("Authorization") String token){
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        long posts = articleService.posts(u.getId());
        UserResponseDTO ur = UserResponseDTO.builder()
                .firstName(u.getFirstName())
                .MainGenre(u.getMainGenre())
                .lastName(u.getLastName())
                .description(u.getDescription())
                .dateOfBirth(u.getDateOfBirth())
                .email(u.getEmail())
                .id(u.getId())
                .posts(posts)
                .description(u.getDescription())
                .build();
        return ur;

    }
    @GetMapping("/api/user/{id}")
    public UserResponseDTO getUserById(@PathVariable long id,@RequestHeader("Authorization") String token) {
        Claims claims = jwtService.extractAllClaims(token.split(" ")[1].trim());
        String username = claims.getSubject();
        User u= userService.findByEmail(username).get();
        var author = userService.get(id);
        long posts = articleService.posts(author.getId());
        Optional<UserFollowsUser> userFollowsUser = userFollowsUserService.findUserFollowsUserByUser1IdAndUser2Id(u.getId(),author.getId());
        UserResponseDTO ur = UserResponseDTO.builder()
                .firstName(author.getFirstName())
                .MainGenre(author.getMainGenre())
                .lastName(author.getLastName())
                .description(author.getDescription())
                .dateOfBirth(author.getDateOfBirth())
                .email(author.getEmail())
                .id(author.getId())
                .posts(posts)
                .build();
        return ur;
    }

    @PostMapping("/api/user/create")
    public UserResponseDTO createUser(@RequestBody CreateUserDTO createUserDTO) {
        var author = mapper.map(createUserDTO, User.class);
        author = userService.save(author);
        return mapper.map(author, UserResponseDTO.class);
    }

    @PutMapping("/api/user/{id}/update")
    public UserResponseDTO updateUser(@PathVariable long id, @RequestBody UpdateUserDTO updateUserDTO) {
        var author = mapper.map(updateUserDTO, User.class);
        author = userService.update(id, author);
        return mapper.map(author, UserResponseDTO.class);
    }

    @DeleteMapping("/api/user/{id}/delete")
    public void deleteUser(@PathVariable long id) {
        userService.delete(id);
    }
}