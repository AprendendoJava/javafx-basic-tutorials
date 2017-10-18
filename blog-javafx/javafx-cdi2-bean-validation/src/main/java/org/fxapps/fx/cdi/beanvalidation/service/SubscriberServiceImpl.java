package org.fxapps.fx.cdi.beanvalidation.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;

import org.fxapps.fx.cdi.beanvalidation.model.Subscriber;

@Default
public class SubscriberServiceImpl implements SubscriberService {
	
	private Set<Subscriber> subscribers;

	@PostConstruct
	private void setup() throws IOException {
		// this was done just to show that CDI lifecycle methods works here ;)
		System.out.println("Creating subscribers list...");
		subscribers = new HashSet<>();
	}

	@Override
	public void save(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	@Override
	public Set<Subscriber> list() {
		return this.subscribers;
	}

}
