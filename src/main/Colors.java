package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class Colors extends EV3ColorSensor {

	public EV3ColorSensor eyes;
	public SampleProvider view;
	public String colorDetected;
	public float[] red;
	public float[] blue;
	public float[] green;
	public float[] black;
	public float[] white;

	public Colors() {
		this(SensorPort.S2);
	}

	public Colors(Port port) {
		super(port);
		this.view = new MeanFilter(eyes.getRGBMode(), 1);
		eyes.setFloodlight(Color.WHITE);
	}

	public String getColorDetected() {
		return colorDetected;
	}

	public void initColors() {
		LCD.drawString("Press enter to calibrate blue...",1,1);
		Button.ENTER.waitForPressAndRelease();
		blue = new float[view.sampleSize()];
		view.fetchSample(blue, 0);

		LCD.drawString("Press enter to calibrate red...",1,1);
		Button.ENTER.waitForPressAndRelease();
		red = new float[view.sampleSize()];
		view.fetchSample(red, 0);

		LCD.drawString("Press enter to calibrate green...",1,1);
		Button.ENTER.waitForPressAndRelease();
		green = new float[view.sampleSize()];
		view.fetchSample(green, 0);

		LCD.drawString("Press enter to calibrate black...",1,1);
		Button.ENTER.waitForPressAndRelease();
		black = new float[view.sampleSize()];
		view.fetchSample(black, 0);
		LCD.drawString("Black calibrated",1,1);

		LCD.drawString("Press enter to calibrate white...",1,1);
		Button.ENTER.waitForPressAndRelease();
		white = new float[view.sampleSize()];
		view.fetchSample(white, 0);
		LCD.drawString("White calibrated",1,1);
	}

	public void getColor() {

		float[] sample = new float[view.sampleSize()];
		// LCD.drawString("\nPress enter to detect a color...");
		// Button.ENTER.waitForPressAndRelease();
		view.fetchSample(sample, 0);
		double minscal = Double.MAX_VALUE;

		double scalaire = TestColor.scalaire(sample, blue);

		if (scalaire < minscal) {
			minscal = scalaire;
			colorDetected = "blue";
		}

		scalaire = TestColor.scalaire(sample, red);
		if (scalaire < minscal) {
			minscal = scalaire;
			colorDetected = "red";
		}

		scalaire = TestColor.scalaire(sample, green);
		if (scalaire < minscal) {
			minscal = scalaire;
			colorDetected = "green";
		}

		scalaire = TestColor.scalaire(sample, black);
		if (scalaire < minscal) {
			minscal = scalaire;
			colorDetected = "black";
		}

		scalaire = TestColor.scalaire(sample, white);
		if (scalaire < minscal) {
			minscal = scalaire;
			colorDetected = "white";
		}

		LCD.drawString("The color is " + colorDetected + " \n");
		// Button.waitForAnyPress();

	}

}
