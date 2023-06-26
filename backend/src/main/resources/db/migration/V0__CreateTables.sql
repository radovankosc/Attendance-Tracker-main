CREATE TABLE IF NOT EXISTS `user` (`id` bigint NOT NULL AUTO_INCREMENT,
                                      `activation_code` varchar(255) DEFAULT NULL,
                                      `active` bit(1) DEFAULT NULL,
                                      `approver_id` bigint DEFAULT NULL,
                                      `email` varchar(255) DEFAULT NULL,
                                      `joining_timestamp` datetime DEFAULT NULL,
                                      `password` varchar(255) DEFAULT NULL,
                                      `pw_reset_code` varchar(255) DEFAULT NULL,
                                      `username` varchar(250) CHARACTER SET latin1 COLLATE latin1_general_cs DEFAULT NULL,
                                      `work_schedule` varchar(255) DEFAULT NULL,
                                      PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `user_roles` (
                                            `user_id` bigint NOT NULL,
                                            `roles` varchar(255) DEFAULT NULL,
                                            PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `holidays` (
                                          `id` bigint NOT NULL AUTO_INCREMENT,
                                          `description` varchar(255) DEFAULT NULL,
                                          `end_timestamp` datetime DEFAULT NULL,
                                          `start_timestamp` datetime DEFAULT NULL,
                                          `title` varchar(255) DEFAULT NULL,
                                          PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `resources` (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `file_name` varchar(255) DEFAULT NULL,
                                           `file_path` varchar(255) DEFAULT NULL,
                                           `file_type` varchar(255) DEFAULT NULL,
                                           `user_id` bigint DEFAULT NULL,
                                           PRIMARY KEY (`id`),
                                           KEY `FK5b41411qkync7c78jw4yr381o` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `track_period` (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `end_timestamp` datetime DEFAULT NULL,
                                              `period_status` varchar(255) DEFAULT NULL,
                                              `start_timestamp` datetime DEFAULT NULL,
                                              `approver_id` bigint DEFAULT NULL,
                                              `user_id` bigint DEFAULT NULL,
                                              PRIMARY KEY (`id`),
                                              KEY `FKws888j3b4lbu0gm0wtxsgtil` (`approver_id`),
                                              KEY `FKhug7f4ogvh7fxo186rk22ryxa` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `track_record` (
                                              `id` bigint NOT NULL AUTO_INCREMENT,
                                              `attachment` varchar(255) DEFAULT NULL,
                                              `end_timestamp` datetime DEFAULT NULL,
                                              `note` varchar(255) DEFAULT NULL,
                                              `record_type` varchar(255) DEFAULT NULL,
                                              `start_timestamp` datetime DEFAULT NULL,
                                              `user_id` bigint DEFAULT NULL,
                                              PRIMARY KEY (`id`),
                                              KEY `FKdcd6x1ymjvef1fcbk75rpq3hw` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;