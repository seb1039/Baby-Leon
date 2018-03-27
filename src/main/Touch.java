package main;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

public class Touch extends EV3TouchSensor {

	private boolean pressed;
	private SampleProvider toucher;
	private float[] sample;
	private float range;

	public Touch(Port port) {
		super(port);
		pressed = false;
		toucher = this.getTouchMode();
		sample = new float[toucher.sampleSize()];
		range = 0;
	}

	private boolean majPressed() {
		
		//toucher.fetchSample(sample, 0);
		//range = sample[0];
		/***
		LCD.clear();
		LCD.drawString("" +range , 4, 3);
		pressed = (range >0);
		***/
		while (range != 1) {
			toucher.fetchSample(sample, 0);
			// System.out.printf("%.5f m\n", sample[0]);
			range = sample[0];
		}
		pressed= (range==1);
		return pressed;
	}

	public boolean isPressed() {
		majPressed();
		return pressed;
	}

	public static void touchTest() {

		EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S1);
		SampleProvider toucher = touch.getTouchMode();

		float[] sample = new float[toucher.sampleSize()];
		float range = 0;
		Motor.A.forward();
		Motor.C.forward();
		while (range != 1) {
			toucher.fetchSample(sample, 0);
			// System.out.printf("%.5f m\n", sample[0]);
			range = sample[0];
		}
		Motor.A.stop();
		Motor.C.stop();
		LCD.drawString("touchTest Finished", 3, 4);
	}

}
