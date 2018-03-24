package main;

import lejos.hardware.*;
import lejos.robotics.*;
import lejos.utility.Delay;
import preferences.ColorPreference;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.remote.ev3.RemoteAnalogPort;
import lejos.remote.ev3.RemotePort;

public class MainSeb {

	public static void main(String[] args) {
		// Sound.beep();

		try {
			LCD.clear();

			/**
			 * Instantiation des ports
			 *
			 */
			Pliers pliers = new Pliers(Motor.B);
			Ultrason ultrason = new Ultrason(SensorPort.S3);
			Touch touch = new Touch(SensorPort.S1);
			
			
			Button.waitForAnyPress();

			// Motors.motorTest();
			// Touch.touchTest();
			// Ultrason.ultrasonTest();
			// Colors.colorsTest();
			// Pliers.pliersTest();
			/***
			 * ColorPreference pref = new
			 * ColorPreference("/home/lejos/preference/couleurs"); pref.startCalibration();
			 ***/


			Chassis chassis = new Chassis(Motor.A, Motor.C, 56, 68);
			chassis.forward(150, 10000);
			/**
			 * Il faudrait qu'on fasse varier la vitesse
			 */
			LCD.drawString("Moteurs demarres", 1, 2);
			SampleProvider son = ultrason.getDistanceMode();
			// Ultrason.ultrasonTest();
			float range = 1;

			while (!ultrason.detectionPalet(.4f,200))
				;
			LCD.drawString("Détecté" + range, 1, 3);
			pliers.open();
			int wait = 500;
			chassis.forward(50, wait);
			if (touch.isPressed()){
			pliers.close();}
			Button.waitForAnyPress();
			ultrason.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}

	}

}
