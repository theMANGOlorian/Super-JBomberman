package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Questa classe rappresenta un power-up che conferisce invincibilità al giocatore.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ArmorPU extends PowerUp {

	/**
	 * Crea un nuovo oggetto ArmorPU.
     *
     * @param label   L'etichetta associata al power-up.
     * @param x       La coordinata x del power-up sulla griglia di gioco.
     * @param y       La coordinata y del power-up sulla griglia di gioco.
     * @param points  Il punteggio assegnato al power-up.
	 */
	public ArmorPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
	}

	/**
	 *Gestisce l'effetto del power-up quando viene raccolto dal giocatore.
     * Conferisce invincibilità al giocatore per un periodo di tempo.
	 */
	@Override
	public void onPickup() {
		PlayerManager.playerInstance.setInvincible(10000);
	}

}
