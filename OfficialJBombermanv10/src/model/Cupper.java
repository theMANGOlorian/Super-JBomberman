package model;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Rappresenta un nemico di tipo Cupper nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Cupper extends Enemy{

	private final static ImageIcon spinning = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/cupper.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));

	
	/**
	 * Crea un nuovo nemico di tipo Cupper con le coordinate specificate.
     *
     * @param x La coordinata x iniziale del nemico.
     * @param y La coordinata y iniziale del nemico.
	 */
	public Cupper(int x, int y){
		
		super(1000,1, x, y, 0.15f, true, false, spinning, false);
	}
	
    private int moveX = 1; // Inizia muovendosi a destra
    private int moveY = 0;
    boolean previouslyMoved = true;
    private ImageIcon currentImage = spinning;
    
    int[][] matrice = GameManager.getArena().getGriglia();
    int righe = 12;
    int colonne = 12;
    
	/**
	 *Definisce il comportamento del nemico Cupper nel gioco
	 *in un momento random si teletrasporta in un posto a caso sulla mappa
	 */
	@Override
	public void behaviour() {
		
		Random random = new Random();
		Random coordinateRand = new Random();
		
		boolean moved = move(moveX, moveY);
		
		//Teleport
		if(random.nextDouble() < 0.001) {
			
			while (true) {
				
	            int x = coordinateRand.nextInt(righe);
	            int y = coordinateRand.nextInt(colonne);
	            
//	            for(int[]i:matrice) {
//	            	for(int j: i) {
//	            		System.out.print(j);
//	            	}
//	            	System.out.println(" ");
//	            }
//	            
	            if (matrice[x][y] == 0 ) {
	                super.x = x*64;
	                super.y = y*64;
	               	                
	                break;	                
	            }
	        }
			
		}
		
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
	    
	    
	    //Ricorda che questo non aggiorna solo l'immagine, ma anche le coordinate
	    notifyTheObserver(currentImage);
	}
}
