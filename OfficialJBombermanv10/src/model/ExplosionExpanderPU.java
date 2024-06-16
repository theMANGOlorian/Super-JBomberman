package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Rappresenta un power-up di tipo "Explosion Expander" nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ExplosionExpanderPU extends PowerUp {

	
	
	/**
	 * Crea un nuovo power-up "Explosion Expander" con l'etichetta, le coordinate e i punti specificati.
     *
     * @param label  L'etichetta associata al power-up.
     * @param x      La coordinata x del power-up.
     * @param y      La coordinata y del power-up.
     * @param points I punti associati al power-up.
	 */
	public ExplosionExpanderPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
		super.points = 200;
	}
	
	/**
	 *Azione da eseguire quando il power-up "Explosion Expander" viene raccolto dal giocatore.
     * Espande la dimensione delle esplosioni delle bombe del giocatore.
	 */
	@Override
	public void onPickup() {
		PlayerManager.playerInstance.expandBombSize(false);
	}
}

