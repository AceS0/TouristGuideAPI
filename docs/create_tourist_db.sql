CREATE DATABASE IF NOT EXISTS turistguide;
USE turistguide;

CREATE TABLE tourist_attractions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    city VARCHAR(100) NOT NULL
);

CREATE TABLE attraction_tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    attraction_id INT,
    tag VARCHAR(50),
    FOREIGN KEY (attraction_id) REFERENCES tourist_attractions(id) ON DELETE CASCADE
);