package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class Colors extends EV3ColorSensor {

	public SampleProvider view;
	public String colorDetected;
	public float[] red;
	public float[] blue;
	public float[] green;
	public float[] black;
	public float[] white;
	private File myFile;

	public Colors() {
		this(SensorPort.S2);
	}

	public Colors(Port port) {
		super(port);
		this.view = new MeanFilter(getRGBMode(), 1);
		/***
		try {
			this.myFile = new File("couleurs");
		} catch (NullPointerException e) {
			LCD.drawString("Pas de fichier", 1, 1);
			Delay.msDelay(10000);
		} finally {
			LCD.drawString("hiedkqs", 1, 1);
			Delay.msDelay(10000);
		}
		***/
		setFloodlight(Color.WHITE);

	}

	public String getColorDetected() {
		return colorDetected;
	}

	public void initColors() {
		LCD.drawString("Press enter to calibrate", 1, 1);
		LCD.drawString("red...", 1, 2);
		// System.out.println("Calibrate blue");
		Button.ENTER.waitForPressAndRelease();
		red = new float[view.sampleSize()];
		view.fetchSample(red, 0);
		LCD.drawString("RED calibrated", 1, 3);
		LCD.clear(2);
		LCD.drawString("blue...", 1, 2);
		// System.out.println("Calibrate blue");
		Button.ENTER.waitForPressAndRelease();
		blue = new float[view.sampleSize()];
		view.fetchSample(blue, 0);
		LCD.clear(3);
		LCD.drawString("BLUE calibrated", 1, 3);
		LCD.clear(2);
		LCD.drawString("green...", 1, 2);
		// System.out.println("Calibrate blue");
		Button.ENTER.waitForPressAndRelease();
		green = new float[view.sampleSize()];
		view.fetchSample(green, 0);
		LCD.clear(3);
		LCD.drawString("GREEN calibrated", 1, 3);
		LCD.clear(2);
		LCD.drawString("black...", 1, 2);
		// System.out.println("Calibrate blue");
		Button.ENTER.waitForPressAndRelease();
		black = new float[view.sampleSize()];
		view.fetchSample(black, 0);
		LCD.clear(3);
		LCD.drawString("BLACK calibrated", 1, 3);
		LCD.clear(2);
		LCD.drawString("white...", 1, 2);
		// System.out.println("Calibrate blue");
		Button.ENTER.waitForPressAndRelease();
		white = new float[view.sampleSize()];
		view.fetchSample(white, 0);
		LCD.clear(3);
		LCD.drawString("WHITE calibrated", 1, 3);

		// System.out.println("Blue calibrated");

		/***
		 * 
		 * LCD.drawString("Press enter to calibrate red...",1,1);
		 * Button.ENTER.waitForPressAndRelease(); red = new float[view.sampleSize()];
		 * view.fetchSample(red, 0); LCD.drawString("Red calibrated",1,1);
		 * 
		 * LCD.drawString("Press enter to calibrate green...",1,1);
		 * Button.ENTER.waitForPressAndRelease(); green = new float[view.sampleSize()];
		 * view.fetchSample(green, 0); LCD.drawString("Green calibrated",1,1);
		 * 
		 * LCD.drawString("Press enter to calibrate black...",1,1);
		 * Button.ENTER.waitForPressAndRelease(); black = new float[view.sampleSize()];
		 * view.fetchSample(black, 0); LCD.drawString("Black calibrated",1,1);
		 ***/

		// LCD.drawString("Press enter to calibrate white...",1,1);
		System.out.println("Calibrate white");
		// Button.ENTER.waitForPressAndRelease();
		white = new float[view.sampleSize()];
		view.fetchSample(white, 0);
		// LCD.drawString("White calibrated",1,1);
		/**
		 * System.out.println(
		 * 
		 * "White calibrated"); try { bw = new BufferedWriter(new FileWriter(myFile));
		 * bw.write(red[0] + ""); bw.write(red[1] + ""); bw.write(red[2] + "");
		 * bw.write(blue[0] + ""); bw.write(blue[1] + ""); bw.write(blue[2] + "");
		 * bw.write(green[0] + ""); bw.write(green[1] + ""); bw.write(green[2] + "");
		 * bw.write(black[0] + ""); bw.write(white[1] + ""); bw.write(white[2] + "");
		 * bw.write(white[3] + ""); bw.close(); } catch (FileNotFoundException e) {
		 * LCD.drawString("Pas de fichier", 1, 1); } catch (IOException f) {
		 * LCD.drawString("Pas d'ecriture", 1, 2); }
		 **/

	}

	public void getColor() {
		/**
		 * BufferedReader br; try { br = new BufferedReader(new FileReader(myFile));
		 * red[0] = Float.parseFloat(br.readLine()); red[1] =
		 * Float.parseFloat(br.readLine()); red[2] = Float.parseFloat(br.readLine());
		 * blue[0] = Float.parseFloat(br.readLine()); blue[1] =
		 * Float.parseFloat(br.readLine()); blue[2] = Float.parseFloat(br.readLine());
		 * green[0] = Float.parseFloat(br.readLine()); green[1] =
		 * Float.parseFloat(br.readLine()); green[2] = Float.parseFloat(br.readLine());
		 * black[0] = Float.parseFloat(br.readLine()); black[1] =
		 * Float.parseFloat(br.readLine()); black[2] = Float.parseFloat(br.readLine());
		 * white[0] = Float.parseFloat(br.readLine()); white[1] =
		 * Float.parseFloat(br.readLine()); white[2] = Float.parseFloat(br.readLine());
		 * br.close();
		 **/
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
		} /**
			 * 
			 * // LCD.drawString("The color is " + colorDetected + " \n",1,1); //
			 * Button.waitForAnyPress(); }catch(
			 * 
			 * FileNotFoundException e) { initColors(); }catch( IOException f) {
			 * System.err.println("IOException"); }
			 **/
	}

	public boolean depassementCouleur(String couleur) {
		getColor();
		return colorDetected.equals(couleur);
	}

}