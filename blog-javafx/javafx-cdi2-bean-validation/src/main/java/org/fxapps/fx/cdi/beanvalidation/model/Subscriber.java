package org.fxapps.fx.cdi.beanvalidation.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subscriber {

	// Look! Bean Validation 2 works with JavaFX properties!
	@NotEmpty(message = "Please provide your name")
	private final StringProperty name = new SimpleStringProperty();

	@Email(message = "Email is not valid")
	@NotEmpty(message = "Please provide your email")
	private final StringProperty email = new SimpleStringProperty();

	@Past(message = "Birth date should be in the past")
	@NotNull(message = "Please let us know your birth date")
	private LocalDate birthDate;

	public StringProperty getName() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Subscriber [name=" + name + ", email=" + email + ", birthDate=" + birthDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscriber other = (Subscriber) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
