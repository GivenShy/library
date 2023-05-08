CREATE TABLE `user_follows_user` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `user1_id` bigint NOT NULL,
                                         `user2_id` bigint NOT NULL,
                                         PRIMARY KEY (`id`),
                                         KEY `fk_follow_author` (`user1_id`),
                                         KEY `fk_follow_user` (`user2_id`),
                                         CONSTRAINT `fk_follow_author` FOREIGN KEY (`user1_id`) REFERENCES `user` (`id`),
                                         CONSTRAINT `fk_follow_user` FOREIGN KEY (`user2_id`) REFERENCES `user` (`id`)
)