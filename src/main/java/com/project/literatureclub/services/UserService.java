package com.project.literatureclub.services;

import com.project.literatureclub.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User get(Long id);

    User save(User author);

    User update(Long id, User author);

    void delete(Long id) ;

    Optional<User> findByEmail(String email);

    List<User> search(String str);

    List<User> findAllAuthors();

    List<User> findAllExcept(Long id);

    List<User> findFollowedUsers(Long id);
}
