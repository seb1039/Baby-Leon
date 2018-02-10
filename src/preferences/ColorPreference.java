package preferences;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Calibrate;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class ColorPreference implements Calibrate {

	public static void startCalibrate(String fileName) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(fileName));

			Port port = LocalEV3.get().getPort("S2");
			EV3ColorSensor colorSensor = new EV3ColorSensor(port);
			SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
			colorSensor.setFloodlight(Color.WHITE);
			file.write(average.toString() + "");

			System.out.println("Appuyez entrer pour calibrer le rouge...");
			Button.ENTER.waitForPressAndRelease();
			float[] red = new float[average.sampleSize()];
			average.fetchSample(red, 0);
			file.write(red + "");

			System.out.println("Appuyez entrer pour calibrer le vert...");
			Button.ENTER.waitForPressAndRelease();
			float[] green = new float[average.sampleSize()];
			average.fetchSample(green, 0);
			file.write(green + "");

			System.out.println("Appuyez entrer pour calibrer le bleu...");
			Button.ENTER.waitForPressAndRelease();
			float[] blue = new float[average.sampleSize()];
			average.fetchSample(blue, 0);
			file.write(blue + "");

			System.out.println("Appuyez entrer pour calibrer le jaune...");
			Button.ENTER.waitForPressAndRelease();
			float[] yellow = new float[average.sampleSize()];
			average.fetchSample(yellow, 0);
			file.write(yellow + "");

			System.out.println("Appuyez entrer pour calibrer le blanc...");
			Button.ENTER.waitForPressAndRelease();
			float[] white = new float[average.sampleSize()];
			average.fetchSample(white, 0);
			file.write(white + "");

			System.out.println("Appuyez entrer pour calibrer le noir...");
			Button.ENTER.waitForPressAndRelease();
			float[] black = new float[average.sampleSize()];
			average.fetchSample(black, 0);
			System.out.println("Black calibrated");
			file.write(black + "");

			/***
			 * File 1 : Average 2 : Red 3 : Green 4 : Blue 5 : Yellow 6 : White 7 : Black
			 */

		} catch (Throwable t) {
			if (t instanceof FileNotFoundException)
				System.out.println("Problème fichier");
			t.printStackTrace();
			Delay.msDelay(10000);
			System.exit(0);
		}
	}
	public void test(String fileName) {
		Port port = LocalEV3.get().getPort("S2");
		EV3ColorSensor colorSensor = new EV3ColorSensor(port);
		SampleProvider average = new MeanFilter(colorSensor.getRGBMode(), 1);
		colorSensor.setFloodlight(Color.WHITE);
		try {
			BufferedReader file = new BufferedReader(new FileReader(fileName));

			int nbColors = 7;
			float[][] vectors = new float[nbColors][3];

			for (float[] e : vectors) {
				String[] tmp = file.readLine().split(" ");
				for (int i = 0; i < 3; i++) {
					e[i] = Float.parseFloat(tmp[i]);
				}
			}

			float[] sample = new float[average.sampleSize()];
			System.out.println("\nPress enter to detect a color...");
			Button.ENTER.waitForPressAndRelease();
			average.fetchSample(sample, 0);
			double minscal = Double.MAX_VALUE;
			String color = "";

			double scalaire = ColorPreference.scalaire(sample, vectors[0]);
			// Button.ENTER.waitForPressAndRelease();
			// System.out.println(scalaire);

			if (scalaire < minscal) {
				minscal = scalaire;
				color = "rouge";
			}

			scalaire = ColorPreference.scalaire(sample, vectors[1]);
			// System.out.println(scalaire);
			// Button.ENTER.waitForPressAndRelease();
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "bleu";
			}

			scalaire = ColorPreference.scalaire(sample, vectors[2]);
			// System.out.println(scalaire);
			// Button.ENTER.waitForPressAndRelease();
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "vert";
			}

			scalaire = ColorPreference.scalaire(sample, vectors[3]);
			// System.out.println(scalaire);
			// Button.ENTER.waitForPressAndRelease();
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "jaune";
			}

			scalaire = ColorPreference.scalaire(sample, vectors[4]);
			// System.out.println(scalaire);
			// Button.ENTER.waitForPressAndRelease();
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "blanc";
			}

			scalaire = ColorPreference.scalaire(sample, vectors[5]);
			// System.out.println(scalaire);
			// Button.ENTER.waitForPressAndRelease();
			if (scalaire < minscal) {
				minscal = scalaire;
				color = "jaune";
			}

			System.out.println("La couleur est " + color + " \n");
			System.out.println("Pressez ENTRER pour terminer \n");
			System.out.println("ESCAPE to exit");
			Button.waitForAnyPress();
			if (Button.ESCAPE.isDown()) {
				colorSensor.setFloodlight(false);
			}
			
			colorSensor.close();
			file.close();
		} catch (FileNotFoundException f) {
			System.out.println("Problème");
		} catch (IOException e) {
			System.out.println("Problème fichier caca");
		}
	}

	public static double scalaire(float[] v1, float[] v2) {
		return Math.sqrt(Math.pow(v1[0] - v2[0], 2.0) + Math.pow(v1[1] - v2[1], 2.0) + Math.pow(v1[2] - v2[2], 2.0));
	}

	@Override
	public void startCalibration() {
		startCalibrate("~/Preferences/couleurs");
	}

	@Override
	public void stopCalibration() {
		// TODO Auto-generated method stub

	}
}
