INSERT INTO item (name, quantity) VALUES ('Laptop', 50);
INSERT INTO item (name, quantity) VALUES ('Phone', 215);
INSERT INTO item (name, quantity) VALUES ('Refrigerator', 15);
INSERT INTO item (name, quantity) VALUES ('Router', 30);
INSERT INTO user (id, username, password) VALUES (unhex(replace(uuid(), '-', '')), 'admin', 'admin');