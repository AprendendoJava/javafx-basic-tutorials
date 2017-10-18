package org.fxapps.fx.cdi.beanvalidation.conf.valuextractors;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import javafx.scene.control.TextField;

@UnwrapByDefault
public class TextFieldValueExtractor implements ValueExtractor<@ExtractedValue(type = String.class)  TextField> {

	@Override
	public void extractValues(TextField originalValue,
			javax.validation.valueextraction.ValueExtractor.ValueReceiver receiver) {
		receiver.value(null, originalValue.getText());
	}

}