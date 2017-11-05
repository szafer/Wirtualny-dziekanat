package pl.edu.us.client.kartoteki.student.kartoteka;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class EdycjaPanel<T, E> extends HorizontalPanel {

	// protected Class<T> entityClass;
	// protected Class<E> editor;
	// private final TextButton nowy;
	// private final TextButton usun;
	// private final TextButton zatwierdz;

	// public EdycjaPanel(final E extends SBED, final ListStore<T> listStore) {
	// nowy = new TextButton("Nowy");
	// usun = new TextButton("Usuń");
	// zatwierdz = new TextButton("Zatwierdź");
	// add(nowy);
	// add(usun);
	// add(zatwierdz);
	// nowy.addSelectHandler(new SelectHandler() {
	//
	// @Override
	// public void onSelect(SelectEvent event) {
	// @SuppressWarnings("unchecked")
	// T s1 = (T) new Object();
	// listStore.add(s1);
	// }
	// });
	// usun.addSelectHandler(new SelectHandler() {
	//
	// @Override
	// public void onSelect(SelectEvent event) {
	// try {
	// T edited = driver.flush();
	// if (edited == null)
	// return;
	// listStore.remove(edited);
	// } catch (IllegalStateException e) {
	// return;
	// }
	// }
	// });
	// zatwierdz.addSelectHandler(new SelectHandler() {
	//
	// @Override
	// public void onSelect(SelectEvent event) {
	// try {
	// T edited = driver.flush();
	// if (edited == null)
	// return;
	// if (!driver.hasErrors()) {
	// listStore.update(edited);
	// }
	// } catch (IllegalStateException e) {
	// return;
	// }
	// }
	// });
	// }
	//
	// public TextButton getNowy() {
	// return nowy;
	// }
	//
	// public TextButton getUsun() {
	// return usun;
	// }
	//
	// public TextButton getZatwierdz() {
	// return zatwierdz;
	// }
}
