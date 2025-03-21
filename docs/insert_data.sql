USE turistguide;

INSERT INTO tourist_attractions (name, description, city) VALUES
('CopenHill', 'En urban skibakke på et forbrændingsanlæg.', 'København'),
('Reffen Street Food', 'Kreativ mad- og kulturhub.', 'København'),
('FÆNGSLET', 'Tidligere fængsel i Horsens, nu museum.', 'Horsens'),
('Aarhus Festuge', 'Årlig kulturfestival i Aarhus.', 'Aarhus');

INSERT INTO attraction_tags (attraction_id, tag) VALUES
(1, 'Sport'), (1, 'Udendørs'), (1, 'Eventyr'), (1, 'Udsigt'),
(2, 'Mad'), (2, 'Street Food'), (2, 'Kultur'), (2, 'Socialt'),
(3, 'Historie'), (3, 'Museum'), (3, 'Escape Room'), (3, 'Koncerter'),
(4, 'Festival'), (4, 'Kultur'), (4, 'Musik'), (4, 'Kunst');
