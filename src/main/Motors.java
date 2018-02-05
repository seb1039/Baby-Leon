package main;
import lejos.hardware.*;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.utility.Delay;

public class Motors{

	public static void motorTest(){
	//MOTEURS
	LCD.clear();
	LCD.drawString("Program 1", 0, 0);
	// Button.waitForAnyPress();
	LCD.clear();
	Motor.A.forward();
	Motor.C.forward();
	
	LCD.drawString("FORWARD", 0, 0);
	Delay.msDelay(10000);
	LCD.clear();
	LCD.drawString("BACKWARD", 0, 0);
	Motor.A.backward();
	Motor.C.backward();
	
	Delay.msDelay(10000);
	Motor.A.stop();
	Motor.C.stop();
	LCD.drawString("motorTest Finished", 3, 4);
	
	}
	
	
	
	
}
