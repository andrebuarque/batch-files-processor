INSERT INTO seller (id, job_id, document, name, salary) VALUES (1, 1, '12312312356', 'Joao', 90500);
INSERT INTO seller (id, job_id, document, name, salary) VALUES (2, 1, '12312312356', 'Pedro', 1200);
INSERT INTO seller (id, job_id, document, name, salary) VALUES (3, 1, '12312312356', 'Antonio', 5000);
INSERT INTO seller (id, job_id, document, name, salary) VALUES (4, 1, '12312312356', 'Fabio', 2100);
INSERT INTO seller (id, job_id, document, name, salary) VALUES (5, 1, '12312312356', 'Jose', 1000);
INSERT INTO seller (id, job_id, document, name, salary) VALUES (6, 1, '12312312356', 'Caio', 980);

INSERT INTO customer (id, job_id, document, name, business_area) VALUES (1, 1, '12312312356', 'Andre', 'Rural');
INSERT INTO customer (id, job_id, document, name, business_area) VALUES (2, 1, '12312312356', 'Maria', 'Rural');
INSERT INTO customer (id, job_id, document, name, business_area) VALUES (3, 1, '12312312356', 'Antonio', 'Rural');
INSERT INTO customer (id, job_id, document, name, business_area) VALUES (4, 1, '12312312356', 'Fabio', 'Rural');

INSERT INTO sale (id, job_id, sale_id, seller) VALUES (1, 1, 1, 'Joao');
INSERT INTO sale (id, job_id, sale_id, seller) VALUES (2, 1, 2, 'Pedro');

INSERT INTO sale_item (id, sale_id, item_id, quantity, price) VALUES (1, 1, 10, 1, 1.5);
INSERT INTO sale_item (id, sale_id, item_id, quantity, price) VALUES (2, 1, 11, 2, 2);
INSERT INTO sale_item (id, sale_id, item_id, quantity, price) VALUES (3, 1, 12, 3, 180.9);
INSERT INTO sale_item (id, sale_id, item_id, quantity, price) VALUES (4, 2, 13, 1, 91.5);
INSERT INTO sale_item (id, sale_id, item_id, quantity, price) VALUES (5, 2, 41, 2, 21);
INSERT INTO sale_item (id, sale_id, item_id, quantity, price) VALUES (6, 2, 99, 3, 130.95);