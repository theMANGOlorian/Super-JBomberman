package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Rappresenta un power-up che aggiunge una vita al giocatore quando viene raccolto.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class BombermanPU extends PowerUp {

	/**
	 * Crea un nuovo power-up Bomberman con le coordinate specificate.
     *
     * @param label  L'etichetta associata al power-up.
     * @param x      La coordinata x del power-up.
     * @param y      La coordinata y del power-up.
     * @param points Il punteggio associato al power-up.
	 */
	public BombermanPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Azione da eseguire quando il power-up viene raccolto. Aggiunge una vita al giocatore.
	 */
	@Override
	public void onPickup() {
		GameManager.addALife();
	}

}
