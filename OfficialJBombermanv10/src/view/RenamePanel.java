package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.GameManager;

/**
 * Questa classe rappresenta un pannello per rinominare un profilo.
 * Estende la classe JPanel e implementa l'interfaccia ActionListener.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class RenamePanel extends JPanel implements ActionListener
{
	JTextField insertName;
	JButton renameButton;
	/**
	 * Costruttore per il pannello di rinomina.
	 */
	public RenamePanel()
	{
		setOpaque(false);
		setLayout(null);
		insertName = new JTextField();
		insertName.setText(GameManager.getProfile(GameManager.getSelectedProfileNumber()).getName());
		insertName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		this.setBounds(30,340,190,90);
		insertName.setBounds(0,0,190,40);
		
		
		renameButton = new JButton("Rename");
		renameButton.setBounds(0,50,190,40);
		renameButton.addActionListener(this);
		
		add(insertName);
		add(renameButton);
		setVisible(true);
	}
	/**
	 * Aggiorna la selezione nel pannello.
	 */
	public void updateSelection()
	{
		insertName.setText(GameManager.getProfile(GameManager.getSelectedProfileNumber()).getName());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==renameButton) {
			if(insertName.getText().length()>9)
				insertName.setText(insertName.getText().substring(0, 10));
			GameManager.getProfile(1).setName(insertName.getText());
			GameManager.updateProfileData(1);
		}
	}

}
