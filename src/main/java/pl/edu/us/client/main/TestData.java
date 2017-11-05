package pl.edu.us.client.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import pl.edu.us.client.symulacja.przychodykoszty.ui.Data;
import pl.edu.us.shared.model.PrzychodKoszt;
import pl.edu.us.shared.model.User;

public class TestData {

	private final List<User> lista = new ArrayList<User>();
	private final List<Data> data = new ArrayList<Data>();
	private final List<Data> data2 = new ArrayList<Data>();
	private final List<PrzychodKoszt> dane = new ArrayList<PrzychodKoszt>();
	private final List<PrzychodKoszt> daneRok = new ArrayList<PrzychodKoszt>();
	private final List<PrzychodKoszt> daneKierunki = new ArrayList<PrzychodKoszt>();
	public TestData() {
		User s1 = new User();
		s1.setId(1);
		s1.setNazwisko("Janik");
		s1.setImie("Piotr");
		User s2 = new User();
		s2.setId(2);
		s2.setNazwisko("Marek");
		s2.setImie("Szafraniec");
		lista.add(s1);
		lista.add(s2);

		Data d1 = new Data("R1", 1, 2, 1, 2, 1, 2, 1, 2, 1);
		Data d2 = new Data("R2", 10, 12, 11, 12, 11, 12, 11, 12, 11);
		data.add(d1);
		data.add(d2);
		Data d12 = new Data("R1", 13, 2, 15, 2, 11, 2, 1, 2, 1);
		Data d22 = new Data("R2", 10, 1, 11, 1, 11, 12, 1, 12, 1);
		data2.add(d12);
		data2.add(d22);
		PrzychodKoszt koszt1 = new PrzychodKoszt(1, "Styczeń", 1, 2014, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(4.0), "Psychologia");
		PrzychodKoszt koszt2 = new PrzychodKoszt(2, "Luty", 2, 2014, BigDecimal.valueOf(3.0), BigDecimal.valueOf(4.0),
				"Psychologia");
		PrzychodKoszt koszt3 = new PrzychodKoszt(3, "Marzec", 3, 2014, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(1.0), "Psychologia");
		PrzychodKoszt koszt4 = new PrzychodKoszt(4, "Kwiecień", 4, 2014, BigDecimal.valueOf(5.0),
				BigDecimal.valueOf(3.0), "Psychologia");
		PrzychodKoszt koszt5 = new PrzychodKoszt(5, "Maj", 5, 2014, BigDecimal.valueOf(10.0), BigDecimal.valueOf(6.0),
				"Informatyka");
		PrzychodKoszt koszt6 = new PrzychodKoszt(6, "Czerwiec", 6, 2014, BigDecimal.valueOf(8.0),
				BigDecimal.valueOf(6.0), "Informatyka");
		PrzychodKoszt koszt7 = new PrzychodKoszt(7, "Lipiec", 7, 2014, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(6.0), "Informatyka");
		PrzychodKoszt koszt8 = new PrzychodKoszt(8, "Sierpień", 8, 2014, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(5.0), "Informatyka");
		PrzychodKoszt koszt9 = new PrzychodKoszt(9, "Wrzesień", 9, 2014, BigDecimal.valueOf(4.0),
				BigDecimal.valueOf(7.0), "Informatyka");
		PrzychodKoszt koszt10 = new PrzychodKoszt(10, "Październik", 10, 2014, BigDecimal.valueOf(6.0),
				BigDecimal.valueOf(8.0), "Administracja");
		PrzychodKoszt koszt11 = new PrzychodKoszt(11, "Listopad", 11, 2014, BigDecimal.valueOf(6.0),
				BigDecimal.valueOf(9.0), "Administracja");
		PrzychodKoszt koszt12 = new PrzychodKoszt(12, "Grudzień", 12, 2014, BigDecimal.valueOf(8.0),
				BigDecimal.valueOf(12.0), "Administracja");
		PrzychodKoszt koszt13 = new PrzychodKoszt(13, "Marzec", 3, 2015, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(1.0), "Administracja");
		PrzychodKoszt koszt14 = new PrzychodKoszt(14, "Kwiecień", 4, 2016, BigDecimal.valueOf(5.0),
				BigDecimal.valueOf(3.0), "Administracja");
		PrzychodKoszt koszt15 = new PrzychodKoszt(15, "Maj", 5, 2017, BigDecimal.valueOf(10.0),
				BigDecimal.valueOf(6.0), "Administracja");
		PrzychodKoszt koszt16 = new PrzychodKoszt(16, "Czerwiec", 6, 2018, BigDecimal.valueOf(8.0),
				BigDecimal.valueOf(6.0), "Administracja");
		PrzychodKoszt koszt17 = new PrzychodKoszt(17, "Lipiec", 7, 2015, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(6.0), "Administracja");
		PrzychodKoszt koszt18 = new PrzychodKoszt(18, "Sierpień", 8, 2016, BigDecimal.valueOf(2.0),
				BigDecimal.valueOf(5.0), "Administracja");
		PrzychodKoszt koszt19 = new PrzychodKoszt(19, "Wrzesień", 9, 2017, BigDecimal.valueOf(4.0),
				BigDecimal.valueOf(7.0), "Administracja");

		dane.add(koszt1);
		dane.add(koszt2);
		dane.add(koszt3);
		dane.add(koszt4);
		dane.add(koszt5);
		dane.add(koszt6);
		dane.add(koszt7);
		dane.add(koszt8);
		dane.add(koszt9);
		dane.add(koszt10);
		dane.add(koszt11);
		dane.add(koszt12);
		dane.add(koszt13);
		dane.add(koszt14);
		dane.add(koszt15);
		dane.add(koszt16);
		dane.add(koszt17);
		dane.add(koszt18);
		dane.add(koszt19);
		Set<Integer> lata = Sets.newHashSet();
		Set<String> kierunki = Sets.newHashSet();
		Map<KluczPKKierunek, PrzychodKoszt> mapa = Maps.newHashMap();
		for (PrzychodKoszt pk : dane) {
			lata.add(pk.getRok());
			kierunki.add(pk.getKierunek());
			KluczPKKierunek klucz = new KluczPKKierunek(pk.getRok(), pk.getKierunek());
			mapa.put(
					klucz,
					new PrzychodKoszt(pk.getId(), "", 0, pk.getRok(), BigDecimal.ZERO, BigDecimal.ZERO, pk
							.getKierunek()));
		}
		List<Integer> l = new ArrayList<Integer>();
		for (Object obj : lata.toArray()) {
			l.add((Integer) obj);
		}
		int licznik = 0;
		Collections.sort(l);
		// dane do pokazania dla symulacji rocznej
		for (Integer i : l) {
			licznik++;
			PrzychodKoszt newPK = new PrzychodKoszt(licznik, "", 0, i, BigDecimal.ZERO, BigDecimal.ZERO, "");
			BigDecimal koszt = BigDecimal.ZERO;
			BigDecimal przychod = BigDecimal.ZERO;
			for (PrzychodKoszt pk : dane) {
				if (pk.getRok() == i) {
					koszt = koszt.add(pk.getKoszt());
				przychod = przychod.add(pk.getPrzychod());
				}
				KluczPKKierunek klucz = new KluczPKKierunek(pk.getRok(), pk.getKierunek());
				if (mapa.containsKey(klucz)) {
					PrzychodKoszt znaleziony = mapa.get(klucz);
					znaleziony.setKoszt(znaleziony.getKoszt().add(pk.getKoszt()));
					znaleziony.setPrzychod(znaleziony.getPrzychod().add(pk.getPrzychod()));
				}
			}
			newPK.setKoszt(koszt);
			newPK.setPrzychod(przychod);
			daneRok.add(newPK);


		}
		// dane do pokazania w symulacji z rozbiciem na kierunki

		daneKierunki.addAll(mapa.values());

	}

