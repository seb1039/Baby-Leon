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
			Colors color = new Colors();
			color.initColors();
			Button.waitForAnyPress();

		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}

	}

}
