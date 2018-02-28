package main;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class Ultrason {
	
	public static EV3UltrasonicSensor eyes;
	public static SampleProvider view;
	
	public Ultrason(BaseRegulatedMotor motorPort){
		this.eyes = new EV3UltrasonicSensor((Port) motorPort);
		this.view= eyes.getDistanceMode();
	}
	
	
	
	/***
public static void ultrasonTest(){
		
		EV3UltrasonicSensor ultrason = new EV3UltrasonicSensor(SensorPort.S3);
		SampleProvider son= ultrason.getDistanceMode();

        float[] sample = new float[son.sampleSize()];
        float range=10;
        Motor.A.forward();
    	Motor.C.forward();
        while (range>0.1){
        	son.fetchSample(sample, 0);
	        range=sample[0];
	    }
        Motor.A.stop();
    	Motor.C.stop();	
	    LCD.drawString("touchTest Finished", 3, 4);
	}***/

}
