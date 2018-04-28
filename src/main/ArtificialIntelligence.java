package main;

import lejos.hardware.Button;
import lejos.hardware.Sounds;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
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
	private boolean secondTour;

	/**
	 * Les différentes variables globales
	 */
	float distMin;

	public ArtificialIntelligence() {
		this.statut = Statut.STANDBY;
		this.pliers = new Pliers(Motor.B);
		this.chassis = new Chassis(Motor.A, Motor.C, 56, 68);
		this.chassis.forward(800, 1);
		this.ultrason = new Ultrason(SensorPort.S3);
		this.touch = new Touch(SensorPort.S1);
		this.colors = new Colors(SensorPort.S2);
		this.firstTour = true;
		this.secondTour = false;
		LCD.drawString("Tout est instancié", 1, 1);
		Delay.msDelay(2000);
		this.colors.initColors();
		distMin = Float.MAX_VALUE;
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
		boolean mur = false;
		LCD.clear();
		LCD.drawString("Statut : DEM", 1, 7);
		if(secondTour) {
			chassis.deviation(false);
			secondTour = false;
		}
		chassis.forward(180, 100000);
		/**
		 * Il faudrait qu'on fasse varier la vitesse
		 */
		LCD.drawString("Moteurs demarres", 1, 2);
		while (Float.isInfinite(ultrason.getDistance()))
			;
		while (!Float.isInfinite(ultrason.getDistance()))
			if (ultrason.getDistance() < 0.32) {
				chassis.stop();
				mur = true;
				break;
			}
		if (!mur) {
			pliers.open();
			if (touch.isPressed()) {
				pliers.close();
			}
			statut = Statut.GOLLUM;
		} else {
			chassis.rotate(45);
			statut = Statut.SHERLOCK;
		}

	}

	// Aller déposer le palet dans la zone ennemie
	private void actionGollum() {
		LCD.clear();
		LCD.drawString("Statut : GOLLUM", 1, 7);
		pliers.close();
		LCD.drawString("Palet : recup", 1, 5);
		if (firstTour) {
			chassis.deviation(true);
			LCD.drawString("Dir : deviation", 1, 6);
			firstTour = false;
			secondTour = true;
		} else {
			chassis.uTurn();
			LCD.drawString("Dir : 1/2 tour", 1, 6);
		}
		chassis.forward(350, 10000);
		while (!this.colors.depassementCouleur("white"))
			;
		chassis.stop();
		pliers.open();
		chassis.backward(800, 200);
		pliers.close();
		chassis.halfRotation(true);
		statut = Statut.SHERLOCK;
	}

	// Après dépôt, rechercher nouveau palet tour 180
	public void actionSherlock(int iteration) {
		distMin = Float.MAX_VALUE;
		boolean trouve = false;
		LCD.clear();
		LCD.drawString("Statut : SHERLOCK", 1, 7);
		chassis.uTurnResearch(true);
		while (chassis.isMoving()) {
			LCD.drawString("isMoving : Yes", 1, 6);
			if (ultrason.giveDistance() < distMin) {
				distMin = ultrason.giveDistance();
			}
		}
		chassis.stop();
		chassis.uTurnResearch(false);
		while (chassis.isMoving()) {
			LCD.drawString("" + Math.abs(ultrason.giveDistance() - distMin), 1, 4);
			if (Math.abs(ultrason.giveDistance() - distMin) <= 0.4f
					&& Math.abs(ultrason.giveDistance() - distMin) >= 0.005f) {
				chassis.stop();
				LCD.clear(6);
				LCD.drawString("isMoving : Non", 1, 6);
				trouve = true;
				break;
			}
		}
		chassis.uTurnCorrect(true);
		if (trouve)
			statut = Statut.DEMARRAGE;
		else
			statut = Statut.SHERLOCK;
	}

	public boolean isMoving() {
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
		chassis.forward(800, 10000);
		ultrason.close();
		touch.close();
		colors.close();

	}
}
