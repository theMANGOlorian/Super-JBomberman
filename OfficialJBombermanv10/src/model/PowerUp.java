package model;

import javax.swing.JLabel;

import controller.PlayerManager;

/**
 * Classe astratta che rappresenta un Power-Up nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public abstract class PowerUp 
{	
	int points;
	JLabel label;
	int x;
	int y;
	
	/**
	 * Costruttore per un Power-Up.
     *
     * @param label   L'etichetta (JLabel) associata al Power-Up.
     * @param x       La coordinata x del Power-Up sulla mappa, non in pixel.
     * @param y       La coordinata y del Power-Up sulla mappa, non in pixel.
     * @param points  I punti che il Power-Up assegna quando viene raccolto.
	 */
	public PowerUp(JLabel label, int x, int y, int points)
	{
		this.points = points;
		this.x = x;
		this.y = y;
		this.label = label;
	}
	/**
	 * @return the JLabel that is associated with the power up 
	 */
	public JLabel getLabel() {
		return label;
	}
	
	/**
	 * @return the points that the Power Up gives when picked up
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Method that gets called when this Power Up is picked up
	 */
	public abstract void onPickup();
	
	/**
	 * Metodo chiamato quando il Power-Up viene raccolto.
	 */
	public void pickup()
	{
		GameManager.addScore(points);
		onPickup();
		System.out.println(points);
		GameManager.collectPowerUp(this);
	}
	
	/**
	 * @return the x coordinate of the PowerUp on the board, not in pixels
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y coordinate of the PowerUp on the board, not in pixels
	 */
	public int getY() {
		return y;
	}	
}
