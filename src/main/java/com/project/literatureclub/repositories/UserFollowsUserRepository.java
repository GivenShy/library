package com.project.literatureclub.repositories;

import com.project.literatureclub.entities.UserFollowsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowsUserRepository extends JpaRepository<UserFollowsUser,Long> {

     List<UserFollowsUser> findByUser1Id(Long id);

     Optional<UserFollowsUser> findUserFollowsUserByUser1IdAndUser2Id(Long user1Id, Long user2Id);


}
