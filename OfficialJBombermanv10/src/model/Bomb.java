package model;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

/**
 * Rappresenta una bomba nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Bomb 
{
	public JLabel label;
	private int x, y;
	private float fuse = 3;	//seconds it takes for bomb to go boom
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	Bomb This = this;
	Timer timer = new Timer();
	
	/**
	 * Crea una nuova bomba con le coordinate specificate.
     *
     * @param x La coordinata x della bomba.
     * @param y La coordinata y della bomba.
	 */
	public Bomb(int x, int y)
	{
		this.x = x;
		this.y = y;
		startTimer();
	}
	/**
	 * Crea una nuova bomba con le coordinate e il tempo di accensione specificati.
     *
     * @param x     La coordinata x della bomba.
     * @param y     La coordinata y della bomba.
     * @param fuse  Il tempo di accensione in secondi.
	 */
	public Bomb(int x, int y, float fuse)
	{
		this.x = x;
		this.y = y;
		this.fuse = fuse;
		startTimer();
	}
	void startTimer()
	{
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  GameManager.explodeBomb(x,y, This);
			  }
			}, (long) (fuse*1000));
		
	}
}
