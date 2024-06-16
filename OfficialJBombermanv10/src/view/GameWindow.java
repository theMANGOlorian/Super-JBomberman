package view;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import controller.PlayerManager;
import controller.MouseController;
import controller.MusicAudio;
import controller.PlayerController;
import model.Arena;
import model.GameManager;
import model.Player;

/**
 * Classe per la gestione della parte grafica del JFrame
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class GameWindow extends JFrame{
	private static GameWindow window;
	private JFrame frame;
	private static MenuPanel menuPanel;
	private JPanel gamePanel;
	private static ArenaView arenaView;
	public static enum Scene{mainMenu, normalGame}
	public final static int windowWidth = 1024, windowHeight = 1000; //height excludes top window bar, which is usually 40px
	
	private GUI gui;
	public GUI getGui() { return gui; }
	
	public static MenuPanel getMenuPanel() { return menuPanel;}
	
	/**
	 * Costruttore della classe con settaggi vari del Jframe
	 */
	public GameWindow() {
		window = this;
		frame = this;	//Create Window itself
		
		frame.setBackground(Color.black);
		frame.setTitle("Super (J)Bomberman!");
		frame.setIconImage(new ImageIcon(getClass().getResource("/resources/bomb2.png")).getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(960-windowWidth/2,0, windowWidth,windowHeight); //grandezza del frame
		frame.setResizable(false); // rendere il JFrame non regolabile
		
		frame.addKeyListener(PlayerController.getKey());
		frame.setVisible(true);
		
	}
	/**
	 * metodo che mostra il pannello di vittoria o sconfitta con lo score
	 * @param victory
	 */
	public void gameOverWindow(boolean victory) {
	    JLayeredPane layeredPane = frame.getLayeredPane();
	    
	    JPanel overlay = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            Graphics2D g2d = (Graphics2D) g;
	            //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 0.5f for 50% transparency

	            g2d.setColor(new Color(0,0,0,0.5f));
	            g2d.fillRect(0, 0, getWidth(), getHeight());
	        }
	    };
	    overlay.setLayout(null);
	    overlay.setOpaque(false);

	    layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);
	    overlay.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());

	    JLabel text = new JLabel();
	    text.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
	    if(victory)
	    {
	    	text.setText("You win!");
	    	text.setForeground(Color.GREEN);
	    }
	    else 
	    {
	    	text.setText("You lose");
		    text.setForeground(Color.RED);
	    }
	    
	    
	    // Calculate the position to center the label
	    int labelX = (overlay.getWidth() - text.getPreferredSize().width) / 2;
	    int labelY = (overlay.getHeight() - text.getPreferredSize().height) / 2;

	    text.setBounds(labelX, labelY, text.getPreferredSize().width, text.getPreferredSize().height);
	    
	    JLabel score = new JLabel();
	    score.setText("Score: "+GameManager.getScore());
	    score.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
	    score.setForeground(Color.WHITE);
	    score.setBounds((overlay.getWidth() - score.getPreferredSize().width) / 2, labelY+text.getHeight(), score.getPreferredSize().width, score.getPreferredSize().height);

	    overlay.add(text);
	    overlay.add(score);
	    PulsanteMenu quitButton = new PulsanteMenu("Quit",2,"closeGame",GameManager.class);
	    quitButton.setVisible(true);
	    quitButton.setEnabled(true);
	    overlay.add(quitButton);
	    
	    overlay.setVisible(true);

	    layeredPane.revalidate();
	    layeredPane.repaint();
	}
	
	/**
	 * metodo per il settaggio della scena del gioco
	 * @param scene, scena da impostare
	 */
	public void setScene(Scene scene)
	{
		//clear previous scene
		frame.getContentPane().removeAll();
		frame.repaint();
		
		switch(scene)
		{
		case mainMenu:
			makeMenu();
			break;
		case normalGame:
			makeGame();
			break;
		}
		frame.setVisible(true);
		
	}
	
	/**
	 * metodo per "costruire" il menù, creando istanze PulsanteMenu e aggiungendo musica
	 */
	public void makeMenu()
	{
		if(menuPanel==null)
		{
			menuPanel = new MenuPanel();
			
			PulsanteMenu pulsanteGioca = new PulsanteMenu("New Game", 1, "newGame", GameManager.class);
			menuPanel.add(pulsanteGioca);
			
			PulsanteMenu pulsanteCarica = new PulsanteMenu("User", 2, "openProfilePicker", MenuPanel.class);
			menuPanel.add(pulsanteCarica);
			
			PulsanteMenu pulsanteEsci = new PulsanteMenu("Exit", 3, "closeGame", GameManager.class);
			menuPanel.add(pulsanteEsci);
			
			PulsanteMenu pulsanteMouse = new PulsanteMenu("Gioca con il mouse", 4, "changePlayWithMouse", GameManager.class)
					{
				@Override
				public void mouseClicked(MouseEvent e) {
					if(this.isEnabled())
					{
						try {
							func.invoke(null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
							System.out.println("La funzione dichiarata ha dei parametri errati. Assicurati che la funzione non ne richieda alcuni e che esso sia statica");
							e1.printStackTrace();
						}
						this.setText("Gioca con " + (GameManager.isPlayingWithMouse()? " la tastiera" : " il mouse"));
					}
					
				}
					};
			pulsanteMouse.setLocation(-65,(GameWindow.windowHeight/2-pulsanteMouse.getHeight()/2)+425);
			menuPanel.add(pulsanteMouse);
			
		}
		
		
		frame.add(menuPanel);
		frame.setVisible(true);
		
		MusicAudio.play("/resources/menu.wav");
		
		menuPanel.animateIntro();
		
	}
	
	/**
	 * metodo per creare la partita, istanzia la parte grafica dell'arena e settaggi vari come layout,backgroud etc..
	 */
	public void makeGame()
	{
		gamePanel = new JPanel();
		frame.requestFocus();
		
		
		gamePanel.setLayout(new BorderLayout());
		gamePanel.setBackground(Color.black);
		gamePanel.setBounds(0,0,1024,960);
		
		gamePanel.addMouseListener(MouseController.getMouseControls(this));
		
		gui = new GUI();
		gui.setVisible(true);
		gui.setBackground(Color.black);
		
		frame.add(gui, BorderLayout.NORTH);
		
		//Empty black panel to shift the arena slightly to be centered
		JPanel blackPanel = new JPanel();
		blackPanel.setPreferredSize(new Dimension(32,1));
		blackPanel.setBackground(Color.black);
		frame.add(blackPanel, BorderLayout.WEST);
		
		//arena
		arenaView = new ArenaView(GameManager.getArena());
		gamePanel.add(GameManager.getPlayerView());
		gamePanel.add(arenaView, BorderLayout.CENTER);
		
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	/**
	 * metodo per resettare l'arena
	 */
	public void resetArena()
	{
		gamePanel.remove(arenaView);
		arenaView = new ArenaView(GameManager.getArena());
		arenaView.repaint();
		//gamePanel.add(GameManager.getPlayerView());
		gamePanel.add(arenaView, BorderLayout.CENTER);
		gamePanel.repaint();
		frame.setVisible(true);
	}
	/**
	 * metodo che aggiunge JLabel al frame
	 * @param x, coordinata x del Jlabel
	 * @param y, coordinata y del Jlabel
	 * @param width, larghezza
	 * @param height, altezza
	 * @param imageName, stringa contenente il nome dell'immagine
	 * @return ritorna il label da aggiugere al frame
	 */
	public JLabel addToFrame(int x, int y, int width, int height, String imageName)
	{
		JLabel label = new JLabel();
		label.setBounds(x,y,width,height);
		
		
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/"+imageName));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        
        label.setIcon(new ImageIcon(scaledImage));
        
        
        arenaView.add(label);
		arenaView.repaint();
		

		return label;
	}

	public static JLabel addToFrame(JLabel label)
	{
        arenaView.add(label);
		arenaView.repaint();
		

		return label;
	}
	
	
	/**
	 * disegna Icon nel game panel
	 * @param image, immagine di tipo ImageIcon
	 * @param g
	 * @param x, coordinata x
	 * @param y, coordinata y
	 */
	public void paintIcon(ImageIcon image, Graphics g, int x, int y)
	{
		image.paintIcon(gamePanel, g, x*64, y*64);
	}
	
	/**
	 * metodo per rimuovere il Jlabel dal frame
	 * @param label, JLabel da rimuovere
	 */
	public static void removeLabelFromFrame(JLabel label)
	{
		arenaView.remove(label);
		arenaView.repaint();
	}
	
	/**
	 * metodo che aggiunge il fuoco delle bombe sulla finestra, verrà rimossa dopo (circa) 1 secondo
	 * @param finestra, la finestra del gioco su cui va messo il fuoco
	 * @param x, coordinata x
	 * @param y, coordinata y
	 * @param direction, in base alla direzione stabilisce quale file prendere (se il fuoco "a destra" o "a sinistra" etc..)
	 */
	public void addExplosion(GameWindow finestra ,int x, int y, String direction)
	{
			
		JLabel fire = finestra.addToFrame(x*64,y*64, 64, 64, direction);
		
		new Timer().schedule(new TimerTask() {
			  @Override
			  public void run() {
				  	finestra.removeLabelFromFrame(fire);					
			  }
			}, 900);
		
	}
	
}

