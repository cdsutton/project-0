DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;

CREATE TABLE clients (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL
);

CREATE TABLE accounts (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	account_name VARCHAR(255) NOT NULL,
	amount INTEGER NOT NULL,
	client_id INTEGER NOT NULL,
	CONSTRAINT fk_accounts_clients FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE
);

INSERT INTO clients (first_name, last_name)
VALUES
('Annie', 'Zebra'),
('Bob', 'Yoshi'),
('Chris', 'Xylophone');

SELECT *
FROM clients;

INSERT INTO accounts (account_name, client_id, amount)
VALUES
('checking', 1, 1500),
('savings', 1, 3000),
('checking', 2, 200),
('savings', 3, 2000);

SELECT *
FROM accounts;

SELECT c.id AS client_id, c.first_name, c.last_name, a.id AS account_id, a.account_name, a.amount
FROM clients c
INNER JOIN accounts a
ON a.client_id = c.id;