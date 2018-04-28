package main;

import lejos.hardware.*;
import lejos.robotics.*;
import lejos.utility.Delay;
import main.Touch;
import main.ArtificialIntelligence.Statut;
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

			Pliers pliers = new Pliers(Motor.B);
			Chassis chassis = new Chassis(Motor.A, Motor.C, 56, 68);

			Touch touch = new Touch(SensorPort.S1);
		//	Colors colors = new Colors(SensorPort.S2);
			Ultrason ultrason = new Ultrason(SensorPort.S3);

			float distMin = Float.MAX_VALUE;
			boolean trouve = false;
			LCD.clear();
			LCD.drawString("Statut : SHERLOCK", 1, 7);
			chassis.uTurnResearch(true);
			while (chassis.isMoving()) {
				LCD.drawString("isMoving : Yes", 1, 6);
				if (ultrason.giveDistance() < distMin) {
					distMin = ultrason.giveDistance();
				}
			}
			chassis.stop();
			chassis.uTurnResearch(false);
			while (chassis.isMoving()) {
				LCD.drawString("" + Math.abs(ultrason.giveDistance() - distMin), 1, 4);
				if (Math.abs(ultrason.giveDistance() - distMin) <= 0.4f
						&& Math.abs(ultrason.giveDistance() - distMin) >= 0.005f) {
					chassis.stop();
					LCD.clear(6);
					LCD.drawString("isMoving : Non", 1, 6);
					trouve = true;
					break;
				}
			}
			chassis.uTurnCorrect(false);
			if (trouve)
				chassis.forward(800, 1000);
			
			
			else
				LCD.drawString("Failure", 1, 3);

			
			LCD.drawString("touchTest Finished", 3, 4);
			ultrason.close();
			Button.waitForAnyEvent();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}

	}

}
