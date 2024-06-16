package model;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Rappresenta un nemico di tipo Denkyun nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Denkyun extends Enemy{
	
	private final static ImageIcon gif = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/Denkyun.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	
	/**
	 * Crea un nuovo nemico di tipo Denkyun con le coordinate specificate.
     *
     * @param x La coordinata x iniziale del nemico.
     * @param y La coordinata y iniziale del nemico.
	 */
	public Denkyun(int x, int y) {
		super(400,2, x, y, 0.15f, false, false, gif, false);
	}
	
	int moveX = 0;
	int moveY = 1;
	Random dirPicker = new Random();
	/**
	 *definisce il comportamento del nemico Denkyun nel gioco
	 */
	@Override
	public void behaviour() 
	{
		boolean moved = move(moveX, moveY);

	    if (!moved) 
	    {
	        switch(dirPicker.nextInt(4))
	        {
	        case 0: moveX = 1; moveY = 0; break;
	        case 1: moveX = -1; moveY = 0; break;
	        case 2: moveX = 0; moveY = 1; break;
	        case 3: moveX = 0; moveY = -1; break;
	        default: System.out.println("Wrong random value. there are 4 directions, you are trying to pick between more than 4"); break;
	        }
	    }
	    notifyTheObserver(gif);
	}
	
}
