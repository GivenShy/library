package com.project.literatureclub.services;

import com.project.literatureclub.entities.Role;
import com.project.literatureclub.entities.User;
import com.project.literatureclub.exceptions.UserNotFoundException;
import com.project.literatureclub.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository authorRepository) {
        this.userRepository = authorRepository;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public User get(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public User update(Long id, User author) {
        var oldUser = this.get(id);
        if (author.getFirstName() != null) {
            oldUser.setFirstName(author.getFirstName());
        }
        if (author.getLastName() != null) {
            oldUser.setLastName(author.getLastName());
        }
        if (author.getEmail() != null) {
            oldUser.setEmail(author.getEmail());
        }
        this.save(oldUser);
        return oldUser;
    }

    @Override
    public void delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.orElseThrow(UserNotFoundException::new));
    }

    public List<User> search(String str) {
        return userRepository.searchUser(str.toLowerCase());
    }

    public List<User> findAllAuthors(){
        return userRepository.findAllByRole(Role.AUTHOR);
    }

    @Override
    public List<User> findAllExcept(Long id) {
        return userRepository.findAllByIdIsNot(id);
    }

    @Override
    public List<User> findFollowedUsers(Long id) {
        return userRepository.followedAuthors(id);
    }


}