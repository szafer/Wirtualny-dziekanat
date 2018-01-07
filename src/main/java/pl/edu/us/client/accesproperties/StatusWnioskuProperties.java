package pl.edu.us.client.accesproperties;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import pl.edu.us.shared.enums.StatusWniosku;

public interface StatusWnioskuProperties extends PropertyAccess<StatusWniosku> {

    ModelKeyProvider<StatusWniosku> kod();

    LabelProvider<StatusWniosku> nazwa();
}
