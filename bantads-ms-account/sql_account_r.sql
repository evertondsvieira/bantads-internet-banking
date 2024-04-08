create database account_r;
create user 'account_r_user'@'localhost' identified by 'admin';

use account_r;

GRANT ALL PRIVILEGES ON account_r TO 'account_user'@'localhost';

drop table account;
drop table account_transaction;

CREATE TABLE account (
  account_id INTEGER PRIMARY KEY auto_increment,
  account_number INTEGER,
  creation_date DATE,
  limit_value DECIMAL(10, 2),
  manager_name VARCHAR(150),
  manager_cpf VARCHAR(11),
  client_name VARCHAR(150),
  client_cpf VARCHAR(11),
  balance DECIMAL(10, 2),
  situation VARCHAR(50)
);

CREATE TABLE account_transaction (
  account_transaction_id INTEGER PRIMARY KEY auto_increment,
  account_number INTEGER,
  account_client_name VARCHAR(150),
  origin_destination_number INTEGER,
  account_origin_destination_name VARCHAR(150),
  current_balance DECIMAL(10, 2),
  transaction_type VARCHAR(50), -- DEPOSIT/ WITHDRAW/ TRANSFER
  transaction_time TIMESTAMP,
  transaction_value DECIMAL(10, 2)
);

show tables;
-- Grant privileges on each table
GRANT SELECT, INSERT, UPDATE, DELETE ON account TO 'account_user'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON account_transaction TO 'account_user'@'localhost';

-- Inserindo dados de exemplo na tabela account
INSERT INTO account (account_number, creation_date, limit_value, manager_name, manager_cpf, client_name, client_cpf, balance, situation)
VALUES
(123456, '2023-05-15', 5000.00, 'Clienterson', '12345678900', 'Maria Souza', '98765432100', 3500.00, 'OPEN'),
(789012, '2023-08-22', 10000.00, 'Tyrion Lannister', '98765432100', 'Ana Oliveira', '12345678900', 8000.00, 'APPROVED'),
(345678, '2023-11-10', 2000.00, 'Daenerys Targaryen', '45678912300', 'Lucas Pereira', '65432198700', 1500.00, 'REJECTED');

-- Inserindo dados de exemplo na tabela account_transaction
INSERT INTO account_transaction (account_number, account_client_name, origin_destination_number, account_origin_destination_name, current_balance, transaction_type, transaction_time, transaction_value)
VALUES
(123456, 'Clienterson', NULL, NULL, 3500.00, 'DEPOSIT', '2023-05-15 10:30:00', 3500.00),
(789012, 'Clienterson', NULL, NULL, 8000.00, 'DEPOSIT', '2023-08-22 14:45:00', 8000.00),
(345678, 'Clienterson', NULL, NULL, 1500.00, 'DEPOSIT', '2023-11-10 09:20:00', 1500.00),
(123456, 'Clienterson', NULL, NULL, 2500.00, 'WITHDRAW', '2023-05-20 15:00:00', 1000.00),
(789012, 'Clienterson', NULL, NULL, 7500.00, 'WITHDRAW', '2023-09-05 11:10:00', 500.00),
(345678, 'Clienterson', NULL, NULL, 1000.00, 'WITHDRAW', '2023-11-25 08:30:00', 500.00),
(123456, 'Clienterson', 789012, 'Tyrion Lannister', 2000.00, 'TRANSFER', '2023-06-10 09:45:00', 500.00),
(789012, 'Clienterson', 345678, 'Daenerys Targaryen', 7000.00, 'TRANSFER', '2023-09-30 16:20:00', 1000.00);


select * from account;
select * from account_transaction;