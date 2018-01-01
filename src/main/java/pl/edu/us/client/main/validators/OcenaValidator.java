package pl.edu.us.client.main.validators;

import java.util.Collections;
import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.error.DefaultEditorError;

import pl.edu.us.shared.commons.AppStrings;

public class OcenaValidator implements Validator<Float> {

    @Override
    public List<EditorError> validate(Editor<Float> editor, Float value) {
        if (value != null) {
            if (value < 2.0f)
                return Collections.<EditorError> singletonList(new DefaultEditorError(editor, AppStrings.ocena_za_niska, value));
            if (value > 5.0f)
                return Collections.<EditorError> singletonList(new DefaultEditorError(editor, AppStrings.ocena_za_wysoka, value));
            if (!value.toString().matches("^\\d{0,2}(?:\\.\\d)?$"))
                return Collections.<EditorError> singletonList(new DefaultEditorError(editor, AppStrings.zly_format, value));
        }
        return null;
    }

}
