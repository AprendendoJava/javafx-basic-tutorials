package org.fxapps.cdifx.service;

import javax.enterprise.inject.Default;

@Default
public class GreeterImpl implements Greeter {

	@Override
	public String greet(String name) {
		return "Hi " + name + "! Welcome to CDI + JavaFX!";
	}

}
