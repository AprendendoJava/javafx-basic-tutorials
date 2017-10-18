package org.fxapps.fx.cdi.beanvalidation.view;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.fxapps.fx.cdi.beanvalidation.model.Subscriber;
import org.fxapps.fx.cdi.beanvalidation.service.SubscriberService;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class FXController {

	@FXML
	@NotEmpty(message = "Please provide an email")
	@Email(message = "Email is not valid")
	public TextField txtEmail;
	
	@FXML
	@NotEmpty(message = "Please provide a name")
	public TextField txtName;
	
	@FXML
	@Past(message = "Your birth date should be in the past!")
	@NotNull(message = "Please provide a value for your birth date")
	public DatePicker dtBirthDate;

	@Inject
	SubscriberService subscriberService;
	
	@Inject
	Validator validator;

	public void subscribe() {
		Set<ConstraintViolation<FXController>> validation = validator.validate(this);
		if(!validation.isEmpty()) {
			showValidationErrors(validation);
		} else {
			validateAndSaveSubscriber();
		}
		
	}

	private void validateAndSaveSubscriber() {
		// we could validate the object as well....
		Subscriber subscriber = getSubscriberFromFields();
		String message;
		 AlertType alertType;
		Set<Subscriber> subscribers = subscriberService.list();
		Predicate<Subscriber> subscriberFilter = s -> s.getEmail().get().equals(subscriber.getEmail().get()); 
		Optional<Subscriber> existingSubscriber = subscribers.stream().filter(subscriberFilter).findAny();
		if(existingSubscriber.isPresent()) {
			alertType = AlertType.ERROR;
			message = "It seems that you already subscribed to this. Try again with a diffent email.";
		} else {
			subscriberService.save(subscriber);
			alertType = AlertType.CONFIRMATION;
			message = "Congratulations! You are the " + (subscribers.size() + 1) + "° subscriber!";
		}
		showMessage(message, alertType);
	}

	private void showMessage(String message, AlertType alertType) {
		  Alert dialogoErro = new Alert(alertType);
          dialogoErro.setTitle("Response from the system");
          dialogoErro.setContentText(message);
          dialogoErro.showAndWait();
		
	}

	private void showValidationErrors(Set<ConstraintViolation<FXController>> validation) {
		// we are using reflection to get the field related to the validation error and show a messge
		// make sure the validated fields are public or the reflection won't work
		// or you can create public get methods etc...
		validation.forEach(v -> {
			String property = v.getPropertyPath().toString();
			Object object = v.getLeafBean();
			try {
				Field field = object.getClass().getField(property);
				Control control = (Control) field.get(object);
				configureToolTipForControl(control,v.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void configureToolTipForControl(Control control, String message) {
		control.setTooltip(new Tooltip(message));
		Bounds boundsInScene = control.localToScreen(control.getBoundsInLocal());
		control.getTooltip().show(control, boundsInScene.getMinX(), boundsInScene.getMaxY());
		control.getTooltip().setAutoHide(true);
	}

	private Subscriber getSubscriberFromFields() {
		Subscriber subscriber = new Subscriber();
		subscriber.setName(txtName.getText());
		subscriber.setEmail(txtEmail.getText());
		subscriber.setBirthDate(dtBirthDate.getValue());
		return subscriber;
	}

}
