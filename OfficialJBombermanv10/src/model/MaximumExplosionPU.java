package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Rappresenta un power-up che aumenta la dimensione massima dell'esplosione delle bombe del giocatore.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class MaximumExplosionPU extends PowerUp {

	/**
	 * Crea un nuovo power-up "MaximumExplosionPU" con le specifiche immagini e posizione.
     *
     * @param label  L'etichetta (immagine) associata a questo power-up.
     * @param x      La coordinata x della posizione del power-up.
     * @param y      La coordinata y della posizione del power-up.
     * @param points Il punteggio assegnato al giocatore quando raccoglie questo power-up.
	 */
	public MaximumExplosionPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
	}

	/**
	 *Azione eseguita quando il giocatore raccoglie questo power-up.
     * Aumenta la dimensione al massimo dell'esplosione delle bombe del giocatore.
	 */
	@Override
	public void onPickup() {
		PlayerManager.playerInstance.expandBombSize(true);
	}

}
