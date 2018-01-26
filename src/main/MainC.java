package main;
import lejos.hardware.*;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.*;

public class MainC {

	public static void main(String[] args) {
		// Sound.beep();
		try {
			LCD.clear();
			LCD.drawString("Program 1", 0, 0);
			Button.waitForAnyPress();
			LCD.clear();
			Motor.A.forward();
			Motor.C.forward();
			LCD.drawString("FORWARD", 0, 0);
			Button.waitForAnyPress();
			LCD.clear();
			LCD.drawString("BACKWARD", 0, 0);
			Motor.A.backward();
			Motor.C.backward();
			Button.waitForAnyPress();
			Motor.A.stop();
			Motor.C.stop();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.ENTER.waitForPress();
		}
	}

}
