package main;

import lejos.hardware.Button;
import main.ArtificialIntelligence.Statut;

public class AutomateAI {

	
	public static void automate() {
		
	ArtificialIntelligence ai = new ArtificialIntelligence();
	Button.waitForAnyEvent();
	ai.setStatut(Statut.DEMARRAGE)
	;
	}
}
