package com.project.literatureclub.repositories;
import com.project.literatureclub.entities.Role;
import com.project.literatureclub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM user where role = 0 and ((LOWER(first_name) like %:str% or LOWER(last_name) like %:str% ))")
    List<User> searchUser(@Param("str") String something);

    List<User> findAllByRole(Role role);

    List<User> findAllByIdIsNot(Long id);

    @Query(nativeQuery = true, value = "SELECT  user.id, user.first_name,user.last_name,user.email,user.password,user.main_genre,user.date_of_birth,user.description,user.follower_count,user.role,user.photo FROM user INNER JOIN user_follows_user ufu on user.id = ufu.user1_id WHERE user2_id = :id")
    List<User> followedAuthors(@Param("id") Long id);
}
