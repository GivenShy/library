CREATE TABLE `user` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `first_name` varchar(20) NOT NULL,
                          `last_name` varchar(20) NOT NULL,
                          `email` varchar(255) NOT NULL,
                          `password` varchar(255) NOT NULL,
                          `photo` blob,
                          `date_of_birth` datetime DEFAULT NULL,
                          `main_genre` varchar(20) DEFAULT NULL,
                          `follower_count` bigint DEFAULT 0,
                          `description` TEXT DEFAULT NULL,
                          `role` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`)
)