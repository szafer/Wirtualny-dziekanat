package pl.edu.us.client.accesproperties;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.enums.TypWniosku;

public interface TypWnioskuProperties extends PropertyAccess<TypWniosku> {

    ModelKeyProvider<TypWniosku> kod();

    LabelProvider<TypWniosku> nazwa();
}
