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

public class MainC {

	public static void main(String[] args) {
		// Sound.beep();

		try {
			LCD.clear();

			Button.waitForAnyPress();

			// Motors.motorTest();
			// Touch.touchTest();
			// Ultrason.ultrasonTest();
			// Colors.colorsTest();
			// Pliers.pliersTest();

			Pliers pliers = new Pliers(Motor.B);

			/***
			 * ColorPreference pref = new
			 * ColorPreference("/home/lejos/preference/couleurs"); pref.startCalibration();
			 ***/

			// while (!Button.ENTER.isDown()) {

			Chassis chassis = new Chassis(Motor.A, Motor.C, 56, 68);
			chassis.forward(300, 10000);
			/**
			 * Il faudrait qu'on fasse varier la vitesse
			 */
			LCD.drawString("Moteurs demarres", 1, 2);
			Ultrason ultrason = new Ultrason(SensorPort.S3);
			SampleProvider son = ultrason.getDistanceMode();
		//	Ultrason.ultrasonTest();
			float range = 1;
			
			while (!ultrason.detectionPalet(.4f));
			LCD.drawString("Détecté" + range, 1, 3);
			pliers.open();
			chassis.forward(200,1000);
			Delay.msDelay(1000);
			pliers.close();
			Button.waitForAnyPress();
		

			// }

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}

	}

}
