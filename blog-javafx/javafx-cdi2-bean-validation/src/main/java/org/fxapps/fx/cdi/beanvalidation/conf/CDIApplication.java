package org.fxapps.fx.cdi.beanvalidation.conf;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.util.AnnotationLiteral;
import javafx.application.Application;
import javafx.stage.Stage;

public class CDIApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	@SuppressWarnings("serial")
	public void start(Stage primaryStage) throws Exception {
		SeContainerInitializer initializer = SeContainerInitializer.newInstance();
		final SeContainer container = initializer.initialize();
		container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {});
	}

}