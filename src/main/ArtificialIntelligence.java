package main;

import lejos.hardware.Button;
import lejos.hardware.Sounds;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ArtificialIntelligence implements Sounds {

	protected enum Statut {
		STANDBY, DEMARRAGE, GOLLUM, SHERLOCK, EGOCENTRIQUE, SHERLOCK2, FIN
	}

	private Statut statut;
	/**
	 * Les différents moteurs
	 */
	private Pliers pliers;
	private Chassis chassis;

	/**
	 * Les différents capteurs
	 */
	private Ultrason ultrason;
	private Touch touch;
	private Colors colors;

	/**
	 * Les différents flags
	 */

	private boolean firstTour;
	
	//*Variables globales
	float distMin;

	public ArtificialIntelligence() {
		this.statut = Statut.STANDBY;
		this.pliers = new Pliers(Motor.B);
		this.chassis = new Chassis(Motor.A, Motor.C, 56, 68);
		this.ultrason = new Ultrason(SensorPort.S3);
		this.touch = new Touch(SensorPort.S1);
		// this.colors = new Colors(SensorPort.S2);
		this.firstTour = true;
		LCD.drawString("Tout est instancié", 1, 1);
		Delay.msDelay(2000);
		distMin= Float.MAX_VALUE;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public void actionToDo() {
		int iteration = 1000;
		boolean encore = true;
		while (encore)
			switch (statut) {
			case STANDBY:
				actionStandby();
				break;
			case DEMARRAGE:
				actionDemarrage();
				break;
			case GOLLUM:
				actionGollum();
				break;
			case SHERLOCK:
				actionSherlock(iteration);
				break;
			case EGOCENTRIQUE:
				actionEgocentrique();
				break;
			case SHERLOCK2:
				actionSherlock2(iteration);
				break;
			case FIN:
				actionFin();
				encore = false;
				break;
			}
	}

	// Initialisation de tous les capteurs/moteurs/couleurs
	private void actionStandby() {
		LCD.clear();
		LCD.drawString("Wait", 1, 1);
		LCD.drawString("Appuyez sur ENTER", 1, 2);
		LCD.drawString("pour commencer", 1, 3);
		LCD.drawString("Statut : STANBY", 1, 7);
		Button.ENTER.waitForPress();
		statut = Statut.DEMARRAGE;
	}

	// Avancer pour chercher palet
	private void actionDemarrage() {
		distMin=Float.MAX_VALUE;
		LCD.clear();
		LCD.drawString("Statut : DEM", 1, 7);
		chassis.forward(150, 100000);
		/**
		 * Il faudrait qu'on fasse varier la vitesse
		 */
		LCD.drawString("Moteurs demarres", 1, 2);

		while (!ultrason.detectionPalet(.4f, 200))
			;
		pliers.open();
		if (touch.isPressed()) {
			pliers.close();
		}
		statut = Statut.GOLLUM;
	}

	// Aller déposer le palet dans la zone ennemie
	private void actionGollum() {
		LCD.clear();
		LCD.drawString("Statut : GOLLUM", 1, 7);
		pliers.close();
		LCD.drawString("Palet : recup", 1, 5);
		chassis.deviation();
		LCD.drawString("Dir : deviation", 1, 6);
		chassis.forward(800, 1000);
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !! ATTENTION: !! !!
		 * Il faut rajouter là un test pour la couleur BLANCHE !!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		statut = Statut.SHERLOCK;
	}

    // Après dépôt, rechercher nouveau palet tour 180
	public void actionSherlock(int iteration) {
		LCD.clear();
		LCD.drawString("Statut : GOLLUM", 1, 7);
		pliers.open();
		chassis.forward(-800, 10);
		chassis.halfRotation(true);
		chassis.uTurnResearch();
		while(chassis.isMoving()){
			if(ultrason.giveDistance()<distMin){
			distMin=ultrason.giveDistance();
			}
		};
		chassis.uTurnResearch();
		if(ultrason.giveDistance()==distMin){
			chassis.stop();
		}
		statut = Statut.DEMARRAGE;
	}

public boolean isMoving(){
		return this.chassis.isMoving();
	}



	// Si on trouve pas de palet à partir de sherlock, alors egocentrique : aller au
	// centre
	private void actionEgocentrique() {
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!
		 * ATTENTION: !! !! Il faut rajouter là une méthode pour rejoindre le centre!!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		chassis.stop();
		statut = Statut.SHERLOCK2;
	}

	// Recherche palet tour complet 360
	private void actionSherlock2(int iteration) {
		boolean test;
		chassis.completeTurnResearch();
		for (int i = 0; i < iteration; i++) {
			test = ultrason.detectionPalet(20);
			if (test) {
				statut = Statut.DEMARRAGE;
				continue;
			}
		}
		statut = Statut.FIN;
	}

	// Fin de la partie
	private void actionFin() {
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!
		 * ATTENTION: !! !! Il faut rajouter là une méthode pour rejoindre notre camp!!!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		chassis.uTurn();
		chassis.forward(-800, 100);
		ultrason.close();
		touch.close();
		colors.close();

	}
}
