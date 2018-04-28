package main;

import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class Chassis {

	private Wheel wheel1;
	private Wheel wheel2;
	private WheeledChassis chassis;
	public int boussole;

	// Constructor
	public Chassis(NXTRegulatedMotor a, NXTRegulatedMotor b, int wheelDiameter, float offset) {
		this.wheel1 = WheeledChassis.modelWheel((RegulatedMotor) a, wheelDiameter).offset(-offset);
		this.wheel2 = WheeledChassis.modelWheel((RegulatedMotor) b, wheelDiameter).offset(offset);
		this.chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		boussole = 0;
	}

	public void setSpeed(double speed) {
		this.chassis.setLinearSpeed(speed);
	}

	public void forward(double speed, double distance) {
		this.chassis.setLinearSpeed(speed);
		this.chassis.travel(distance);

	}

	public void backward(double speed, double distance) {
		this.chassis.setLinearSpeed(speed);
		this.chassis.travel(-distance);
		this.chassis.waitComplete();
	}

	public void uTurn() {
		this.chassis.setAngularSpeed(800);
		this.chassis.rotate(165);
		this.chassis.waitComplete();
		boussole += 180;
	}

	public void uTurnResearch(boolean flag) {
		this.chassis.setAngularSpeed(40);
		if (flag)
			this.chassis.rotate(180);
		else
			this.chassis.rotate(-180);
		// this.chassis.waitComplete();
	}

	public void uTurnCorrect(boolean flag) {
		this.chassis.setAngularSpeed(20);
		if (flag) {
			this.chassis.rotate(7);
			this.chassis.waitComplete();
		} else {
			this.chassis.rotate(-7);
			this.chassis.waitComplete();
		}
	}

	public void completeTurn() {
		this.chassis.setAngularSpeed(800);
		this.chassis.rotate(360);
		this.chassis.waitComplete();
		boussole += 360;
	}

	public void completeTurnResearch() {
		this.chassis.setAngularSpeed(150);
		this.chassis.rotate(360);
		// this.chassis.waitComplete();
	}

	public void rotate(float angle) {
		this.chassis.rotate(angle);
	}

	public void stop() {
		this.chassis.stop();
	}

	public void halfRotation(boolean bool) {
		this.chassis.setAngularSpeed(800);
		if (bool) {
			this.chassis.rotate(80);
			this.chassis.waitComplete();
			boussole += 90;
		} else {
			this.chassis.rotate(-80);
			this.chassis.waitComplete();
			boussole += 270;
		}
	}

	public void deviation(boolean bool) {
		if (bool)
			this.chassis.rotate(50);
		else
			this.chassis.rotate(-50);
		this.chassis.waitComplete();
		this.forward(300, 300);
		this.chassis.waitComplete();
		if (bool)
			this.chassis.rotate(-50);
		else
			this.chassis.rotate(50);
		this.chassis.waitComplete();
	}

	public boolean isMoving() {
		return this.chassis.isMoving();
	}

}
