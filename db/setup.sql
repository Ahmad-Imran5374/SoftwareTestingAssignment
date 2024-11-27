CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    name TEXT NOT NULL
);

INSERT INTO users (email, password, name) VALUES
('johndoe@example.com', 'password123', 'John Doe'),
('janedoe@example.com', 'securepassword', 'Jane Doe');
