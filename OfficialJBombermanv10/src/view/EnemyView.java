package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import model.Enemy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * Classe per la gestione della parte grafica dei nemici
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class EnemyView extends JLabel implements Observer{
	
	private int x,y;
	private final int xOffset, yOffset;
	private Enemy enemy;
	private ImageIcon immagine;
	
	/**
	 * @param e, istanza del nemico
	 * @param boss, variabiler booleana per definire un boss
	 */
	public EnemyView(Enemy e, boolean boss)
	{
		setLayout(null);
		if(boss)
		{
			xOffset = 48*4;
			yOffset = 85*4;
			setSize(110*4, 151*4);
		}
		else {
			xOffset = 0;
			yOffset = 48;
			setSize(64,96);
		}
		
		
		enemy = e;
		enemy.addObserver(this); //associare observer all observable
		GameWindow.addToFrame(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
        // Imposta la dimensione del JLabel in base alle dimensioni dell'immagine del personaggio
        if (getIcon() != null) {
            return new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
        }
        return super.getPreferredSize();
    }
	
	final int timeBetweenflashes = 100;
    private long lastTimeSinceFlick = System.currentTimeMillis();
	public void showHit()
    {
    	if(System.currentTimeMillis() - lastTimeSinceFlick > timeBetweenflashes)
    		{
    			setVisible(!isVisible());
    			lastTimeSinceFlick = System.currentTimeMillis();
    		}
    }
	
	/**
	 * aggiorna la grafica ogni volta che nella model della classe Enemy avviene un cabiamento
	 */
	@Override
	public void update(Observable o, Object arg) {
		ArrayList<Object> args = (ArrayList<Object>) arg;		
		x = (int) args.get(0)-xOffset;
		y = (int) args.get(1)-yOffset;
		
		if( (ImageIcon)args.get(2) != immagine || immagine == null){
			immagine = (ImageIcon) args.get(2);
		}
		
		setIcon(immagine);
		setLocation(x,y);
		
		repaint();
	}
}
