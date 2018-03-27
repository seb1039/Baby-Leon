package main;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;

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

	private ArtificialIntelligence() {
		this.statut = Statut.STANDBY;
		this.pliers = new Pliers(Motor.B);
		this.chassis = new Chassis(Motor.A, Motor.C, 56, 68);
		this.ultrason = new Ultrason(SensorPort.S3);
		this.touch = new Touch(SensorPort.S1);
		this.colors = new Colors((Port) Motor.B);
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public void actionToDo() {
		int iteration = 1000;
		boolean test;
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

	//Initialisation de tous les capteurs/moteurs/couleurs
	public void actionStandby() {
		LCD.drawString("Appuyez sur ENTER pour commencer", 3, 1);
		Button.ENTER.waitForPress();
		statut = Statut.DEMARRAGE;
	}

	//Avancer pour chercher palet
	public void actionDemarrage() {
		pliers.open();
		chassis.forward(700, 10000);
		if (touch.isPressed())
			pliers.close();
		statut = Statut.GOLLUM;
	}
	
	

	//Aller déposer le palet dans la zone ennemie
	public void actionGollum() {
		pliers.close();
		chassis.deviation();
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !! ATTENTION: !! !!
		 * Il faut rajouter là un test pour la couleur BLANCHE !!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
	}

	//Après dépôt, rechercher nouveau palet tour 180
	public void actionSherlock(int iteration) {
		boolean test;
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

	//Si on trouve pas de palet à partir de sherlock, alors egocentrique : aller au centre
	public void actionEgocentrique() {
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!
		 * ATTENTION: !! !! Il faut rajouter là une méthode pour rejoindre le centre!!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		chassis.stop();
		statut = Statut.SHERLOCK2;
	}

	//Recherche palet tour complet 360
	public void actionSherlock2(int iteration) {
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

	//Fin de la partie
	public void actionFin() {
		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!
		 * ATTENTION: !! !! Il faut rajouter là une méthode pour rejoindre notre camp!!!
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */
		chassis.uTurn();
		chassis.forward(-800, 100);
	}
}
