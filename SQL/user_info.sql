
use <your_database>;

CREATE TABLE IF NOT EXISTS user_info(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    addr  VARCHAR (255),
    phone VARCHAR (15),
    email VARCHAR (50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

