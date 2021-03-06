INSERT INTO warehouse (id, name) VALUES (uuid(), 'warehouse-1');
INSERT INTO warehouse (id, name) VALUES (uuid(), 'warehouse-2');
INSERT INTO item (name, quantity, warehouse_id) VALUES ('Laptop', 50, (SELECT wh.id FROM warehouse wh WHERE wh.name = 'warehouse-1'));
INSERT INTO item (name, quantity, warehouse_id) VALUES ('Phone', 215, (SELECT wh.id FROM warehouse wh WHERE wh.name = 'warehouse-1'));
INSERT INTO item (name, quantity, warehouse_id) VALUES ('Refrigerator', 15, (SELECT wh.id FROM warehouse wh WHERE wh.name = 'warehouse-1'));
INSERT INTO item (name, quantity, warehouse_id) VALUES ('Router', 30, (SELECT wh.id FROM warehouse wh WHERE wh.name = 'warehouse-2'));
INSERT INTO user (username, password, role, warehouse_id) VALUES ('admin', SHA2('admin', 256), 2, (SELECT wh.id FROM warehouse wh WHERE wh.name = 'warehouse-1'));
INSERT INTO user (username, password, role, warehouse_id) VALUES ('test', SHA2('test', 256), 0, (SELECT wh.id FROM warehouse wh WHERE wh.name = 'warehouse-1'));