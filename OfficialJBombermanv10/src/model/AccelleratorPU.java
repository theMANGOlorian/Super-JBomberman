package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Questa classe rappresenta il power-up "Accelerator" nel gioco.
 * Quando viene raccolto da un giocatore, aumenta la velocità del giocatore.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class AccelleratorPU extends PowerUp {

	int points = 400;
	
	/**
	 * /**
     * Crea una nuova istanza del power-up "Accelerator".
     * 
     * @param label   L'etichetta grafica associata al power-up.
     * @param x       La coordinata x in cui si trova il power-up.
     * @param y       La coordinata y in cui si trova il power-up.
     * @param points  Il punteggio associato al power-up.
     */
	public AccelleratorPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
	}
	
	/**
	 *Questo metodo viene chiamato quando il power-up "Accelerator" viene raccolto.
     * Aumenta la velocità del giocatore che lo ha raccolto.
	 */
	@Override
	public void onPickup() {
		PlayerManager.playerInstance.raiseSpeed();
	}
}