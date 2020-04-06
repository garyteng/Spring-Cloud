
use <your_database>;

CREATE TABLE IF NOT EXISTS notebooks(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    note VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);