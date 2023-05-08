CREATE TABLE `article` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `title` varchar(20) NOT NULL,
                           `user_id` bigint NOT NULL,
                           `text` longtext NOT NULL,
                           `like_count` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `fk_article_author` (`user_id`),
                           CONSTRAINT `fk_article_author` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)