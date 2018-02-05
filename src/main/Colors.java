package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class Colors {

	public static void colorsTest() {

		// Initialiser les vecteurs couleurs types (vert, rouge, noir, jaune,
		// blanc, gris)
		// On a un vecteur couleur observé
		// Faire le produit scalaire entre obs et tous les vect couleurs types
		// Le produit scalaire minimal est considérer comme la couleur lue

		//EV3ColorSensor color = new EV3ColorSensor(SensorPort.S2);
		//SampleProvider colorer = color.getRGBMode();
		
		Port port = LocalEV3.get().getPort("S2");
		EV3ColorSensor color = new EV3ColorSensor(port);
		SampleProvider colorer = new MeanFilter(color.getRGBMode(), 1);
		color.setFloodlight(Color.WHITE);
		
		//Vecteurs couleurs du plateau 
				
		float[] Blue = new float[3];
		Blue[0]=(float) 6.5;
		Blue[1]=(float) 21.25;
		Blue[2]=(float) 18;
		
		float[] Black = new float[3];
		Black[0]=(float) 3.83;
		Black[1]=(float) 7.08;
		Black[2]=(float) 3.17;
		
		float[] Red = new float[3];
		Red[0]=(float) 26.33;
		Red[1]=(float) 7.8;
		Red[2]=(float) 3.13;

		float[] Grey = new float[3];
		Grey[0]=(float) 17.75;
		Grey[1]=(float) 19;
		Grey[2]=(float) 12.16;
		
		float[] White = new float[3];
		White[0]=(float) 35.58;
		White[1]=(float) 40.75;
		White[2]=(float) 26.58;
		
		float[] Yellow = new float[3];
		Yellow[0]=(float) 36.83;
		Yellow[1]=(float) 34.75;
		Yellow[2]=(float) 7.66;
		
		
		
		//Tableau rassemblant ces couleurs types
		float[][] ColorTab = new float[6][3];
		ColorTab[0]= Black;
		ColorTab[1]= Blue;
		ColorTab[2]= Grey;
		ColorTab[3]= Red;
		ColorTab[4]= White;
		ColorTab[5]= Yellow;	
		
		//Tableau de String répertoriant les couleur
		String[] ColorString = new String[6];
		ColorString[0]= "Black";
		ColorString[1]= "Blue";
		ColorString[2]= "Grey";
		ColorString[3]= "Red";
		ColorString[4]= "White";
		ColorString[5]= "Yellow";
		

		float[] sample = new float[colorer.sampleSize()];
		
		float R = 0;
		float G = 0;
		float B = 0;
		
		boolean test = true;
		
		Button.waitForAnyPress();
		colorer.fetchSample(sample, 0);
		R = sample[0] * 255;
		G = sample[1] * 255;
		B = sample[2] * 255;
			
		//Calcul des produits scalaire entre ce qui est mesuré sur le plateau et tous les vecteurs couleurs types
		float[] prodScal = new float[6];
		
		for(int i=0; i<6; i++){
				prodScal[i]=(float) Math.sqrt(Math.pow(R - ColorTab[i][0] , 2.0) + Math.pow(G - ColorTab[i][1], 2.0) + Math.pow(B - ColorTab[i][2], 2.0));
			}			
		
		//Couleur observée = produit scal max
		//ColorTab[obs] = couleur observée
		int obs = 0;
		float min = 255;
		for(int i=0; i<6; i++){
			if(prodScal[i]<min){
				min = prodScal[i];
				obs=i;
			}
		}
		
		
		LCD.drawString(ColorString[obs] , 1, 2);
		LCD.drawString("R: "+R, 1, 3);
		LCD.drawString("G: "+G, 1, 4);
		LCD.drawString("B: "+B, 1, 5);

		
		/***
			LCD.drawString("rouge : "+ R, 1, 2);
			LCD.drawString("vert : " + G, 1, 3);
			LCD.drawString("bleu : " + B, 1, 4);
			***/
		
		Button.waitForAnyPress();
	}

}
