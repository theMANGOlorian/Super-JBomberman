package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.GameManager;

/**
 * Classe per la grafica del Menu di inizio
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class MenuPanel extends JPanel
{
	final static Image backgroundImage = new ImageIcon(GameManager.class.getResource("/resources/menuBackground.png")).getImage();
	final static Image titleCardImage = new ImageIcon(GameManager.class.getResource("/resources/menuTitle.png")).getImage();
	final static Image baloonImageR = new ImageIcon(GameManager.class.getResource("/resources/menuBaloonR.png")).getImage();
	final static Image baloonImageL = new ImageIcon(GameManager.class.getResource("/resources/menuBaloonL.png")).getImage();
	
	static Image renderingBaloon = baloonImageR;
	
	int titleY = (GameWindow.windowHeight/100)*-200;
	
	
	static int baloonx = -baloonImageR.getWidth(null);
	static int baloony = 100;
	
	final static int minBaloonY = 100;	//the smallest y at which the baloon can be
	final static int maxBaloonY = 650;	//the biggest y at which the baloon can be
	
	static float fadeAlpha = 1;
	static float multiplier = 0.001f;
	private static ProfilePanel profile = new ProfilePanel();
	
	
	public MenuPanel()
	{
		setOpaque(false);
		setLayout(null);
	}
	
	/**
	 *disegna il menu panel
	 */
	@Override
	public void paint(Graphics g)
	{
		//this.g = g;
		Graphics2D g2d = (Graphics2D) g;
		
		//prima si disegna il background
		g2d.drawImage(backgroundImage, 0, 0, null);
		g2d.drawImage(renderingBaloon, baloonx, baloony, null);
		g2d.drawImage(titleCardImage, 0, titleY, null);
		
		//poi i componenti (pulsanti e overlay profilo)
		super.paint(g);
		
		//poi l'overlay nero di transizione
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, GameWindow.windowWidth, GameWindow.windowWidth);
	}
	/**
	 * aggiorna il profilo selezionato
	 */
	public static void updateProfilePicker()
	{
		profile.updateSelection();
	}
	/**
	 * apre il profile picker
	 */
	public static void openProfilePicker()
	{
		//buttonsVisible = false;
		
		MenuPanel p = GameWindow.getMenuPanel();
		for(int i = 0; i<p.getComponentCount(); i++)
		{
			p.getComponent(i).setEnabled(false);
			p.getComponent(i).setVisible(false);
		}
		p.add(profile);
		p.repaint();
		profile.repaint();
	}
	/**
	 * chiude il profile picker
	 */
	public static void closeProfilePicker()
	{
		//buttonsVisible = false;
		
		MenuPanel p = GameWindow.getMenuPanel();
		p.remove(profile);
		for(int i = 0; i<p.getComponentCount(); i++)
		{
			p.getComponent(i).setEnabled(true);
			p.getComponent(i).setVisible(true);
		}
		
	}
	
	/**
	 * rende animata il menu iniziale (aniamzione del titolo etc..)
	 */
	public void animateIntro()
	{
		while(fadeAlpha>0.01f)
		{
			multiplier= multiplier*1.1f;
			fadeAlpha=1-multiplier;
			if(fadeAlpha<0.01f)
				fadeAlpha = 0.01f;
			try {
				Thread.sleep(GameManager.minMS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			
		}
		while(titleY<0)
		{
			titleY+=20;
			try {
				Thread.sleep(GameManager.minMS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
		MenuPanel p = GameWindow.getMenuPanel();
		for(int i = 0; i<p.getComponentCount(); i++)
		{
			p.getComponent(i).setEnabled(true);
			p.getComponent(i).setVisible(true);
		}
		repaint();
	}
	
	static Random rand = new Random();
	/**
	 * permtte di muovere il dirigibile in background
	 */
	public static void moveBaloon()
	{
		
		if(renderingBaloon == baloonImageR)
			if(baloonx>GameWindow.windowWidth)
			{
				renderingBaloon = baloonImageL;
				baloony= rand.nextInt(maxBaloonY-minBaloonY)+minBaloonY;
			}
			else
				baloonx+=1;
		else
			if(baloonx<-baloonImageL.getWidth(null))
			{
				renderingBaloon = baloonImageR;
				baloony= rand.nextInt(maxBaloonY-minBaloonY)+minBaloonY;
			}
			else
				baloonx-=1;
		
		GameWindow.getMenuPanel().repaint();;
		//System.out.println(baloonx);
	}
}
