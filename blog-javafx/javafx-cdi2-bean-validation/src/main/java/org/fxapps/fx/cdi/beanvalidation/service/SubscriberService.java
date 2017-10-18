package org.fxapps.fx.cdi.beanvalidation.service;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.fxapps.fx.cdi.beanvalidation.model.Subscriber;

@ApplicationScoped
public interface SubscriberService {

	public void save(Subscriber subscriber);

	public Set<Subscriber> list();

}
