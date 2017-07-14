package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;

public class Person {

	private int personId;
	private String username;
	private String password;
	private String birthdate;

	public int getIdPerson() {
		return personId;
	}

	public String getUserName() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getBirthdate() {
		return birthdate;
	}
	// Setters
	public void setIdPerson(int idPerson) {
		this.personId = idPerson;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBirthdate(String birthdate){
		this.birthdate = birthdate;
	}

	public String toString() {
		return "Person ( " + personId + " " + username + " " + password + " "
				+ birthdate + " )";
	}

	
}