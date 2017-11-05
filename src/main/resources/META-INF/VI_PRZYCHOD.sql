create view vi_przychod as select rok, mc ,kierunek.id kierunek ,
	  count(student_id) il_stud, count(student_id) * (czesne / 6) przychod,
	  count(student_id) * 3.25 koszt_na_st from okres_sym join okres_st on
	  rok_mc between EXTRACT(YEAR_MONTH FROM data_od) and EXTRACT(YEAR_MONTH
	  FROM data_do) join kierunek on okres_st.kierunek_id = kierunek.id group
	  by rok, mc ,kierunek.id;