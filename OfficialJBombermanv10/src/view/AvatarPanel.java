package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.GameManager;

/**
 * Classse per la gestione del Panel con gli AVATAR
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class AvatarPanel extends JPanel implements ActionListener
{
	private final static ImageIcon avatarWhite = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarWhite.png"));
	private final static ImageIcon avatarBlack = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarBlack.png"));
	private final static ImageIcon avatarBlue = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarBlue.png"));
	private final static ImageIcon avatarLightBlue = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarLightBlue.png"));
	private final static ImageIcon avatarPink = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarPink.png"));
	private final static ImageIcon avatarGreen = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarGreen.png"));
	private final static ImageIcon avatarYellow = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarYellow.png"));
	private final static ImageIcon avatarRed = new ImageIcon(ProfilePanel.class.getResource("/resources/avatarRed.png"));
	
	private final static ImageIcon[] avatars = new ImageIcon[]{avatarWhite, avatarBlack, avatarRed, avatarPink, avatarLightBlue, avatarGreen, avatarBlue, avatarYellow};
	
	
	
	JComboBox imageSelection;
	JLabel avatar;
	
	/**
	 * Costruttore, settaggi vari per la grafica della classe
	 */
	public AvatarPanel()
	{
		setOpaque(false);
		this.setBounds(0,75,218,296);
		avatar = new JLabel();
		avatar.setIcon(avatars[GameManager.getProfile(GameManager.getSelectedProfileNumber()).getAvatar()]);
		avatar.setBounds(0,0,218,216);
		add(avatar);
		
		String[] avatarNames = {"White", "Black", "Red", "Pink", "Acqua", "Green", "Blue", "Yellow"};
		imageSelection = new JComboBox(avatarNames);
		imageSelection.setSelectedIndex(GameManager.getProfile(GameManager.getSelectedProfileNumber()).getAvatar());
		imageSelection.addActionListener(this);
		imageSelection.setBounds(0, 218, 218, 30);
		
		this.add(imageSelection);
	}
	/**
	 * aggiorna l'avatar selezionato nel profilo
	 */
	public void updateSelected()
	{
		avatar.setIcon(avatars[GameManager.getProfile(GameManager.getSelectedProfileNumber()).getAvatar()]);
		imageSelection.setSelectedIndex(GameManager.getProfile(GameManager.getSelectedProfileNumber()).getAvatar());
	}

	/**
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==imageSelection)
		{
			GameManager.getProfile(GameManager.getSelectedProfileNumber()).setAvatar(imageSelection.getSelectedIndex());
			GameManager.updateProfileData(GameManager.getSelectedProfileNumber());
			avatar.setIcon(avatars[GameManager.getProfile(GameManager.getSelectedProfileNumber()).getAvatar()]);
			repaint();
		}
		
	}
}
