CREATE TABLE IF NOT EXISTS restaurant (
                                          id SERIAL PRIMARY KEY,
                                          name VARCHAR(255),
    capacity INT
    );

CREATE TABLE IF NOT EXISTS meal (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255),
    date DATE,
    type VARCHAR(20),
    restaurant_id INT,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
    );

CREATE TABLE IF NOT EXISTS reservation (
                                           id SERIAL PRIMARY KEY,
                                           token VARCHAR(255),
    date DATE,
    type VARCHAR(20),
    cancelled BOOLEAN,
    checked_in BOOLEAN,
    restaurant_id INT,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
    );

ALTER SEQUENCE restaurant_id_seq RESTART WITH 7;
ALTER SEQUENCE meal_id_seq RESTART WITH 55;
ALTER SEQUENCE reservation_id_seq RESTART WITH 19;

INSERT INTO restaurant (id, name, capacity) VALUES (1, 'Cantina Santiago', 50);
INSERT INTO restaurant (id, name, capacity) VALUES (2, 'Cantina Crasto', 30);
INSERT INTO restaurant (id, name, capacity) VALUES (3, 'North Restaurant', 60);
INSERT INTO restaurant (id, name, capacity) VALUES (4, 'Center Restaurant', 40);
INSERT INTO restaurant (id, name, capacity) VALUES (5, 'South Restaurant', 80);
INSERT INTO restaurant (id, name, capacity) VALUES (6, 'Restaurant', 100000);

INSERT INTO meal (id, name, date, type, restaurant_id) VALUES
                                                           (5, 'Almoço Santiago', '2025-04-10', 'LUNCH', 1),
                                                           (6, 'Jantar Santiago', '2025-04-10', 'DINNER', 1),
                                                           (7, 'Almoço Crasto', '2025-04-11', 'LUNCH', 2),
                                                           (8, 'Jantar Crasto', '2025-04-11', 'DINNER', 2),
                                                           (9, 'Chicken Caesar Salad', '2025-04-12', 'LUNCH', 3),
                                                           (10, 'Vegetarian Wrap', '2025-04-12', 'LUNCH', 3),
                                                           (11, 'Spaghetti Carbonara', '2025-04-12', 'LUNCH', 3),
                                                           (12, 'Fish Tacos', '2025-04-12', 'LUNCH', 3),
                                                           (13, 'Beef Burger', '2025-04-12', 'LUNCH', 3),
                                                           (14, 'Falafel Bowl', '2025-04-12', 'LUNCH', 3),
                                                           (15, 'Grilled Salmon', '2025-04-12', 'LUNCH', 3),
                                                           (16, 'Pasta Primavera', '2025-04-12', 'DINNER', 3),
                                                           (17, 'Steak Frites', '2025-04-12', 'DINNER', 3),
                                                           (18, 'Lamb Shank', '2025-04-12', 'DINNER', 3),
                                                           (19, 'Vegetable Stir-fry', '2025-04-12', 'DINNER', 3),
                                                           (20, 'Chicken Alfredo', '2025-04-12', 'DINNER', 3),
                                                           (21, 'Shrimp Scampi', '2025-04-12', 'DINNER', 3),
                                                           (22, 'Ratatouille', '2025-04-12', 'DINNER', 3),
                                                           (23, 'Duck Breast', '2025-04-12', 'DINNER', 3),
                                                           (24, 'Chicken Shawarma', '2025-04-13', 'LUNCH', 4),
                                                           (25, 'Cheeseburger', '2025-04-13', 'LUNCH', 4),
                                                           (26, 'Caprese Salad', '2025-04-13', 'LUNCH', 4),
                                                           (27, 'Grilled Veggie Sandwich', '2025-04-13', 'LUNCH', 4),
                                                           (28, 'BBQ Chicken Sandwich', '2025-04-13', 'LUNCH', 4),
                                                           (29, 'Poke Bowl', '2025-04-13', 'LUNCH', 4),
                                                           (30, 'Chicken Quesadilla', '2025-04-13', 'LUNCH', 4),
                                                           (31, 'Seafood Paella', '2025-04-13', 'DINNER', 4),
                                                           (32, 'Beef Wellington', '2025-04-13', 'DINNER', 4),
                                                           (33, 'Chicken Marsala', '2025-04-13', 'DINNER', 4),
                                                           (34, 'Pork Schnitzel', '2025-04-13', 'DINNER', 4),
                                                           (35, 'Vegetarian Lasagna', '2025-04-13', 'DINNER', 4),
                                                           (36, 'Grilled Octopus', '2025-04-13', 'DINNER', 4),
                                                           (37, 'Lobster Roll', '2025-04-13', 'DINNER', 4),
                                                           (38, 'Baked Salmon', '2025-04-13', 'DINNER', 4),
                                                           (39, 'Pulled Pork Sandwich', '2025-04-14', 'LUNCH', 5),
                                                           (40, 'Grilled Chicken Salad', '2025-04-14', 'LUNCH', 5),
                                                           (41, 'Vegetarian Pizza', '2025-04-14', 'LUNCH', 5),
                                                           (42, 'Lamb Gyros', '2025-04-14', 'LUNCH', 5),
                                                           (43, 'Beef Tacos', '2025-04-14', 'LUNCH', 5),
                                                           (44, 'Eggplant Parmesan', '2025-04-14', 'LUNCH', 5),
                                                           (45, 'Pork Belly Sandwich', '2025-04-14', 'LUNCH', 5),
                                                           (46, 'Chateaubriand Steak', '2025-04-14', 'DINNER', 5),
                                                           (47, 'Stuffed Lobster', '2025-04-14', 'DINNER', 5),
                                                           (48, 'Mussels in White Wine', '2025-04-14', 'DINNER', 5),
                                                           (49, 'Chicken Piccata', '2025-04-14', 'DINNER', 5),
                                                           (50, 'Seafood Risotto', '2025-04-14', 'DINNER', 5),
                                                           (51, 'Beef Carpaccio', '2025-04-14', 'DINNER', 5),
                                                           (52, 'Baked Cod', '2025-04-14', 'DINNER', 5),
                                                           (53, 'Peking Duck', '2025-04-14', 'DINNER', 5),
                                                           (54, 'Chicken Shawarma', '2025-04-15', 'LUNCH', 6);

INSERT INTO reservation (id, token, date, type, cancelled, checked_in, restaurant_id) VALUES
                                                                                          (3, 'token123', '2025-04-10', 'LUNCH', false, false, 1),
                                                                                          (4, 'token124', '2025-04-10', 'DINNER', false, false, 1),
                                                                                          (5, 'token125', '2025-04-11', 'LUNCH', false, true, 2),
                                                                                          (6, 'token223', '2025-04-11', 'DINNER', false, false, 2),
                                                                                          (7, 'token323', '2025-04-12', 'LUNCH', false, false, 3),
                                                                                          (8, 'token324', '2025-04-12', 'DINNER', false, false, 3),
                                                                                          (9, 'token423', '2025-04-13', 'LUNCH', false, false, 4),
                                                                                          (10, 'token424', '2025-04-13', 'DINNER', true, false, 4),
                                                                                          (11, 'token523', '2025-04-14', 'LUNCH', false, false, 5),
                                                                                          (12, 'token524', '2025-04-14', 'DINNER', false, false, 5),
                                                                                          (13, 'token623', '2025-04-15', 'LUNCH', false, false, 6),
                                                                                          (14, 'token624', '2025-04-15', 'DINNER', false, false, 6);
