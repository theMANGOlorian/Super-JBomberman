package model;

import java.awt.Image;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import controller.PlayerManager;

/**
 * Classe del boss bigaron
 * @author Mattia Pandolfi & Benjamin Ruff
 *
 */
public class Bigaron extends Enemy
{
	private final static ImageIcon idle = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/bigaron1.png")).getImage().getScaledInstance(110*4, 151*4, Image.SCALE_DEFAULT));
	private final static ImageIcon atk1 = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/bigaron2.png")).getImage().getScaledInstance(110*4, 151*4, Image.SCALE_DEFAULT));
	private final static ImageIcon atk2 = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/bigaron3.png")).getImage().getScaledInstance(110*4, 151*4, Image.SCALE_DEFAULT));
	private final static ImageIcon atk3 = new ImageIcon( new ImageIcon(Puropen.class.getResource("/resources/bigaron4.png")).getImage().getScaledInstance(110*4, 151*4, Image.SCALE_DEFAULT));
	
	private long timeAtLastAttack;
	private final int minTimeBetweenAttacks = 2000;
	private final int chanceOfAttack = 100; // every frame there is a 1/chanceOfAttack chance Bigaron will choose to stop and attack with his hammer
	private final int chanceToSwitchAxis = 10; //every intersection there is a 1/chanceToSwitchAxis chance Bigaron will change movement axis
	
	/**
	 * Costruttore per la classe Bigaron.
     * 
     * @param x La coordinata x iniziale della posizione di Bigaron.
     * @param y La coordinata y iniziale della posizione di Bigaron.
	 */
	public Bigaron(int x, int y) {
		super(10000, 5, x, y, 0.15f, false, false, idle, true);
		timeAtLastAttack = System.currentTimeMillis();
	}

	private int moveX = 0; // Start by moving up
	private int moveY = 1;
	private boolean previouslyMoved = true;
	private ImageIcon currentImage = idle;

	int playerX;
	int playerY; 
	private boolean attacking = false;
	
	Random randomizer = new Random();
	/**
	 * Definisce il comportamento di Bigaron.
	 */
	@Override
	public void behaviour() 
	{
		if(attacking)
		{
			return;
		}
		
		//Movement pattern
		//playerX = PlayerManager.playerInstance.getX();
		//playerY = PlayerManager.playerInstance.getY();
		System.out.println(this.x + " " + this.y);
		//if((((this.x+64)%128>=0 && (this.x+64)%128<10) || ((this.x+64)%128>=118 && (this.x+64)%128<128))  && (((this.y+64)%128>=0 && (this.y+64)%128<10) || ((this.y+64)%128>=118 && (this.y+64)%128<128)))
		if((this.x+64)%128==0 && (this.y+64)%128==0)
		{
			System.out.println("on intersection");
			if(randomizer.nextInt(chanceToSwitchAxis)==0)
			{
				if(moveX!=0)
				{
					moveY = moveX;
					moveX = 0;
				}
				else
				{
					moveX = moveY;
					moveY = 0;
				}
				if(!move(moveX,moveY))
				{
					moveX = -moveX;
					moveY = -moveY;
				}
			}
		}
		else
		{
			
			boolean moved = move(moveX,moveY);
			if(moveX!=0)
			{
				if(!moved)
				{
					moveX = -moveX;
				}
			}
			else
			{
				if(this.y >= 512 || !moved || this.y<= 128)
				{
					moveY = -moveY;
				}
			}
		}
		notifyTheObserver(idle);
		
		if(System.currentTimeMillis() - timeAtLastAttack > minTimeBetweenAttacks)
		{
			if(randomizer.nextInt(chanceOfAttack) == 0)
			{
				//Attack with hammer
				attacking = true;
				
				
				notifyTheObserver(atk1);
				int hammerX = this.x-64;
				int hammerY = this.y-64+ 192;
				
				new Timer().schedule(new TimerTask()
				{

					@Override
					public void run() {
						notifyTheObserver(atk2);
						new Timer().schedule(new TimerTask()
						{

							@Override
							public void run() {
								
								notifyTheObserver(atk3);
								playerX = PlayerManager.playerInstance.getX();
								playerY = PlayerManager.playerInstance.getY();
								
								if((playerX >= hammerX-63 && playerX <= hammerX+191) && (playerY+48 >= hammerX-63 && playerY+48 <= hammerX+191))
								{
									GameManager.Die();
								}
								
								new Timer().schedule(new TimerTask()
								{

									@Override
									public void run() {
										notifyTheObserver(idle);
										attacking = false;
									}
							
								}, 500);
							}
					
						}, 500);
					}
			
				}, 500);
				
				//Reset timer for next attack
				timeAtLastAttack = System.currentTimeMillis();
			}
				
		}
		
		
	}

}
