CREATE DATABASE IF NOT EXISTS todo_db;

USE todo_db;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `password`   varchar(255) NOT NULL,
    `user_login` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `tasks`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `date`       datetime(6)  DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    `priority`   varchar(255) DEFAULT NULL,
    `task_state` varchar(255) DEFAULT NULL,
    `owner_id`   bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKh279lo9lqbhxqh68jq9sqs83s` (`owner_id`),
    CONSTRAINT `FKh279lo9lqbhxqh68jq9sqs83s` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`)
);

CREATE TABLE IF NOT EXISTS `users_roles`
(
    `users_id` bigint NOT NULL,
    `roles`    varchar(255) DEFAULT NULL,
    KEY `FKml90kef4w2jy7oxyqv742tsfc` (`users_id`),
    CONSTRAINT `FKml90kef4w2jy7oxyqv742tsfc` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
);

CREATE TABLE IF NOT EXISTS `users_tasks`
(
    `users_id` bigint NOT NULL,
    `tasks_id` bigint NOT NULL,
    PRIMARY KEY (`users_id`, `tasks_id`),
    UNIQUE KEY `UK_ntgmxrnn2x6w47tltwj5d1fyt` (`tasks_id`),
    CONSTRAINT `FKld0lt93yib868lmnx5oruyrti` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKphskmyjd10svfk7iwnda2rib0` FOREIGN KEY (`tasks_id`) REFERENCES `tasks` (`id`)
);