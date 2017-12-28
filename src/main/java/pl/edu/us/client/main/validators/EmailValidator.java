package pl.edu.us.client.main.validators;

import java.util.Collections;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;

import pl.edu.us.shared.commons.AppStrings;

public class EmailValidator implements Validator<String> {

    @Override
    public List<EditorError> validate(Editor<String> editor, String value) {
        Boolean b = value.matches(
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if (!b)
            return Collections.<EditorError> singletonList(new DefaultEditorError(editor, AppStrings.bledny_email, value));
        return null;
    }

}
