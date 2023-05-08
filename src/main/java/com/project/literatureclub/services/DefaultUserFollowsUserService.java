package com.project.literatureclub.services;

import com.project.literatureclub.entities.UserFollowsUser;
import com.project.literatureclub.repositories.UserFollowsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserFollowsUserService implements UserFollowsUserService {

    private final UserFollowsUserRepository repo;

    public DefaultUserFollowsUserService(UserFollowsUserRepository repo) {
        this.repo = repo;
    }


    @Override
    public UserFollowsUser get(Long id) {
        Optional<UserFollowsUser> temp = repo.findById(id);
        return temp.orElseThrow(RuntimeException::new);
    }

    @Override
    public UserFollowsUser save(UserFollowsUser temp) {
        repo.save(temp);
        return temp;
    }

    @Override
    public void delete(Long id) {
        Optional<UserFollowsUser> temp = repo.findById(id);
        repo.delete(temp.orElseThrow(RuntimeException::new));
    }

    @Override
    public List<UserFollowsUser> findByUserId(Long id) {
        return repo.findByUser1Id(id);
    }

    @Override
    public Optional<UserFollowsUser> findUserFollowsUserByUser1IdAndUser2Id(Long user1Id, Long user2Id) {
        return repo.findUserFollowsUserByUser1IdAndUser2Id(user1Id, user2Id);
    }
}
