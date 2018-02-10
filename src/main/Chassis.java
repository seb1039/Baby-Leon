package main;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;

public class Chassis {
	
	private Wheel wheel1;
	private Wheel wheel2;
	private WheeledChassis chassis;
	
	
	
	//Constructor
	public Chassis(Port left, Port right, int wheelDiameter, float offset) {
		this.wheel1 = WheeledChassis.modelWheel((RegulatedMotor) left, wheelDiameter).offset(-offset);
		this.wheel2 = WheeledChassis.modelWheel((RegulatedMotor) right, wheelDiameter).offset(offset);
		this.chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	}
	
	public void forward(double speed, double distance){
		this.chassis.setLinearSpeed(speed);
		this.chassis.travel(distance);
	}
	

	
	public void uTurn(){
		this.chassis.rotate(180);		
	}
	
	public void stop(){
		this.chassis.stop();
	}

}
