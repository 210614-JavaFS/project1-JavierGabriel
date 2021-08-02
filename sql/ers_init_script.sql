--CREATE DATABASE ERS;
--DROP TABLE IF EXISTS reimbursements;
--DROP TABLE IF EXISTS reimbursement_status;
--DROP TABLE IF EXISTS reimbursement_types;
--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS roles;

CREATE TABLE roles(
role_id SERIAL PRIMARY KEY,
role_type VARCHAR(10) NOT NULL
);

CREATE TABLE users(
user_id SERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
user_password VARCHAR(70) NOT NULL,
first_name VARCHAR(100) NOT NULL,
last_name VARCHAR(100) NOT NULL,
user_email VARCHAR(150) NOT NULL UNIQUE,
user_role INTEGER NOT NULL REFERENCES roles(role_id)
);

CREATE TABLE reimbursement_types(
reimb_type_id SERIAL PRIMARY KEY,
reimb_type VARCHAR(10) NOT NULL
);

CREATE TABLE reimbursement_status(
reimb_status_id SERIAL PRIMARY KEY,
reimb_status VARCHAR(10) NOT NULL 
);

DROP TABLE IF EXISTS reimbursements;
CREATE TABLE reimbursements(
reimb_id SERIAL PRIMARY KEY,
amount NUMERIC(21,2) NOT NULL,
submitted TIMESTAMPTZ NOT NULL DEFAULT NOW(),
resolved TIMESTAMPTZ DEFAULT NULL,
description VARCHAR(250) DEFAULT NULL,
receipt BYTEA DEFAULT NULL,
author INTEGER NOT NULL REFERENCES users(user_id),
resolver INTEGER REFERENCES users(user_id) DEFAULT NULL,
status_id INTEGER NOT NULL REFERENCES reimbursement_status(reimb_status_id),
reimb_type INTEGER NOT NULL REFERENCES reimbursement_types(reimb_type_id)
);



INSERT INTO roles(role_type)
VALUES('employee'),
('f_manager');

INSERT INTO reimbursement_types(
reimb_type
)
VALUES('lodging'),
('travel'),
('food'),
('other');


INSERT INTO reimbursement_status(reimb_status)
VALUES('pending'),
('denied'),
('accepted');

--INSERTION OF USERS WITH ENCRYPTED PASSWORD 'pass'
/*
INSERT INTO users(username,
user_password,
first_name,
last_name,
user_email,
user_role)
VALUES('javier', '$2a$04$u0JNLxAcw8GCJOJlwTsxHeUcUsebSHWByzBYi/zSASr97M2gUJuxy', 'javier', 'gonzalez', 'javier@revature.net', 2);

INSERT INTO users(username,
user_password,
first_name,
last_name,
user_email,
user_role)
VALUES('tim', '$2a$04$cMqxua9OAbPw8E5RlDSGfuZbX7Qs9qfj0c8IkFJZ1QMeRawCPv9H.', 'Tim', 'Gattie', 'tim@revature.net', 1);

INSERT INTO users(username,
user_password,
first_name,
last_name,
user_email,
user_role)
VALUES('jon', '$2a$04$B9M141.xNW9fwJ8xvZ7ISOAOJm0i6kzmPc6OwwvJDj2z96/U5fFki', 'jon', 'tron', 'jon@revature.net', 1);

*/