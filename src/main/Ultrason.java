package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Ultrason extends EV3UltrasonicSensor {

	public SampleProvider view;
	private float[] sample;
	private float range;

	public Ultrason() {
		this("S3");
	}

	public Ultrason(Port port) {
		super(port);
		this.view = this.getDistanceMode();
		sample = new float[view.sampleSize()];
		range = 1f;
	}

	public Ultrason(String portName) {
		super(LocalEV3.get().getPort(portName));
		this.view = this.getDistanceMode();
		sample = new float[view.sampleSize()];
		range = 1f;
	}
	
	public float getDistance() {
		view.fetchSample(sample, 0);
		range = sample[0];
		return range;
	}

	
	public boolean detectionPalet(float distance, int delay) {
		/**
		 * 1. On capte qqchs 2. Si on ne recapte plus, on ouvre les pinces 3. Sinon, on
		 * referme les pinces
		 */

		LCD.clear(2);
		LCD.clear(3);
		LCD.drawString("distance:", 1, 2);
		LCD.drawString("" + range, 1, 3);

		/**
		 * Etape 1
		 */

		view.fetchSample(sample, 0);
		range = sample[0];
		if (range > distance || range < 0.0f)
			return false;
		Delay.msDelay(delay);
		/**
		 * Etape 2
		 */
		view.fetchSample(sample, 0);
		range = sample[0];
		if (range < distance && range > 0.0f) {
			LCD.drawString("Detecte" + (double) range, 1, 4);
			return true;
		}
		return false;
	}
	public boolean detectionPalet(int delay) {
		/**
		 * 1. On capte qqchs 2. Si on ne recapte plus, on ouvre les pinces 3. Sinon, on
		 * referme les pinces
		 */

		LCD.clear();
		LCD.drawString("distance:", 1, 2);
		LCD.drawString("" + range, 1, 3);

		/**
		 * Etape 1
		 */

		view.fetchSample(sample, 0);
		range = sample[0];
		if (range == 1.0f || range < 0.0f)
			return false;
		Delay.msDelay(delay);
		/**
		 * Etape 2
		 */
		view.fetchSample(sample, 0);
		range = sample[0];
		if (range < 1.0f && range > 0.0f) {
			LCD.drawString("Detecte" + (double) range, 1, 4);
			return true;
		}
		return false;
	}
	
	public boolean researchPalet(int delay) {
		/**
		 * 1. On fait un tour d'horizon on garde le plus proche 2. On refait un tour d'horizon jusqu'à retrouver la distance minimum détectée avant 
		 */

		LCD.clear();
		LCD.drawString("distance:", 1, 2);
		LCD.drawString("" + range, 1, 3);

		/**
		 * Etape 1
		 */
		double distance = Double.MAX_VALUE;  
		view.fetchSample(sample, 0);
		range = sample[0];
		if (range > distance || range < 0.0f)
			return false;
		Delay.msDelay(delay);
		/**
		 * Etape 2
		 */
		view.fetchSample(sample, 0);
		range = sample[0];
		if (range < distance && range > 0.0f) {
			LCD.drawString("Detecte" + (double) range, 1, 4);
			return true;
		}
		return false;
	}
	
	public float giveDistance(){
		view.fetchSample(sample, 0);
		range = sample[0];
		return range;
	}

	public static void ultrasonTest() {
		EV3UltrasonicSensor ultrason = new EV3UltrasonicSensor(SensorPort.S3);
		SampleProvider son = ultrason.getDistanceMode();
		float[] sample = new float[son.sampleSize()];
		float range = 10;
		Motor.A.forward();
		Motor.C.forward();
		while (range > 0.15) {
			son.fetchSample(sample, 0);
			range = sample[0];
			LCD.drawString("" + range, 1, 3);
		}
		Motor.A.stop();
		Motor.C.stop();
		LCD.drawString("touchTest Finished", 3, 4);
		ultrason.close();
	}

}
