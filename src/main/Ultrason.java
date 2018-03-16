package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.UARTPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Ultrason extends EV3UltrasonicSensor{
	
	public static EV3UltrasonicSensor eyes;
	public static SampleProvider view;
	private float[] sample;
	private float range;
	
	public Ultrason(){
		this("S2");
	}
	
	public Ultrason(Port port) {
		super(port);
		this.view = this.getDistanceMode();
		sample = new float[view.sampleSize()];
		range = 1;
	}

	public Ultrason(String portName){
		super(LocalEV3.get().getPort(portName));
		this.view = this.getDistanceMode();
		sample = new float[view.sampleSize()];
		range = 1;
	}
	
	public boolean detectionPalet() {
		/**
		 * 1. On capte qqchs
		 * 2. Si on ne recapte plus, on ouvre les pinces
		 * 3. Sinon, on referme les pinces
		 */
		
		/**
		 * Etape 1
		 */
		while (range > 0.4) {
			view.fetchSample(sample, 0);
			range = sample[0];
			
		}
		Delay.msDelay(200);
		/**
		 * Etape 2
		 */
		view.fetchSample(sample, 0);
		range = sample[0];
		return range > 0.4;
	}
	
	/***
	 * public static void ultrasonTest(){
	 * EV3UltrasonicSensor ultrason = new EV3UltrasonicSensor(SensorPort.S3);
	 * SampleProvider son= ultrason.getDistanceMode();
	 * float[] sample = new float[son.sampleSize()];
	 * float range=10;
	 * Motor.A.forward();
	 * Motor.C.forward();
	 * while (range>0.1){
	 * 		son.fetchSample(sample, 0);
	 * 		range=sample[0];
	 * }
        Motor.A.stop();
    	Motor.C.stop();	
	    LCD.drawString("touchTest Finished", 3, 4);
	}***/

}
