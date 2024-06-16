package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.GameManager;

/**
 * Classe per il pannello del profilo
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ProfilePanel extends JPanel
{	
	RenamePanel renamePanel;
	AvatarPanel avatarPanel;
	ScorePanel scoreboard;
	/**
	 * costruttore della classe, settaggi vari per la grafica
	 */
	public ProfilePanel()
	{
		setLayout(null);
		setOpaque(false);
		setBounds(GameWindow.windowWidth/4, GameWindow.windowHeight/4, GameWindow.windowWidth/2, GameWindow.windowHeight/2);
		
		PulsanteMenu pulsanteBack = new PulsanteMenu("Select",2,"closeProfilePicker",MenuPanel.class);
		pulsanteBack.setVisible(true);
		pulsanteBack.setEnabled(true);
		pulsanteBack.setLocation(getWidth()/2-pulsanteBack.getWidth()/2,getHeight()-pulsanteBack.getHeight());
		add(pulsanteBack);
		
		
		try {
			GameManager.loadProfileData();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to read Profile data!");
			MenuPanel.closeProfilePicker();
		}
		
		renamePanel = new RenamePanel();
		add(renamePanel);
		avatarPanel = new AvatarPanel();
		add(avatarPanel);
		
		add(new ProfileSelectionPanel());
		
		scoreboard = new ScorePanel();
		add(scoreboard);
	}
	public void updateSelection()
	{
		renamePanel.updateSelection();
		avatarPanel.updateSelected();
		scoreboard.updateSelection();
	}
	

	/**
	 * disegna la parte grafica
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		g2d.setColor(new Color(0.77f, 0.9f, 0.9f, 0.9f));
		g2d.fillRect(0,0,getWidth(), getHeight());
		g2d.setStroke(new BasicStroke(8));
		g2d.setColor(new Color(0.77f/2, 0.9f/2, 0.9f/2, 0.9f));
		g2d.drawRect(0, 0,getWidth(), getHeight());
		//g2d.drawRect(GameWindow.windowWidth/4, GameWindow.windowHeight/4, GameWindow.windowWidth/2, GameWindow.windowHeight/2);
		super.paint(g);
	}
}
