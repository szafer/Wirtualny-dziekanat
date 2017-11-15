CREATE TABLE U_USER(	ID NUMBER(6) NOT NULL , IMIE VARCHAR2(30), NAZWISKO VARCHAR2(30), 
LOGIN VARCHAR2(30), PASSWORD VARCHAR2(30));

 

CREATE TABLE ROLA( ID NUMBER(6) NOT NULL , 
					USER_ID NUMBER(6) NOT NULL,
					NAZWA VARCHAR2(30),
					KOD VARCHAR2(10) NOT NULL );


CREATE TABLE KIERUNEK(ID NUMBER(6) NOT NULL , NAZWA VARCHAR2(30), ILOSC_SEM NUMBER(6), CZESNE DECIMAL(11,2)
);


CREATE TABLE STUDENT(ID NUMBER(6) NOT NULL , IMIE VARCHAR2(30), NAZWISKO VARCHAR2(30), DATA_UR DATE,ULICA VARCHAR2(30),
NR_DOMU NUMBER(3),	NR_MIESZK NUMBER(3),	MIASTO VARCHAR2(30),	
KOD_POCZT VARCHAR2(30), EMAIL VARCHAR2(30),	LOGIN VARCHAR2(30),	PASS VARCHAR2(30),	
PLEC NUMBER(1),	NR_ALBUMU NUMBER(6));

CREATE TABLE OKRES_ST(ID NUMBER(6) NOT NULL , KIERUNEK_ID NUMBER(6), STUDENT_ID NUMBER(6), DATA_OD DATE, DATA_DO DATE);


CREATE TABLE PRZEDMIOT(	ID NUMBER(6) NOT NULL , NAZWA VARCHAR2(30), IL_WYK NUMBER(6), IL_CW NUMBER(6));


CREATE TABLE SEMESTR(ID NUMBER(6) NOT NULL , KIERUNEK_ID NUMBER(6) NOT NULL, NUMER NUMBER(2) NOT NULL
);
CREATE TABLE SEMESTR_PRZEDMIOT(SEMESTR_ID NUMBER(6) NOT NULL, PRZEDMIOT_ID NUMBER(6) NOT NULL
);

CREATE TABLE OCENA(	ID NUMBER(6) NOT NULL , PRZEDMIOT_ID NUMBER(6) NOT NULL, 
STUDENT_ID NUMBER(6) NOT NULL, OCENA DECIMAL(2,1), TYP_ID NUMBER(1));
--
--create or replace view vi_przychod_koszt as select   a.rok, a.mc, 
--CASE  a.mc WHEN 1 THEN  'Styczeń' WHEN 2 THEN  'Luty' WHEN 3 THEN  'Marzec' 
--WHEN 4 THEN  'Kwiecień' WHEN 5 THEN  'Maj' WHEN 6 THEN  'Czerwiec' WHEN 7 THEN  'Lipiec'
--WHEN 8 THEN  'Sierpień' WHEN 9 THEN  'Wrzesień' WHEN 10 THEN  'Październik' WHEN 11 THEN  'Listopad'
--ELSE 'Grudzień' END AS miesieac , c.nazwa kierunke,  b.przychod, a.koszt_wykladw koszt , 
--b.przychod - a.koszt_wykladw dochod, b.koszt_na_st from vi_koszt a join vi_przychod b 
--on b.rok= a.rok and b.mc = a.mc and a.kierunek_id =  b.kierunek join kierunek c on c.id = a.kierunek_id; 
--
--	
--	  create view vi_przychod as select rok, mc ,kierunek.id kierunek ,
--	  count(student_id) il_stud, count(student_id) * (czesne / 6) przychod,
--	  count(student_id) * 3.25 koszt_na_st from okres_sym join okres_st on
--	  rok_mc between EXTRACT(YEAR_MONTH FROM data_od) and EXTRACT(YEAR_MONTH
--	  FROM data_do) join kierunek on okres_st.kierunek_id = kierunek.id group
--	  by rok, mc ,kierunek.id;
--	  
--	  create or replace view vi_kierunek as select distinct kierunek_id
--	  kierunek , data_od, data_do from okres_st;
--	  
--	  create or replace view vi_koszt as select distinct rok, mc
--	  ,vi_kierunek.kierunek kierunek_, data_od, sum(il_wyk/ 6) * 40
--	  koszt_wykladw from okres_sym join vi_kierunek on rok_mc between
--	  EXTRACT(YEAR_MONTH FROM data_od) and EXTRACT(YEAR_MONTH FROM data_do)
--	  join semestr on semestr.kierunek_id = vi_kierunek.kierunek join
--	  semestr_przedmiot on semestr.id = semestr_przedmiot.semestr_id join
--	  przedmiot on przedmiot.id = semestr_przedmiot.przedmiot_id where rok_mc
--	  between EXTRACT(YEAR_MONTH FROM DATE_ADD(data_od, INTERVAL (numer -1)*6
--	  MONTH)) and EXTRACT(YEAR_MONTH FROM DATE_ADD(data_od, INTERVAL numer*6
--	  MONTH)) group by rok, mc ,kierunek , data_od order by 1 ,2,3,4;
