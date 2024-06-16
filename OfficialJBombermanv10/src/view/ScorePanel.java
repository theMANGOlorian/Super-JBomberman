package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GameManager;

/**
 * Questa classe rappresenta un pannello per la visualizzazione dei punteggi e il reset del progresso del profilo.
 * Estende la classe JPanel e implementa l'interfaccia ActionListener.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ScorePanel extends JPanel implements ActionListener
{
	JLabel lastScore;
	JLabel totalScore;
	JLabel gamesPlayed;
	JLabel gamesWon;
	JLabel gamesLost;
	
	JButton resetProgress;
	
	/**
	 * Costruttore per il pannello dei punteggi.
	 */
	public ScorePanel()
	{
		setOpaque(false);
		setLayout(null);
		setBounds(228, 100, GameWindow.windowWidth/2-238, 200);
		lastScore = new JLabel();
		totalScore = new JLabel();
		gamesPlayed = new JLabel();
		gamesWon = new JLabel();
		gamesLost = new JLabel();
		
		lastScore.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		totalScore.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		gamesPlayed.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		gamesWon.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		gamesLost.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		
		lastScore.setBounds(0, 0, 290, 25);
		totalScore.setBounds(0, 25, 290, 25);
		gamesPlayed.setBounds(0, 50, 290, 25);
		gamesWon.setBounds(0, 75, 290, 25);
		gamesLost.setBounds(0, 100, 290, 25);
		
		updateSelection();
		
		add(lastScore);
		add(totalScore);
		add(gamesPlayed);
		add(gamesWon);
		add(gamesLost);
		
		
		resetProgress = new JButton("Reset Progress");	
		resetProgress.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		resetProgress.setBackground(new Color(196/256f, 88/256f, 84/256f));
		resetProgress.setBounds(0, 150, 200, 40);
		
		add(resetProgress);
		resetProgress.addActionListener(this);
	}
	
	/**
	 * Aggiorna la visualizzazione dei punteggi.
	 */
	public void updateSelection() 
	{
		lastScore.setText  (" Last score: "+GameManager.getProfile(GameManager.getSelectedProfileNumber()).getLastScore());
		totalScore.setText ("Total Score: "+GameManager.getProfile(GameManager.getSelectedProfileNumber()).getTotalScore());
		gamesPlayed.setText("      Games: "+ (GameManager.getProfile(GameManager.getSelectedProfileNumber()).getGamesWon() + GameManager.getProfile(GameManager.getSelectedProfileNumber()).getGamesLost()));
		gamesWon.setText   ("        Won: "+GameManager.getProfile(GameManager.getSelectedProfileNumber()).getGamesWon());
		gamesLost.setText  ("       Lost: "+GameManager.getProfile(GameManager.getSelectedProfileNumber()).getGamesLost());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == resetProgress)
		{
			int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all progress of this profille?\n(This action is irreversible...)", "Confirm progress reset", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if(answer==0)
			{
				GameManager.getProfile(GameManager.getSelectedProfileNumber()).resetProfile(GameManager.getSelectedProfileNumber());
				GameManager.updateProfileData(GameManager.getSelectedProfileNumber());
				MenuPanel.updateProfilePicker();
			}
		}
	}

}
