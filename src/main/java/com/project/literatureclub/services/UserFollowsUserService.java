package com.project.literatureclub.services;

import com.project.literatureclub.entities.UserFollowsUser;

import java.util.List;
import java.util.Optional;

public interface UserFollowsUserService {
    UserFollowsUser get(Long id);

    UserFollowsUser save(UserFollowsUser temp);


    void delete(Long id);

    List<UserFollowsUser> findByUserId(Long id);

    Optional<UserFollowsUser> findUserFollowsUserByUser1IdAndUser2Id(Long user1Id, Long user2Id);

}
