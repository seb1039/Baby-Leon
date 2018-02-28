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
		    

			Button.waitForAnyPress();
		    
			//Motors.motorTest();			
			//Touch.touchTest();
		    //Ultrason.ultrasonTest();
		    //Colors.colorsTest();
		    //Pliers.pliersTest();
		    
		    /***
		    Pliers pliers = new Pliers(Motor.B);
		    pliers.open();
		    pliers.close();
		    ***/
		    
		    /***
		    ColorPreference pref = new ColorPreference("/home/lejos/preference/couleurs");
		    pref.startCalibration();
		    ***/
		    
		  
		    
			//while (!Button.ENTER.isDown()) {

				Pliers pliers = new Pliers(Motor.B);

				Chassis chassis = new Chassis(Motor.A, Motor.C, 56, 68);
				//pliers.open();
				chassis.forward(300, 10000);				
				LCD.drawString("Moteurs demarres", 1, 2);
				//pliers.close();
				//chassis.uTurn();	
				//chassis.forward(300, 100);
				//chassis.stop();
				
				EV3UltrasonicSensor ultrason = new EV3UltrasonicSensor(SensorPort.S3);
				SampleProvider son= ultrason.getDistanceMode();

		        float[] sample = new float[son.sampleSize()];
		        float range=10;
		        while (range>0.8){
		        	son.fetchSample(sample, 0);
			        range=sample[0];
			        LCD.drawString("distance: "+ range, 1, 2);
			    }
		        chassis.stop();
		        Button.waitForAnyPress();

			//}
		    
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Button.waitForAnyPress();
		}
	
}
	
}




