package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ArtificialIntelligence {

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

	public ArtificialIntelligence() {
		this.statut = Statut.STANDBY;
		this.pliers = new Pliers(Motor.B);
		this.chassis = new Chassis(Motor.A, Motor.C, 56, 68);
		this.ultrason = new Ultrason(SensorPort.S3);
		this.touch = new Touch(SensorPort.S1);
	//	this.colors = new Colors(SensorPort.S2);
		LCD.drawString("Tout est instancié", 1, 1);
		Delay.msDelay(2000);
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
		LCD.drawString("Appuyez sur ENTER",1,2);
		LCD.drawString("pour commencer", 1,3);
		LCD.drawString("Statut : STANBY", 1, 7);
		Button.ENTER.waitForPress();
		statut = Statut.DEMARRAGE;
	}

	// Avancer pour chercher palet
	private void actionDemarrage() {
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
		chassis.deviation();
		Delay.msDelay(6000);
		chassis.forward(800,100);
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !! ATTENTION: !! !!
		 * Il faut rajouter là un test pour la couleur BLANCHE !!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		statut = Statut.SHERLOCK;
	}

	// Après dépôt, rechercher nouveau palet tour 180
	private void actionSherlock(int iteration) {
		boolean test;
		LCD.drawString("Statut : SHERLOCK", 7, 1);
		pliers.open();
		chassis.forward(-800, 10);
		chassis.halfRotation();
		chassis.uTurnResearch();
		for (int i = 0; i < iteration; i++) {
			test = ultrason.detectionPalet(20);
			if (test) {
				statut = Statut.DEMARRAGE;
				continue;
			}
		}
		statut = Statut.EGOCENTRIQUE;
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
	}
}
