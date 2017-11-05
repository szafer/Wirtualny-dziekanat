create or replace view vi_koszt as select distinct rok, mc
	  ,vi_kierunek.kierunek kierunek_, data_od, sum(il_wyk/ 6) * 40
	  koszt_wykladw from okres_sym join vi_kierunek on rok_mc between
	  EXTRACT(YEAR_MONTH FROM data_od) and EXTRACT(YEAR_MONTH FROM data_do)
	  join semestr on semestr.kierunek_id = vi_kierunek.kierunek join
	  semestr_przedmiot on semestr.id = semestr_przedmiot.semestr_id join
	  przedmiot on przedmiot.id = semestr_przedmiot.przedmiot_id where rok_mc
	  between EXTRACT(YEAR_MONTH FROM DATE_ADD(data_od, INTERVAL (numer -1)*6
	  MONTH)) and EXTRACT(YEAR_MONTH FROM DATE_ADD(data_od, INTERVAL numer*6
	  MONTH)) group by rok, mc ,kierunek , data_od order by 1 ,2,3,4;