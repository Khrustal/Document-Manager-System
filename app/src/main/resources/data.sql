INSERT INTO users (username, password, email, role) VALUES ('ivanov', '123', 'example1@gmail.com', 'ADMIN');
INSERT INTO users (username, password, email, role) VALUES ('bulkov', '123', 'example2@gmail.com', 'USER');

INSERT INTO doctype (name) VALUES ('Topic');
INSERT INTO doctype (name) VALUES ('Order');
INSERT INTO doctype (name) VALUES ('Fax');

INSERT INTO mail_config (id, status) VALUES (1, false);