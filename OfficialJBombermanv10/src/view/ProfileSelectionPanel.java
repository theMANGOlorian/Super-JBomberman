package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.GameManager;

/**
 * Classe per la grafica della selezione dei profili nel panello
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ProfileSelectionPanel extends JPanel implements ActionListener
{
	JRadioButton profile1;
	JRadioButton profile2;
	JRadioButton profile3;
	
	/**
	 * metodo costruttore, aggiunta dei JRadioButton e gruppi di profili
	 */
	public ProfileSelectionPanel()
	{
		setBounds(0,0,GameWindow.windowWidth/2, 30);
		profile1 = new JRadioButton("profile 1");
		profile2 = new JRadioButton("profile 2");
		profile3 = new JRadioButton("profile 3");
		
		profile1.addActionListener(this);
		profile2.addActionListener(this);
		profile3.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		group.add(profile1);
		group.add(profile2);
		group.add(profile3);
		
		this.add(profile1);
		this.add(profile2);
		this.add(profile3);
	}
	
	/**
	 *selezione del profilo
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==profile1)
		{
			System.out.println("selecting profile 1");
			GameManager.selectProfile(1);
			MenuPanel.updateProfilePicker();
		}
		else if(e.getSource()==profile2)
		{
			System.out.println("selecting profile 2");
			GameManager.selectProfile(2);
			MenuPanel.updateProfilePicker();
		}
		else if(e.getSource()==profile3)
		{
			System.out.println("selecting profile 3");
			GameManager.selectProfile(3);
			MenuPanel.updateProfilePicker();
		}
	}

}
