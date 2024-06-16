package model;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import controller.PlayerManager;

//elon mask
/**
 * La classe ClownMask rappresenta un nemico a forma di maschera clown nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class ClownMask extends Enemy{
	
	private ArrayList<Ball> myBalls = new ArrayList<Ball>();
	private final static ImageIcon idle = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/clownFace1.png")).getImage().getScaledInstance(110*4, 151*4, Image.SCALE_DEFAULT));
	//private final static ImageIcon idle = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/puropenDown.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
	
	/**
	 * Costruttore per la classe ClownMask.
     * 
     * @param x La coordinata x iniziale della posizione della maschera clown.
     * @param y La coordinata y iniziale della posizione della maschera clown.
	 */
	public ClownMask(int x, int y) {
		super(20000, 2, x, y, 0.075f, false, false, idle, true);
		// TODO Auto-generated constructor stub
	}
	
	private int playerX;
	private int playerY;
	
	double speed;
	/**
	 * definisce il comportamento di clown mask
	 */
	@Override
	public void behaviour() 
	{
		Iterator<Ball> balliterator = myBalls.iterator();
		while(balliterator.hasNext())
		{
			Ball ball = balliterator.next();
			ball.behave();
			if(ball.isDead)
				balliterator.remove();
		}
		
		playerX = PlayerManager.playerInstance.getX();
		playerY = PlayerManager.playerInstance.getY()-48;
		// Calculate the Direction Vector
		double directionX = playerX - this.x;
		double directionY = playerY - this.y;

		// Normalize the Direction Vector
		double length = Math.sqrt(directionX * directionX + directionY * directionY);
		if (length != 0) {  // Ensure length is not zero to avoid division by zero
		    directionX /= length;
		    directionY /= length;
		}
		speed = 0.075*GameManager.getDeltaTime();
		// Move the Enemy at the Desired Speed
		double speedX = directionX * speed;
		double speedY = directionY * speed;

		// Update the enemy's position
		this.x += speedX;
		this.y += speedY;
		
		
		notifyTheObserver(idle);
	}
	
	@Override
	public void hit()
	{
		
		
		System.out.println("hit");
		super.hp--;
		beenHit = true;
		new Timer().schedule(new TimerTask()
				{
					
					@Override
					public void run() {
						
						
						
						beenHit = false;
						if(hp<1)
						{
							die();
							GameManager.levelCleared();
						}
						else
						{
							spawnballs();
						}
					}
					
				}, 1000);
		
	}
	public void spawnballs()
	{
		myBalls.add(new Ball(this.x/64, this.y/64, 0)); 
		myBalls.add(new Ball(this.x/64, this.y/64, 1));
		myBalls.add(new Ball(this.x/64, this.y/64, 3));
		myBalls.add(new Ball(this.x/64, this.y/64, 4));
		myBalls.add(new Ball(this.x/64, this.y/64, 5));
		myBalls.add(new Ball(this.x/64, this.y/64, 6));
		myBalls.add(new Ball(this.x/64, this.y/64, 7));
	}
	
}
