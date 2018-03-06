package org.fxapps.cdifx.view;

import javax.inject.Inject;

import org.fxapps.cdifx.service.Greeter;
import org.fxapps.cdifx.service.Message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXController {

	@FXML
	Label lblMessage;

	@FXML
	TextField txtName;

	@Inject @Message
	Greeter greeter;

	public void updateMessage() {
		String greeting = greeter.greet(txtName.getText());
		lblMessage.setText(greeting);
	}

}
