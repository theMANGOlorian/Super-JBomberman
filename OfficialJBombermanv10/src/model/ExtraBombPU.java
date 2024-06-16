package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Rappresenta un power-up di tipo "Extra Bomb" nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ExtraBombPU extends PowerUp 
{
	
	/**
	 * Crea un nuovo power-up "Extra Bomb" con l'etichetta, le coordinate e i punti specificati.
     *
     * @param label  L'etichetta associata al power-up.
     * @param x      La coordinata x del power-up.
     * @param y      La coordinata y del power-up.
     * @param points I punti associati al power-up.
	 */
	public ExtraBombPU(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
	}
	
	/**
	 * Azione da eseguire quando il power-up "Extra Bomb" viene raccolto dal giocatore.
     * Aggiunge una bomba alla capacità massima del giocatore.
	 */
	@Override
	public void onPickup() {
		PlayerManager.playerInstance.addABomb();
	}
}
