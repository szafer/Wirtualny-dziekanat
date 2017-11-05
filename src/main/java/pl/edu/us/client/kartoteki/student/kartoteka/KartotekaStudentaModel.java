package pl.edu.us.client.kartoteki.student.kartoteka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.client.accesproperties.KierunekProperties;
import pl.edu.us.client.accesproperties.StudentProperties;
import pl.edu.us.shared.enums.Plec;
import pl.edu.us.shared.enums.TypSemestru;
import pl.edu.us.shared.model.Kierunek;
import pl.edu.us.shared.model.OkresStudiow;
import pl.edu.us.shared.model.Student;

@Singleton
public class KartotekaStudentaModel {

	private final ListStore<Plec> storePlec;
	private ListStore<Student> students;
	private ListStore<Student> doUsuniecia;
	private ListStore<Kierunek> kierunki;
	private ListStore<Kierunek> kierunkiStudenta;
	private Student selected;
	private List<OkresStudiow> okresyStudenta;
	private List<Kierunek> fakedKkierunki;

	interface PlecProperties extends PropertyAccess<Plec> {
		ModelKeyProvider<Plec> kod();

		LabelProvider<Plec> nazwa();
	}

	PlecProperties plecProp = GWT.create(PlecProperties.class);
	StudentProperties studentProp = GWT.create(StudentProperties.class);
	KierunekProperties kieruProp = GWT.create(KierunekProperties.class);

	@Inject
	public KartotekaStudentaModel() {
		fakedKkierunki = new ArrayList<Kierunek>();
		okresyStudenta = new ArrayList<OkresStudiow>();
		storePlec = new ListStore<Plec>(plecProp.kod());
		storePlec.addAll(Arrays.asList(Plec.values()));
		students = new ListStore<Student>(studentProp.key());
		doUsuniecia = new ListStore<Student>(studentProp.key());
		kierunki = new ListStore<Kierunek>(kieruProp.key());
		kierunkiStudenta = new ListStore<Kierunek>(kieruProp.key());
	}

	public void wyczysc() {
		fakedKkierunki.clear();
		okresyStudenta.clear();
		storePlec.clear();
		students.clear();
		doUsuniecia.clear();
		kierunki.clear();
		kierunkiStudenta.clear();
	}
	public ListStore<Plec> getStorePlec() {
		return storePlec;
	}

	public PlecProperties getPlecProp() {
		return plecProp;
	}

	public ListStore<Student> getStudents() {
		return students;
	}

	public StudentProperties getStudentProp() {
		return studentProp;
	}

	public ListStore<Student> getDoUsunieccia() {
		return doUsuniecia;
	}

	public ListStore<Kierunek> getKierunki() {
		return kierunki;
	}

	public KierunekProperties getKieruProp() {
		return kieruProp;
	}

	public Student getSelected() {
		return selected;
	}

	public void setSelected(Student selected) {
		this.selected = selected;
		if (selected != null) {
			okresyStudenta = selected.getOkresy();
		} else {
			okresyStudenta = new ArrayList<OkresStudiow>();
		}
	}

	public void zaznaczKerunki() {
		for (Kierunek kier : kierunki.getAll()) {
			for (OkresStudiow ok : selected.getOkresy()) {
				if (ok.getKierunek().getId() == kier.getId()) {
					kier.setRokOd(ok.getDataOd().getYear() + 1900);
					kier.setTypSemestru(ok.getDataOd().getMonth() > 6 ? TypSemestru.LETNI : TypSemestru.ZIMOWY);
				}
			}
			kierunki.commitChanges();
		}
	}

	public void czyscKierunki() {
		for (Kierunek kier : kierunki.getAll()) {
			kier.setTypSemestru(null);
			kier.setRokOd(null);
		}
		kierunki.commitChanges();
	}

	@SuppressWarnings("deprecation")
	public void towrzOkresy() {
		for (Kierunek kier : kierunki.getAll()) {
			boolean jest = false;
			for (OkresStudiow ok : okresyStudenta) {
				if (ok.getKierunek().getId() == kier.getId())
					jest = true;
			}
			if (kier.getRokOd() != null && !jest) {
				OkresStudiow ok = new OkresStudiow();
				ok.setKierunek(kier);
				ok.setStudent(selected);
				Date dataOd = new Date();
				dataOd.setYear(kier.getRokOd() - 1900);
				dataOd.setDate(1);
				dataOd.setMonth(kier.getTypSemestru() == TypSemestru.LETNI ? 3 : 9);
				Date dataDo = new Date();
				dataDo.setDate(1);
				dataDo.setMonth(dataOd.getMonth() + (kier.getIloscSemestrow() % 2 == 0 ? 0 : 6));
				dataDo.setYear(dataOd.getYear()
						+ (kier.getIloscSemestrow() % 2 == 0 ? kier.getIloscSemestrow() / 2
								: kier.getTypSemestru() == TypSemestru.LETNI ? (kier.getIloscSemestrow() + 1) / 2
										: kier.getIloscSemestrow() - 1) / 2);
				ok.setDataOd(dataOd);
				ok.setDatado(dataDo);
				okresyStudenta.add(ok);
			} else {
				for (OkresStudiow ok : okresyStudenta) {
					if (ok.getKierunek().getId() == kier.getId()) {
						Date dataOd = new Date();
						dataOd.setYear(kier.getRokOd() - 1900);
						dataOd.setDate(1);
						dataOd.setMonth(kier.getTypSemestru() == TypSemestru.LETNI ? 3 : 9);
						Date dataDo = new Date();
						dataDo.setDate(1);
						dataDo.setMonth(dataOd.getMonth() + (kier.getIloscSemestrow() % 2 == 0 ? 0 : 6));
						dataDo.setYear(dataOd.getYear()
								+ (kier.getIloscSemestrow() % 2 == 0 ? kier.getIloscSemestrow() / 2 : kier
										.getTypSemestru() == TypSemestru.LETNI ? (kier.getIloscSemestrow() + 1) / 2
										: kier.getIloscSemestrow() - 1) / 2);
						ok.setDataOd(dataOd);
						ok.setDatado(dataDo);
					}
				}
			}
		}
		selected.setOkresy(okresyStudenta);
	}

}
