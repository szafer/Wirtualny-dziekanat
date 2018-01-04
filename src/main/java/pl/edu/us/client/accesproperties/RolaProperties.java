package pl.edu.us.client.accesproperties;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.enums.Rola;

public interface RolaProperties extends PropertyAccess<Rola> {
    ModelKeyProvider<Rola> kod();

    LabelProvider<Rola> nazwa();
}