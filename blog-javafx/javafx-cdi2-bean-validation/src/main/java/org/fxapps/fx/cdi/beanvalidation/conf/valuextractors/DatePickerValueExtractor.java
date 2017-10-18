package org.fxapps.fx.cdi.beanvalidation.conf.valuextractors;

import java.time.LocalDate;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

import javafx.scene.control.DatePicker;

@UnwrapByDefault
public class DatePickerValueExtractor implements ValueExtractor<@ExtractedValue(type = LocalDate.class)  DatePicker> {

	@Override
	public void extractValues(DatePicker originalValue,
			javax.validation.valueextraction.ValueExtractor.ValueReceiver receiver) {
		receiver.value(null, originalValue.getValue());
	}

}