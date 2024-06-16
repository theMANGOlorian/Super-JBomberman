package model;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Rappresenta un nemico di tipo "StarNuts" nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class StarNuts extends Enemy
{
	private final static ImageIcon down = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/starNutsDown.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	private final static ImageIcon up = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/starNutsUp.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	private final static ImageIcon left = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/starNutsLeft.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	private final static ImageIcon right = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/starNutsRight.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));


	/**
	 * Costruttore per un nuovo oggetto StarNuts.
     *
     * @param x La coordinata x iniziale di StarNuts.
     * @param y La coordinata y iniziale di StarNuts.
	 */
	public StarNuts(int x, int y) {
		super(200, 1, x, y, 0.15f, false, false, down, false);
		// TODO Auto-generated constructor stub
	}

	int moveX = 1;
	int moveY = 0;
	private ImageIcon currentImage;
	/**
	 *definisce il comportamento di star nuts nel gioco
	 *StarNuts è un nemico che si muove in orizzontale e in verticale, ma cambia direzione solo quando incontra un ostacolo.
     *StatNuts tende ad andare a destra
	 */
	@Override
	protected void behaviour() {
		boolean moved = move(moveX, moveY);

	    if (!moved) 
	    {
	        //Vai alla sua destra
	    	
	    	if(moveX!=0)
	    	{
	    		moveY = moveX;
	    		moveX = 0;
	    	}
		    else if(moveY!=0)
		    {
		    	moveX = -moveY;
		    	moveY = 0;
		    }
	    }
	    
	    if(moveX==1)
	    	currentImage = right;
	    else if(moveX==-1)
	    	currentImage = left;
	    else if(moveY==1)
	    	currentImage = down;
	    else
	    	currentImage = up;
	    
	    //Ricorda che questo non aggiorna solo l'immagine, ma anche le coordinate
	    notifyTheObserver(currentImage);
	}

}
