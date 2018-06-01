package org.fxapps.chat;

import org.jgroups.JChannel;
import org.jgroups.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ChatApp extends Application {

	private static final double WIDTH = 800;
	private static final double HEIGHT = 600;

	private JChannel channel;
	private TextField txtNick = new TextField("Someone");
	private TextField txtMessage = new TextField();
	private TextArea txtLog = new TextArea();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox(20);
		txtMessage.setPrefWidth(600);
		txtMessage.setOnAction(e -> sendMessage());
		txtLog.setPrefHeight(500);
		txtLog.setMaxWidth(730);
		txtLog.setEditable(true);
		txtLog.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.EXTRA_LIGHT, FontPosture.ITALIC, 10));
		txtNick.setOnMouseClicked(e -> txtNick.setEditable(true));
		txtNick.setOnAction(e -> txtNick.setEditable(false));
		txtNick.editableProperty().addListener((a, b, c) -> {
			if (c)
				txtNick.setOpacity(1.0);
			else
				txtNick.setOpacity(0.5);

		});
		txtNick.setEditable(false);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(txtLog, new HBox(20, txtNick, txtMessage));
		stage.setScene(new Scene(root, WIDTH, HEIGHT));
		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);
		stage.show();
		stage.setOnCloseRequest(e -> channel.close());
		start();
	}

	private void start() throws Exception {
		channel = new JChannel();
		channel.connect("ChatCluster");
		channel.setReceiver(msg -> Platform.runLater(() -> txtLog.appendText(msg.getObject())));
	}

	private void sendMessage() {
		String msgStr = txtNick.getText() + " says:" + txtMessage.getText() + "\n";
		Message msg = new Message(null, msgStr);
		try {
			channel.send(msg);
			txtMessage.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}