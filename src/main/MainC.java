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

	public static void main(String[] args)  {
		// Sound.beep();
		try {
		    LCD.clear();
		    
			//Motors.motorTest();			
			//Touch.touchTest();
		    //Ultrason.ultrasonTest();
		 //   Colors.colorsTest();
		    
		    ColorPreference pref = new ColorPreference("/usr/lejos/preference/couleurs");
		    pref.startCalibration();
		    
		    /***
		    Chassis chassis = new Chassis(56, 68);
		    chassis.forward(300, 100000);
		    LCD.drawString("Moteurs demarrés", 1, 2);
		    Delay.msDelay(10000);
		    chassis.uTurn();
		    Delay.msDelay(10000);
		    chassis.forward(500, 100000);
		    Delay.msDelay(10000);
		    chassis.stop();
		    ***/
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}
	}

}
