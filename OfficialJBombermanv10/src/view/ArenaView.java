package view;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import model.Arena;
import model.GameManager;

/**
 * Classe che gestisce la parte grafica dell'arena
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ArenaView extends JLabel implements Observer{
	private int[][] griglia;
	private Image[] images;
	
	/**
	 * associa observer e observable per l'arena,
	 *  
	 * @param livello instanza del livell
	 */
	public ArenaView(Arena livello) {
		
		livello.addObserver(this); //aggiunta dell'observer
		
		this.griglia = livello.getGriglia();

		images = new Image[3]; //nelle parentesi quadre ci va il numero di immagini disponibili per la griglia
		
		for (int i = 0; i < images.length; i++) {
            //metodo per caricare l'immagine associata all'indice i
			images[i] = caricaImmagine(i,livello);
        }
	}
	
	/**
	 * quando richiamata aggiorna la grafica dell'arena
	 */
	@Override
    public void update(Observable o, Object griglia) {
        //in arg sara prensente la mia nuova griglia
		this.griglia = (int[][])griglia;
		repaint();
    }
	
	
	//disegno dell'arena
    /**
     *usando la matrice della griglia la disegna con gli sprites appropiati
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = 64;
        int cellHeight = 64;
        
        for (int row = 0; row < griglia.length; row++) {
            for (int col = 0; col < griglia[row].length; col++) {
                int index = griglia[row][col];
                index = index > 2 ? 2 : index;
                Image image = images[index];
                if (image != null) {  
                	if(griglia[row][col]==2)	//if its a gif, paint it on
                	{
            
                		GameManager.paintAnimatedTile(col, row, new ImageIcon(image), g);
                	}
                	else
                		g.drawImage(image, col * cellWidth, row * cellHeight, cellWidth, cellHeight, this);
                }
            }
        }
    }
	
	
	//caricamento delle immagini
	/**
	 * carica dalla cartella resources gli sprites e i GIF per la generazione dell'arena
	 * @param index (per capire se corrisponde ad uno sprite di un pavimento o un muro etc..)
	 * @param lv istanza dell'arena
	 * @return ritorna un lo sprite o il GIF
	 */
	private Image caricaImmagine(int index,Arena lv) {
        // Implementa questo metodo in base alla tua logica per caricare le immagini
		String imagePath = "/resources/tile01.png";
		if(index<0)
		{
			System.out.println("invalid tile index of "+index+"! check level tiles. Changing index to 0...");
			index = 0;
		}
		else if(index==0 || index==1)
			imagePath = "/resources/tile" + index + lv.getTema() + ".png";
		else
		{
			//imagePath = "/resources/box"+lv.getTema()+".gif";
			imagePath = "/resources/box" + lv.getTema()+ ".gif";
		}
			 

		
		return new ImageIcon(getClass().getResource(imagePath)).getImage();
    }
	
}
