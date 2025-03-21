CREATE TABLE IF NOT EXISTS car (
    car_id SERIAL PRIMARY KEY,
    maker VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    segment CHAR(1),
    motortype VARCHAR(5),
    transmission VARCHAR(255)
    );

INSERT INTO car (maker, model, segment, motortype, transmission) VALUES
('Honda', 'Civic', 'C', 'I4', 'Automatic'),
('Mazda', 'MX-5', 'S', 'I4', 'Manual'),
('BMW', 'M3', 'D', 'I6', 'Manual'),
('Mercedes', 'A-Class', 'C', 'I4', 'Automatic'),
('Nissan', 'Leaf', 'C', 'EV', 'Automatic'),
('Tesla', 'Model 3', 'D', 'EV', 'Automatic'),
('Alfa Romeo', 'Giulia', 'D', 'I4', 'Automatic'),
('Audi', 'A4', 'D', 'I4', 'Automatic'),
('Kia', 'Sportage', 'C', 'I4', 'Automatic'),
('Hyundai', 'Elantra', 'C', 'I4', 'Manual');