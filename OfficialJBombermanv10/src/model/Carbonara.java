package model;

import javax.swing.JLabel;

/**
 * Rappresenta un power-up Carbonara che fornisce punti al giocatore quando viene raccolto.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Carbonara extends PowerUp {

	/**
	 * Crea un nuovo power-up Carbonara con le coordinate specificate.
     *
     * @param label  L'etichetta associata al power-up.
     * @param x      La coordinata x del power-up.
     * @param y      La coordinata y del power-up.
     * @param points Il punteggio associato al power-up.
	 */
	public Carbonara(JLabel label, int x, int y, int points) {
		super(label, x, y, points);
	}

	/**
	 * Azione da eseguire quando il power-up viene raccolto. Il power-up Carbonara fornisce punti al giocatore.
     *
	 */
	@Override
	public void onPickup() {
		//Carbonara non fa nulla oltre a dare punti
		return;
	}

}
