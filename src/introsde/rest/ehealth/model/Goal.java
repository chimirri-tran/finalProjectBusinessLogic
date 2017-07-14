package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the "Goal" database table.
 * 
 */
public class Goal {

	private int idGoal;
	private double actualWeight;
	private double finalWeight;
	private int heartRate;
	private double height;
	private double initialWeight;
	private double lostWeight;
	private int maxBloodPressure;
	private int minBloodPressure;
	private int sleepHours;
	private int idPerson;
	private int steps;

	public Goal() {
	}

	public Goal(double actualWeight, double height, double finalWeight, double lostWeight, int maxBloodPressure,
			int minBloodPressure, int idPerson, int steps) {
		this.actualWeight = actualWeight;
		this.height = height;
		this.finalWeight = finalWeight;
		this.height = height;
		this.maxBloodPressure = maxBloodPressure;
		this.minBloodPressure = minBloodPressure;
		this.lostWeight = lostWeight;
		this.idPerson = idPerson;
		this.steps = steps;
	}

	public double getActualWeight() {
		return this.actualWeight;
	}

	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}

	public double getFinalWeight() {
		return this.finalWeight;
	}

	public void setFinalWeight(double finalWeight) {
		this.finalWeight = finalWeight;
	}

	public int getHeartRate() {
		return this.heartRate;
	}

	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	public double getHeight() {
		return this.height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getIdGoal() {
		return this.idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public int getSteps() {
		return this.steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	// we make this transient for JAXB to avoid and infinite loop on
	// serialization
	@XmlTransient
	public int getIdPerson() {
		return idPerson;
	}

	public void setPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public double getInitialWeight() {
		return this.initialWeight;
	}

	public void setInitialWeight(double initialWeight) {
		this.initialWeight = initialWeight;
	}

	public double getLostWeight() {
		return this.lostWeight;
	}

	public void setLostWeight(double lostWeight) {
		this.lostWeight = lostWeight;
	}

	public int getMaxBloodPressure() {
		return this.maxBloodPressure;
	}

	public void setMaxBloodPressure(int maxBloodPressure) {
		this.maxBloodPressure = maxBloodPressure;
	}

	public int getMinBloodPressure() {
		return this.minBloodPressure;
	}

	public void setMinBloodPressure(int minBloodPressure) {
		this.minBloodPressure = minBloodPressure;
	}

	public int getSleepHours() {
		return this.sleepHours;
	}

	public void setSleepHours(int sleepHours) {
		this.sleepHours = sleepHours;
	}

	public String toString() {
		return "Goal ( " + idGoal + ", " + idPerson + ", " + actualWeight + ", " + height + ", " + finalWeight + ", "
				+ lostWeight + ", " + lostWeight + ", " + maxBloodPressure + ", " + minBloodPressure + ", " + idPerson
				+ ", " + steps + ", " + ")";

	}

	
	

}