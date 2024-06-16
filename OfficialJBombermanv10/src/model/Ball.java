package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import view.GameWindow;

/**
 * La classe Ball rappresenta un nemico a forma di palla nel gioco.
 * @author Mattia Pandolfi & Benjamin Ruff
 *
 */
public class Ball extends Enemy
{
	private final static ImageIcon idle = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/ball.gif")).getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));
	protected boolean isDead = false;
	private final int dirx;
	private final int diry;
	/**
	 * Costruttore per la classe Ball.
     * 
     * @param x   La coordinata x iniziale della posizione della palla.
     * @param y   La coordinata y iniziale della posizione della palla.
     * @param dir La direzione iniziale della palla (0-7, rappresentante le direzioni).
	 */
	public Ball(int x, int y, int dir) {
		super(0, 1, x, y, 0.75, false, false, idle, false);
		System.out.println("i did a ball");
		switch(dir)
		{
			case 0:  dirx =  0; diry = -1; break; //su
			case 1:  dirx =  1; diry = -1; break; //sudestra
			case 2:  dirx =  1; diry =  0; break; //destra
			case 3:  dirx =  1; diry =  1; break; //destragiu
			case 4:  dirx =  0; diry =  1; break; //giu
			case 5:  dirx = -1; diry =  1; break; //sinistragiu
			case 6:  dirx = -1; diry =  0; break; //sinistra
			default: dirx = -1; diry = -1; break; //sinistrasu
		}
		view.setSize(64,64);
	}

	/**
	 *Definisce il comportamento della palla.
	 */
	@Override
	protected void behaviour() 
	{
		System.out.println(this.x+" "+this.y);
		this.x += dirx*GameManager.getDeltaTime()*0.15;
		this.y += diry*GameManager.getDeltaTime()*0.15;
		
		notifyTheObserver(idle);
		if(this.x<32 || this.x>800 || this.y<32 || this.y>800)
		{
			System.out.println("killed a ball");
			GameWindow.removeLabelFromFrame(view);
			isDead = true;
		}
	}

}