	public List<User> getLista() {
		return lista;
	}

	public List<Data> getData() {
		return data;
	}

	public List<Data> getData2() {
		return data2;
	}

	public List<PrzychodKoszt> getDane() {
		return dane;
	}

	public List<PrzychodKoszt> getDaneRok() {
		return daneRok;
	}

	public List<PrzychodKoszt> getDaneKierunki() {
		return daneKierunki;
	}

	private class KluczPKKierunek {
		private Integer rok;
		private String kierunek;

		public KluczPKKierunek() {

		}

		public KluczPKKierunek(Integer rok, String kierunek) {
			super();
			this.rok = rok;
			this.kierunek = kierunek;
		}

		public Integer getRok() {
			return rok;
		}

		public void setRok(Integer rok) {
			this.rok = rok;
		}

		public String getKierunek() {
			return kierunek;
		}

		public void setKierunek(String kierunek) {
			this.kierunek = kierunek;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((kierunek == null) ? 0 : kierunek.hashCode());
			result = prime * result + ((rok == null) ? 0 : rok.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			KluczPKKierunek other = (KluczPKKierunek) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (kierunek == null) {
				if (other.kierunek != null)
					return false;
			} else if (!kierunek.equals(other.kierunek))
				return false;
			if (rok == null) {
				if (other.rok != null)
					return false;
			} else if (!rok.equals(other.rok))
				return false;
			return true;
		}

		private TestData getOuterType() {
			return TestData.this;
		}

	}


}
