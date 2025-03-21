CREATE TABLE IF NOT EXISTS car (
    car_id SERIAL PRIMARY KEY,
    maker VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    segment CHAR(1),
    motortype VARCHAR(5),
    transmission VARCHAR(255)
    );
