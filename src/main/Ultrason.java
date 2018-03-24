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

public class Ultrason extends EV3UltrasonicSensor {

	public static SampleProvider view;
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

	public boolean detectionPalet(float distance, int delay) {
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
	}

}
