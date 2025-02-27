-- Insert 20 users
INSERT INTO users (username, email, password, role, is_available, last_online) VALUES
                                                                                              ('john_doe', 'john@example.com', 'hashedpass1', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('jane_smith', 'jane@example.com', 'hashedpass2', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('bob_jones', 'bob@example.com', 'hashedpass3', 'USER', false, '2024-02-26 00:00:00'),
                                                                                              ('alice_wong', 'alice@example.com', 'hashedpass4', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('mike_chen', 'mike@example.com', 'hashedpass5', 'USER', false, '2024-02-26 00:00:00'),
                                                                                              ('sarah_park', 'sarah@example.com', 'hashedpass6', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('david_kim', 'david@example.com', 'hashedpass7', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('emma_wilson', 'emma@example.com', 'hashedpass8', 'USER', false, '2024-02-26 00:00:00'),
                                                                                              ('tom_brown', 'tom@example.com', 'hashedpass9', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('lisa_may', 'lisa@example.com', 'hashedpass10', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('peter_li', 'peter@example.com', 'hashedpass11', 'USER', false, '2024-02-26 00:00:00'),
                                                                                              ('amy_yang', 'amy@example.com', 'hashedpass12', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('chris_lee', 'chris@example.com', 'hashedpass13', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('kate_park', 'kate@example.com', 'hashedpass14', 'USER', false, '2024-02-26 00:00:00'),
                                                                                              ('ryan_ng', 'ryan@example.com', 'hashedpass15', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('olivia_tan', 'olivia@example.com', 'hashedpass16', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('sam_wu', 'sam@example.com', 'hashedpass17', 'USER', false, '2024-02-26 00:00:00'),
                                                                                              ('ella_choi', 'ella@example.com', 'hashedpass18', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('jack_lin', 'jack@example.com', 'hashedpass19', 'USER', true, '2024-02-26 00:00:00'),
                                                                                              ('mia_kang', 'mia@example.com', 'hashedpass20', 'USER', false, '2024-02-26 00:00:00');

-- Insert 5 conversations
INSERT INTO conversations (name) VALUES
                                     ('General Chat'),
                                     ('Tech Talk'),
                                     ('Random Thoughts'),
                                     ('Work Group'),
                                     ('Friends Hangout');

-- Link users to conversations (multiple users per conversation)
INSERT INTO user_conversations (user_id, conversation_id) VALUES
                                                              (1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
                                                              (6, 2), (7, 2), (8, 2), (9, 2),
                                                              (10, 3), (11, 3), (12, 3),
                                                              (13, 4), (14, 4), (15, 4), (16, 4),
                                                              (17, 5), (18, 5), (19, 5), (20, 5),
                                                              (1, 2), (2, 3), (3, 4); -- Some users in multiple conversations

-- Insert 30 messages across different conversations
INSERT INTO messages (content, user_id, conversation_id) VALUES
                                                             ('Hello everyone!', 1, 1),
                                                             ('Hey there!', 2, 1),
                                                             ('What\'s up?', 3, 1),
                                                            ('Anyone working on AI?', 6, 2),
                                                            ('I love coding in Python', 7, 2),
                                                            ('Java is better IMO', 8, 2),
                                                            ('Random question: favorite food?', 10, 3),
                                                            ('Pizza for sure', 11, 3),
                                                            ('Sushi here', 12, 3),
                                                            ('Meeting at 3 PM today', 13, 4),
                                                            ('Don\'t forget the reports', 14, 4),
                                                             ('Can someone share the slides?', 15, 4),
                                                             ('Who wants to play games tonight?', 17, 5),
                                                             ('I\'m in!', 18, 5),
                                                            ('What game?', 19, 5),
                                                            ('How\'s everyone\'s day?', 1, 1),
                                                            ('Pretty good, thanks!', 4, 1),
                                                            ('Just chilling', 5, 1),
                                                            ('AI is fascinating', 9, 2),
                                                            ('Any new projects?', 16, 4),
                                                            ('Hey Jane!', 1, 3),
                                                            ('Hi John!', 2, 3),
                                                            ('Good morning team', 15, 4),
                                                            ('Evening plans anyone?', 20, 5),
                                                            ('New tech discussion?', 6, 2),
                                                            ('Coffee time!', 10, 3),
                                                            ('Work work work', 13, 4),
                                                            ('Game night was fun', 17, 5),
                                                            ('Agreed!', 18, 5),
                                                            ('What\'s next?', 19, 5);

-- Insert 5 images (linked to specific users and messages)
INSERT INTO images (public_id, url, user_id, message_id) VALUES
                                                             ('img1', 'http://example.com/img1.jpg', 1, 1),
                                                             ('img2', 'http://example.com/img2.jpg', 6, 4),
                                                             ('img3', 'http://example.com/img3.jpg', 10, 7),
                                                             ('img4', 'http://example.com/img4.jpg', 13, 10),
                                                             ('img5', 'http://example.com/img5.jpg', 17, 13);

-- Insert last read markers for users in conversations
INSERT INTO last_reads (user_id, message_id) VALUES
                                                 (1, 16),  -- John read up to "How's everyone's day?"
                                                 (2, 17),  -- Jane read up to "Pretty good, thanks!"
                                                 (3, 3),   -- Bob read up to "What's up?"
                                                 (6, 19),  -- Mike read up to "AI is fascinating"
                                                 (10, 26), -- Lisa read up to "Coffee time!"
                                                 (13, 27), -- Chris read up to "Work work work"
                                                 (17, 29); -- Sam read up to "Game night was fun"