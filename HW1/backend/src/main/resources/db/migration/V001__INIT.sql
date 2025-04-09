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

ALTER SEQUENCE restaurant_id_seq RESTART WITH 3;
ALTER SEQUENCE meal_id_seq RESTART WITH 5;
ALTER SEQUENCE reservation_id_seq RESTART WITH 3;

INSERT INTO restaurant (id, name, capacity) VALUES (1, 'Cantina Santiago', 50);
INSERT INTO restaurant (id, name, capacity) VALUES (2, 'Cantina Crasto', 30);

INSERT INTO meal (id, name, date, type, restaurant_id) VALUES (1, 'Almoço Santiago', '2025-04-10', 'LUNCH', 1),
                                                           (2, 'Jantar Santiago', '2025-04-10', 'DINNER', 1),
                                                           (3, 'Almoço Crasto', '2025-04-11', 'LUNCH', 2),
                                                           (4, 'Jantar Crasto', '2025-04-11', 'DINNER', 2);

INSERT INTO reservation (id, token, date, type, cancelled, checked_in, restaurant_id) VALUES (1, 'token123', '2025-04-10', 'LUNCH', false, false, 1),
                                                                                          (2, 'token456', '2025-04-11', 'DINNER', false, true, 2);
