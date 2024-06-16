package view;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import model.Player;

/**
 * Classe per la parte grafica del giocatore
 * @author Benjamin Ruff & Mattia Pandolf
 *
 */
public class PlayerView extends JLabel implements Observer {
    private int x, y;
    private Player p;
    final int timeBetweenflashes = 100;
    private long lastTimeSinceFlick = System.currentTimeMillis();
    
    /**
     * costruttore, permette di associare observer e observable del player e carica il primo sprite del personaggio
     * @param player, istanza del giocatore
     */
    public PlayerView(Player player) {
    	p = player;
    	setBounds(64,16,64,64+32);
        p.addObserver(this); // associazione del observer all'observable

        // Primo sprite del player
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/down2.png"));
        // Ridimensiona l'immagine all'inizio
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(
            originalIcon.getIconWidth() * 4, originalIcon.getIconHeight() * 4, Image.SCALE_DEFAULT
        );
        
        
        setIcon(new ImageIcon(scaledImage));
    }
    public void showInvincible()
    {
    	if(p.isInvincible())
    	{
    		if(System.currentTimeMillis() - lastTimeSinceFlick > timeBetweenflashes)
    		{
    			setVisible(!isVisible());
    			lastTimeSinceFlick = System.currentTimeMillis();
    		}
    	}
    	else
    	{
    		setVisible(true);
    	}
    }
    
    /**
     *Imposta la dimensione del JLabel in base alle dimensioni dell'immagine del personaggio
     */
    @Override
    public Dimension getPreferredSize() {
        // Imposta la dimensione del JLabel in base alle dimensioni dell'immagine del personaggio
        if (getIcon() != null) {
            return new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
        }
        return super.getPreferredSize();
    }
    /**
     * aggiorna la grafica del giocatore quando viene notificato
     */
    @Override
    public void update(Observable o, Object arg) {
        ArrayList<Object> args = (ArrayList<Object>) arg;
        x = (int) args.get(0);
        y = (int) args.get(1);
        Image playerImage = (Image) args.get(2);

        // Crea una nuova ImageIcon con l'immagine aggiornata del personaggio
        ImageIcon updatedIcon = new ImageIcon(playerImage);
        setIcon(updatedIcon);

        // Aggiorna la posizione del JLabel
        setLocation(x, y);

        // Richiama repaint() per aggiornare l'immagine del personaggio nella nuova posizione
        repaint();
    }
}
