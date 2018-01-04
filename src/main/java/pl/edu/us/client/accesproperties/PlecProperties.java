package pl.edu.us.client.accesproperties;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.enums.Plec;

public interface PlecProperties extends PropertyAccess<Plec> {
    ModelKeyProvider<Plec> kod();

    LabelProvider<Plec> nazwa();
}