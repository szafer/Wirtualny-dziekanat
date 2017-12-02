package pl.edu.us.client.accesproperties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;

public interface UserProperties<T> extends AccProperties<T> {

    @Path("id")
    ModelKeyProvider<T> key();

    ValueProvider<T, Integer> id();

    ValueProvider<T, String> imie();

    ValueProvider<T, String> nazwisko();

}
