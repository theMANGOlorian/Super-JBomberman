package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameManager;

/**
 * metodo per gestione della topbar dove mostra le vite rimastre, punteggio
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class GUI extends JPanel implements Observer
{
	int Lives = 5;	//just the gui number shown
	int Score = 0;
	
	ImageIcon background = new ImageIcon(getClass().getResource("/resources/GUI.png"));
	JLabel backgroundLabel = new JLabel();
	JLabel livesLabel = new JLabel();
	JLabel scoreLabel = new JLabel();
	
	/**
	 * costruttore, settaggi vari per rendere la gui visibile in un certo modo
	 */
	public GUI()
	{
		GameManager.getGameManager().addObserver(this);
		backgroundLabel.setIcon(new ImageIcon(background.getImage().getScaledInstance(256*4, 32*4, Image.SCALE_DEFAULT)));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(256*4,32*4));
		
		livesLabel.setBounds(98, 37, 50, 50);
		livesLabel.setText(Lives+"");
		livesLabel.setForeground(Color.white);
		livesLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 50));
		
		scoreLabel.setBounds(192, 37, 256, 50);
		scoreLabel.setText(Score+"");
		scoreLabel.setForeground(Color.white);
		scoreLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 50));
		scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		this.add(scoreLabel);
		this.add(livesLabel);
		this.add(backgroundLabel);
	}
	/**
	 * metodo update per aggiornare la gui in caso di cambiamento della vita o dello score
	 */
	@Override
	public void update(Observable o, Object HpAndScore)
	{
		int[] toUpdate;
		try
		{
			toUpdate = (int[]) HpAndScore;
			
		}
		catch(ClassCastException exception)
		{
			System.out.println("/!\\ The inserted value to update GUI is not of type int[]. ignoring update... /!\\");
			return;
		}
		if(toUpdate.length>2)
			System.out.println("/!\\ More than 2 values inserted to update GUI. Ignoring values at indexes 2+.../!\\");
		
		Lives=toUpdate[0];
		String livesText = Lives<0 ? "X" : Lives+"";
		livesLabel.setText(livesText);
		
		Score=toUpdate[1];
		scoreLabel.setText(Score+"");
		
		repaint();
	}
}
