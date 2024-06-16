package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Rappresenta un power-up che rende le bombe attraversabili dal giocatore quando viene raccolto.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class BombPasserPU extends PowerUp {

	/**
	 * Crea un nuovo power-up BombPasser con le coordinate specificate.
     *
     * @param label  L'etichetta associata al power-up.
     * @param x      La coordinata x del power-up.
     * @param y      La coordinata y del power-up.
     * @param points Il punteggio associato al power-up.
	 */
	public BombPasserPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
	}

	/**
	 *Azione da eseguire quando il power-up viene raccolto. Rende le bombe attraversabili dal giocatore.
	 */
	@Override
	public void onPickup() {
		PlayerManager.playerInstance.makeBombsWalkable();
	}

}
