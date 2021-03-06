--   --------------------------------------------------
--   Generated by Enterprise Architect Version 8.0.864
--   Created On : piątek, 24 kwiecień, 2015
--   DBMS       : MySql
--   --------------------------------------------------


SET FOREIGN_KEY_CHECKS=0;


--  Drop Tables, Stored Procedures and Views

DROP TABLE IF EXISTS kierunek;
DROP TABLE IF EXISTS ocena;
DROP TABLE IF EXISTS okres_st;
DROP TABLE IF EXISTS przedmiot;
DROP TABLE IF EXISTS semestr_przedmiot;
DROP TABLE IF EXISTS semestr;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS okres_sym;

--  Create Tables
CREATE TABLE kierunek
(
id NUMERIC(10) NOT NULL,
nazwa VARCHAR(50) NULL,
il_sem NUMERIC(2) NULL,
czesne NUMERIC(11,2) NULL,
PRIMARY KEY (id)
) ;
alter table kierunek add column max_grupa numeric(3);


CREATE TABLE ocena
(
id NUMERIC(10) NULL,
przedmiot_id NUMERIC(10) NULL,
student_id NUMERIC(10) NULL,
ocena NUMERIC(2,1) NULL,
typ_id NUMERIC(10) NULL,
KEY (przedmiot_id),
KEY (student_id)
) ;


CREATE TABLE okres_st
(
id NUMERIC(10) NOT NULL,
kierunek_id NUMERIC(10) NULL,
student_id NUMERIC(10) NULL,
data_od DATE NULL,
data_do DATE NULL,
PRIMARY KEY (id),
KEY (kierunek_id),
KEY (student_id)
) ;


CREATE TABLE przedmiot
(
id NUMERIC(10) NOT NULL,
nazwa VARCHAR(50) NULL,
il_wyk NUMERIC(10) NULL,
il_cw NUMERIC(10) NULL,
PRIMARY KEY (id)
) ;


CREATE TABLE semestr_przedmiot
(
semestr_id NUMERIC(10) NOT NULL,
przedmiot_id NUMERIC(10) NOT NULL,
PRIMARY KEY (semestr_id, przedmiot_id),
KEY (przedmiot_id),
KEY (semestr_id)
) ;


CREATE TABLE semestr
(
id NUMERIC(10) NOT NULL,
kierunek_id NUMERIC(10) NULL,
numer NUMERIC(2) NULL,
PRIMARY KEY (id),
KEY (kierunek_id)
) ;


CREATE TABLE student
(
id NUMERIC(10) NOT NULL,
imie VARCHAR(50) NULL,
nazwisko VARCHAR(50) NULL,
data_ur DATE NULL,
ulica VARCHAR(50) NULL,
nr_domu VARCHAR(10) NULL,
nr_mieszk VARCHAR(10) NULL,
miasto VARCHAR(50) NULL,
kod_poczt VARCHAR(10) NULL,
PRIMARY KEY (id)
) ;

CREATE TABLE okres_sym
(
rok   NUMERIC(4) NOT NULL,
mc    NUMERIC(2) NOT NULL,
rok_mc NUMERIC(6) NOT NULL
) ;

SET FOREIGN_KEY_CHECKS=1;


--  Create Foreign Key Constraints
ALTER TABLE ocena ADD CONSTRAINT FK_ocena_przedmiot
FOREIGN KEY (przedmiot_id) REFERENCES przedmiot (id);

ALTER TABLE ocena ADD CONSTRAINT FK_ocena_student
FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE okres_st ADD CONSTRAINT FK_okres_st_kierunek
FOREIGN KEY (kierunek_id) REFERENCES kierunek (id)
ON DELETE CASCADE;

ALTER TABLE okres_st ADD CONSTRAINT FK_okres_st_student
FOREIGN KEY (student_id) REFERENCES student (id)
ON DELETE CASCADE;

ALTER TABLE semestr_przedmiot ADD CONSTRAINT FK_semestr_przedmiot_przedmiot
FOREIGN KEY (przedmiot_id) REFERENCES przedmiot (id);

ALTER TABLE semestr_przedmiot ADD CONSTRAINT FK_semestr_przedmiot_semestr
FOREIGN KEY (semestr_id) REFERENCES semestr (id);

ALTER TABLE semestr ADD CONSTRAINT FK_semestr_kierunek
FOREIGN KEY (kierunek_id) REFERENCES kierunek (id);


alter table kierunek add column max_grupa numeric(3);

