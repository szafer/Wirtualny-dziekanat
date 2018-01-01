package pl.edu.us.client.accesproperties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface AccProperties<E> extends PropertyAccess<E> {
    @Path("id")
    ModelKeyProvider<E> key();

}
