package org.fxapps.cdifx.service;
@Message
public class GreeterPT implements Greeter {

	@Override
	public String greet(String name) {
		return "Olá " + name;
	}

}
