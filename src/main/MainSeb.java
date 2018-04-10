package main;

import lejos.hardware.*;
import lejos.hardware.lcd.LCD;

public class MainSeb {

	public static void main(String[] args) {
		// Sound.beep();

		try {
			LCD.clear();
			ArtificialIntelligence ai = new ArtificialIntelligence();
			ai.actionToDo();
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
			LCD.drawString("Problem", 1, 2);
			Button.waitForAnyPress();
		}

	}

}
