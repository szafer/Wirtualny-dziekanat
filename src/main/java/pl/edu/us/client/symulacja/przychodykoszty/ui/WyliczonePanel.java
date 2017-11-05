package pl.edu.us.client.symulacja.przychodykoszty.ui;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer.VBoxLayoutAlign;
import com.sencha.gxt.widget.core.client.form.BigDecimalField;
import com.sencha.gxt.widget.core.client.form.IntegerField;

import pl.edu.us.shared.model.ViPrzychod;

public class WyliczonePanel implements IsWidget, Editor<ViPrzychod> {

	IntegerField ilStud;// TODO ma być postawiona ilość studentów
	BigDecimalField przychod, koszt, dochod;
	HBoxLayoutContainer lab;
	public WyliczonePanel() {

		VBoxLayoutContainer v = new VBoxLayoutContainer(VBoxLayoutAlign.CENTER);
		Label ilStudLab = new Label("Ilość studentów");
		ilStudLab.setWidth("150");
		ilStud = new IntegerField();
		v.add(ilStudLab);
		v.add(ilStud);

		VBoxLayoutContainer v1 = new VBoxLayoutContainer(VBoxLayoutAlign.CENTER);
		Label przychodLab = new Label("Przychód");
		przychodLab.setWidth("150");
		przychod = new BigDecimalField();
		v1.add(przychodLab);
		v1.add(przychod);
		VBoxLayoutContainer v2 = new VBoxLayoutContainer(VBoxLayoutAlign.CENTER);
		Label kosztLab = new Label("Koszt");
		kosztLab.setWidth("150");
		koszt = new BigDecimalField();
		v2.add(kosztLab);
		v2.add(koszt);
		VBoxLayoutContainer v3 = new VBoxLayoutContainer(VBoxLayoutAlign.CENTER);
		Label dochodLab = new Label("Dochód");
		dochodLab.setWidth("150");
		dochod = new BigDecimalField();
		v3.add(dochodLab);
		v3.add(dochod);
		BoxLayoutData layoutData = new BoxLayoutData(new Margins(0, 5, 0, 0));
		layoutData.setMinSize(620);

		lab = new HBoxLayoutContainer();
		lab.setWidth(620);
		lab.setPadding(new Padding(5));
		lab.add(v, layoutData);
		lab.add(v1, layoutData);
		lab.add(v2, layoutData);
		lab.add(v3, layoutData);
	}

	@Override
	public Widget asWidget() {
		return lab;
	}

	@Ignore
	public IntegerField getIlStud() {
		return ilStud;
	}
}
