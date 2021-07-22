DROP TABLE IF EXISTS jc_student_child;
DROP TABLE IF EXISTS jc_student_order;
DROP TABLE IF EXISTS jc_register_office;
DROP TABLE IF EXISTS jc_passport_office;
DROP TABLE IF EXISTS jc_country_struct;
DROP TABLE IF EXISTS jc_street;

CREATE TABLE jc_street
(
    street_code integer NOT NULL,
    street_name varchar(300),

    PRIMARY KEY (street_code)
);

CREATE TABLE jc_country_struct
(
    area_id char(12) NOT NULL,
    area_name varchar(200),
    PRIMARY KEY (area_id)
);

CREATE TABLE jc_passport_office
(
    p_office_id integer NOT NULL,
    p_office_area_id char(12) NOT NULL,
    p_office_name varchar(200),
    PRIMARY KEY (p_office_id),
    FOREIGN KEY (p_office_area_id) REFERENCES jc_country_struct (area_id) ON DELETE RESTRICT
);

CREATE TABLE jc_register_office
(
    r_office_id integer NOT NULL,
    r_office_area_id char(12) NOT NULL,
    r_office_name varchar(200),
    PRIMARY KEY (r_office_id),
    FOREIGN KEY (r_office_area_id) REFERENCES jc_country_struct (area_id) ON DELETE RESTRICT
);

CREATE TABLE jc_student_order
(
    student_order_id SERIAL,
    h_sur_name varchar(100) NOT NULL,
    h_given_name varchar(100) NOT NULL,
    h_patronymic varchar(100) NOT NULL ,
    h_date_of_birth date NOT NULL ,
    h_passport_serial varchar(10) NOT NULL ,
    h_passport_number varchar(10) NOT NULL ,
    h_passport_date date NOT NULL ,
    h_passport_office_id integer NOT NULL ,
    h_post_index varchar(10),
    h_street_code integer NOT NULL ,
    h_building varchar(10) NOT NULL ,
    h_extension varchar(10),
    h_apartment varchar(10),
    w_sur_name varchar(100) NOT NULL,
    w_given_name varchar(100) NOT NULL,
    w_patronymic varchar(100) NOT NULL ,
    w_date_of_birth date NOT NULL ,
    w_passport_serial varchar(10) NOT NULL ,
    w_passport_number varchar(10) NOT NULL ,
    w_passport_date date NOT NULL ,
    w_passport_office_id integer NOT NULL ,
    w_post_index varchar(10),
    w_street_code integer NOT NULL ,
    w_building varchar(10) NOT NULL ,
    w_extension varchar(10),
    w_apartment varchar(10),
    certificate_id varchar(20) NOT NULL ,
    register_office_id integer NOT NULL ,
    marriage_date date NOT NULL ,
    PRIMARY KEY (student_order_id),
    FOREIGN KEY (h_street_code) REFERENCES jc_street (street_code)  ON DELETE RESTRICT,
    FOREIGN KEY (w_street_code) REFERENCES jc_street (street_code) ON DELETE RESTRICT,
    FOREIGN KEY (register_office_id) REFERENCES jc_register_office (r_office_id) ON DELETE RESTRICT
);

CREATE TABLE jc_student_child
(
    student_child_id SERIAL,
    student_order_id integer NOT NULL ,
    c_sur_name varchar(100) NOT NULL,
    c_given_name varchar(100) NOT NULL,
    c_patronymic varchar(100) NOT NULL ,
    c_date_of_birth date NOT NULL ,
    c_certificate_number varchar(10) NOT NULL ,
    c_certificate_date date NOT NULL ,
    c_register_office_id integer NOT NULL ,
    c_post_index varchar(10),
    c_street_code integer NOT NULL ,
    c_building varchar(10) NOT NULL ,
    c_extension varchar(10),
    c_apartment varchar(10),
    PRIMARY KEY (student_child_id),
    FOREIGN KEY (c_street_code) REFERENCES jc_street (street_code)  ON DELETE RESTRICT,
    FOREIGN KEY (c_register_office_id) REFERENCES jc_register_office (r_office_id) ON DELETE RESTRICT
);







INSERT INTO jc_street (street_code, street_name) VALUES (1, 'Street First');
INSERT INTO jc_street (street_code, street_name) VALUES (2, 'Street Second'), (3,'Third');

UPDATE jc_street SET street_name = 'Super';
UPDATE jc_street SET street_name = 'First Street' WHERE street_code = 1;
UPDATE jc_street SET street_name = 'First Street', street_code = 10 WHERE street_code = 1;

DELETE FROM jc_street WHERE street_code = 10;
DELETE FROM jc_street;

SELECT * FROM jc_street;

SELECT street_name FROM jc_street;
SELECT street_code, street_name FROM jc_street;
SELECT street_code, street_name FROM jc_street WHERE street_code = 3 OR street_code = 1;
SELECT street_code, street_name FROM jc_street WHERE street_code IN (1, 3);
SELECT street_code sc, street_name sn FROM jc_street WHERE street_code IN (1, 3) ORDER BY street_code ASC;
SELECT street_code sc, street_name sn FROM jc_street WHERE street_code IN (1, 3) ORDER BY street_code DESC ;

SELECT street_code FROM (SELECT street_code, street_name FROM jc_street WHERE street_code IN (1, 3)) s1;
SELECT s1.* FROM (SELECT street_code, street_name FROM jc_street WHERE street_code IN (1, 3)) s1;

SELECT street_code, street_name FROM jc_street WHERE street_name LIKE '%Firs%';
SELECT street_code, street_name FROM jc_street WHERE UPPER(street_name) LIKE UPPER('%firs%');

SELECT * FROM jc_country_struct WHERE area_id LIKE '__0000000000' AND area_id <> '';
SELECT * FROM jc_country_struct WHERE area_id LIKE '02___0000000' AND area_id <> '020000000000';
SELECT * FROM jc_country_struct WHERE area_id LIKE '02001___0000' AND area_id <> '020010000000';
SELECT * FROM jc_country_struct WHERE area_id LIKE '02001001____' AND area_id <> '020010010000';