CREATE TABLE `user_likes_article` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `user_id` bigint NOT NULL,
                                        `article_id` bigint NOT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `fk_like_article` (`article_id`),
                                        KEY `fk_like_user` (`user_id`),
                                        CONSTRAINT `fk_like_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
                                        CONSTRAINT `fk_like_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)