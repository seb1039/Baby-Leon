package main;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
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

	public ArtificialIntelligence(Statut statut, Pliers pliers, Chassis chassis, Ultrason ultrason, Touch touch,
			Colors colors) {
		this.statut = statut;
		this.pliers = pliers;
		this.chassis = chassis;
		this.ultrason = ultrason;
		this.touch = touch;
		this.colors = colors;
	}

	public ArtificialIntelligence() {
		this.statut = Statut.STANDBY;
		this.pliers = new Pliers(Motor.B);
		this.chassis = new Chassis(Motor.A, Motor.C, 56, 68);
		this.ultrason = new Ultrason(SensorPort.S3);
		this.touch = new Touch(SensorPort.S1);
		this.colors = new Colors(Motor.B);
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public void actionToDo() {
		switch (statut) {
		case DEMARRAGE:
			Thread palet = new Thread(new Runnable() {
				private boolean test = true;

				@Override
				public void run() {
					while (test)
						test = ultrason.detectionPalet(.4f, 200);
				}
			});
			palet.start();
			pliers.open();
			chassis.forward(700, 1000);
			try {
				palet.join();
				LCD.drawString("Détecté", 1, 3);
			} catch (InterruptedException e) {
				LCD.drawString("InterruptedException", 1, 2);
			}
			pliers.open();
			int wait = 500;
			chassis.forward(100, wait);
			if (touch.isPressed()) {
				pliers.close();
			}
			statut = Statut.GOLLUM;
			break;
		case GOLLUM:
			pliers.close();
			

		}
	}

}
