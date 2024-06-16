package model;

import java.awt.Image;

import javax.swing.ImageIcon;


/**
 * Rappresenta un nemico di tipo "Puropen" nel gioco.
 * 
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Puropen extends Enemy{
	
	private final static ImageIcon down = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/puropenDown.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	private final static ImageIcon up = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/puropenUp.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	private final static ImageIcon left = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/puropenLeft.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	private final static ImageIcon right = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/puropenRight.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));

	
	/**
	 * Costruttore per un nuovo oggetto Puropen.
     *
     * @param x La coordinata x iniziale del Puropen.
     * @param y La coordinata y iniziale del Puropen.
	 */
	public Puropen(int x, int y){
		
		super(100,1, x, y, 0.15f, false, false, down, false);
	}
	
	
	private int moveX = 1; // Start by moving right
	private int moveY = 0;
	boolean previouslyMoved = true;
	private ImageIcon currentImage;

	/**
	 * definisce il comportamento di puropen nel gioco
	 * Puropen è un nemico che si muove in orizzontale e in verticale, cambiando direzione quando incontra un ostacolo.
     * Se si incastra, cambia direzione di movimento.
	 */
	@Override
	public void behaviour() {
		
		//Logica dietro algoritmo:
		//Puropen si muove a destra e a sinistra (cambiando direzione quando collision), e se si incastra cambia a muoversi su e giu, e viceversa
		//Per vedere se si incastra, memorizziamo la direzione in cui sta andando, e se si è mosso prima
		//Se non si è mosso, ma prima si, allora semplicemente si gira nella direzione opposta
		//Se non si è mosso, ne ora ne prima, allora si è incastrato, cambia asse di movimento
		
		//vedi se si è mosso
	    boolean moved = move(moveX, moveY);

	    if (!moved) 
	    {
	        if(moveX != 0)
	        {
	        	//Se non si è mosso, ma prima si
	        	if(previouslyMoved)
	        	{
	        		//si gira nella direzione opposta
	        		moveX = -moveX;
	        		previouslyMoved = false;
	        	}
	        	//Se non si è mosso, ne ora ne prima
	        	else 
	        	{
	        		//si è incastrato, cambia asse di movimento
	        		moveX = 0;
	        		moveY = 1;
	        		previouslyMoved = true;
	        	}
	        }
	        else //se x è zero, y non lo è
	        {
	        	if(previouslyMoved)
	        	{
	        		moveY = -moveY;
	        		previouslyMoved = false;
	        	}
	        		
	        	else
	        	{
	        		moveY = 0;
	        		moveX = 1;
	        		previouslyMoved = true;
	        	}	
	        }
	    }
	    else
	    	previouslyMoved = true;
	    
	    
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
