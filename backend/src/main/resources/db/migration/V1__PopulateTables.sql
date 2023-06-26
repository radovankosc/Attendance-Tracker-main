INSERT INTO user(id, activation_code, active, approver_id, email, joining_timestamp, password, pw_reset_code, username, work_schedule) VALUES
                                                                                                                                           (1, null, 1, null, 'bigboss@gmail.com','2023-02-15 00:00:00', '$2a$10$gpeyW0Gj2EwhtIKicDgCeOFeWu4dR2fSmqx6AXzZp.kwRQgoPz7Mm', null, 'John', 'AB0DE'),
                                                                                                                                           (2, null, 1, null, 'thebiggestbigestboss@gmail.com','2023-01-02 00:00:00', '$2a$10$.IdcjYKDpZPWF0mT0RokaeYX3EZ5IMpvrQvsEYl6sKiXn.6UKHRry', null, 'Michael', '8888800')
;

INSERT INTO user_roles(user_id, roles) VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');

INSERT INTO holidays(id, description, end_timestamp, start_timestamp, title) VALUES (1, 'public holiday', '2023-02-22 00:00:00', '2023-02-22 23:59:59', 'Public Holiday in February for John & Michael');

INSERT INTO track_period(id, end_timestamp, period_status, start_timestamp, approver_id, user_id) VALUES
                                                                                                      (1,'2023-02-27 00:00:00', 'CLOSED', '2023-02-20 00:00:00', 2, 1),
                                                                                                      (2,'2023-03-06 00:00:00', 'SUBMITTED', '2023-02-27 00:00:00', 2, 1),
                                                                                                      (3,'2023-03-13 00:00:00', 'REQUESTED', '2023-03-06 00:00:00', 2, 1),
                                                                                                      (4,'2023-02-27 00:00:00', 'CLOSED', '2023-02-20 00:00:00', 2, 2),
                                                                                                      (5,'2023-03-06 00:00:00', 'REQUESTED', '2023-02-27 00:00:00', 2, 2)
;

INSERT INTO track_record(id, attachment, end_timestamp, note, record_type, start_timestamp, user_id) VALUES
                                                                                                         (1, null, '2023-02-15 20:00:00', 'Visited my psychiatrist', 'DOCTOR_VISIT', '2023-02-15 06:00:00', 2),
                                                                                                         (2, null, '2023-02-15 20:00:00', 'Worked on this endpoint','WORKED_HOURS', '2023-02-15 06:00:00', 1),
                                                                                                         (3, null,'2023-02-20 10:00:00', 'Michael workday','WORKED_HOURS', '2023-02-20 08:00:00', 2),
                                                                                                         (4, null,'2023-02-21 16:00:00', 'Tuesday Holiday','PUBLIC_HOLIDAY', '2023-02-21 08:00:00', 2),
                                                                                                         (5, null,'2023-02-22 16:00:00', 'Michael workday','WORKED_HOURS', '2023-02-22 08:00:00', 2),
                                                                                                         (6, null,'2023-02-23 12:00:00', 'Michael workday','WORKED_HOURS', '2023-02-23 08:00:00', 2),
                                                                                                         (7, null,'2023-02-23 16:00:00', 'rehabilitation','DOCTOR_VISIT', '2023-02-23 12:00:00', 2),
                                                                                                         (8, null,'2023-02-20 16:00:00', 'finals at school','UNPAID_LEAVE', '2023-02-20 10:00:00', 2),
                                                                                                         (9, null,'2023-02-20 20:00:00', 'Johnny workday','WORKED_HOURS', '2023-02-20 08:00:00', 1),
                                                                                                         (10, 'MRI-Scan_Fluffy.png','2023-02-21 12:00:00', 'Visited hamster emergency room','DOCTOR_ACCOMPANY', '2023-02-21 08:00:00', 1),
                                                                                                         (11, null,'2023-02-21 16:00:00', 'Johnny workday','WORKED_HOURS', '2023-02-22 08:00:00', 1),
                                                                                                         (12, null,'2023-02-22 10:00:00', 'Johnny workday','WORKED_HOURS', '2023-02-22 08:00:00', 1),
                                                                                                         (13, null,'2023-02-22 16:00:00', 'Fluffy needed assistance','LEAVE_FOR_FAMILY_CARE', '2023-02-22 10:00:00', 1),
                                                                                                         (14, null,'2023-02-23 20:00:00', 'Too upset from watching The Notebook.','OTHER', '2023-02-23 08:00:00', 1),
                                                                                                         (15, 'MRI-Scan_Fluffy.png','2023-02-23 20:00:00', 'Johnny workday ','WORKED_HOURS','2023-02-23 08:00:00', 1);