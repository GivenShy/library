package com.project.literatureclub.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Table(name = "user_follows_user")
public class UserFollowsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "user1_id")
    private Long user1Id;

    @Getter
    @Setter
    @Column(name = "user2_id")
    private Long user2Id;

}
