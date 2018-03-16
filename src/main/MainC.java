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
			// pliers.open();
			chassis.forward(300, 10000);
			/**
			 * Il faudrait qu'on fasse varier la vitesse
			 */
			LCD.drawString("Moteurs demarres", 1, 2);
			// pliers.close();
			// chassis.uTurn();
			// chassis.forward(300, 100);
			// chassis.stop();

			Ultrason ultrason = new Ultrason(SensorPort.S3);
			SampleProvider son = ultrason.getDistanceMode();

			float[] sample = new float[son.sampleSize()];
			float range = 1;
			pliers.open();
			Ultrason ul = new Ultrason();
			
			do {
				LCD.clear();
				LCD.drawString("distance:", 1, 2);
				LCD.drawString("" + range, 1, 3);
			} while (ultrason.detectionPalet());
			LCD.drawString("" + range, 1, 3);
			chassis.stop();
			pliers.close();
			Button.waitForAnyPress();

			// }

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}

	}

}
