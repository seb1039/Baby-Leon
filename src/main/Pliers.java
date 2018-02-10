package main;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class Pliers {
	
	public static int tachocount;
	public static BaseRegulatedMotor pliers;
	
	public Pliers(BaseRegulatedMotor motorPort){
		this.pliers=motorPort;
		this.tachocount=pliers.getTachoCount();		
	}
	
	public static void open(){
		pliers.forward();
		while(tachocount<900){
			tachocount=pliers.getTachoCount();
		}
		pliers.stop();
	}
	
	public static void close(){
		pliers.backward();
		while(tachocount>0){
			tachocount=pliers.getTachoCount();
		}
		pliers.stop();
	}
	
	public static void pliersTest(){
		
		//PINCES
		LCD.clear();
		LCD.drawString("Program for pliers", 0, 0);
		Button.waitForAnyPress();
		LCD.clear();
		Motor.B.forward();
		LCD.drawString("OPEN", 0, 0);
		Button.waitForAnyPress();
		LCD.clear();
		LCD.drawString("CLOSE", 0, 0);
		Motor.B.backward();
		Button.waitForAnyPress();
		Motor.B.stop();
		LCD.drawString("pliersTest Finished", 3, 4);
		
		}

}
