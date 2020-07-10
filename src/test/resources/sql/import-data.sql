INSERT INTO sellers (job_id, document, name, salary) VALUES (1, '12312312356', 'Joao', 90500);
INSERT INTO sellers (job_id, document, name, salary) VALUES (1, '12312312356', 'Pedro', 1200);
INSERT INTO sellers (job_id, document, name, salary) VALUES (1, '12312312356', 'Antonio', 5000);
INSERT INTO sellers (job_id, document, name, salary) VALUES (1, '12312312356', 'Fabio', 2100);
INSERT INTO sellers (job_id, document, name, salary) VALUES (1, '12312312356', 'Jose', 1000);
INSERT INTO sellers (job_id, document, name, salary) VALUES (1, '12312312356', 'Caio', 980);

INSERT INTO customers (job_id, document, name, business_area) VALUES (1, '12312312356', 'Andre', 'Rural');
INSERT INTO customers (job_id, document, name, business_area) VALUES (1, '12312312356', 'Maria', 'Rural');
INSERT INTO customers (job_id, document, name, business_area) VALUES (1, '12312312356', 'Antonio', 'Rural');
INSERT INTO customers (job_id, document, name, business_area) VALUES (1, '12312312356', 'Fabio', 'Rural');

INSERT INTO sales (job_id, id, seller) VALUES (1, 1, 'Joao');
INSERT INTO sales (job_id, id, seller) VALUES (1, 2, 'Pedro');

INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (1, 1, 10, 1, 1.5);
INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (1, 1, 11, 2, 2);
INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (1, 1, 12, 3, 180.9);
INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (1, 2, 13, 1, 91.5);
INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (1, 2, 41, 2, 21);
INSERT INTO sale_items (job_id, sale_id, id, quantity, price) VALUES (1, 2, 99, 3, 130.95);

COMMIT;