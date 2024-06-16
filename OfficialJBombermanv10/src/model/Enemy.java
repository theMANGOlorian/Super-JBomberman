package model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import controller.PlayerManager;
import view.EnemyView;
import view.GameWindow;

public abstract class Enemy extends Observable
{
	protected int x;
	protected int y;
	
	protected final int score;
	protected final int hits;
	protected int hp;
	protected final double speed;
	protected final boolean canWalkOnSoftBlocks;
	protected final boolean canWalkOnBombs;
	protected final EnemyView view;
	protected final boolean boss;
	
	public boolean isBoss() {return boss;}
	public int getX(){ return x;}
	public int getY(){ return y;}
	
	/**
	 * @param score I punti dati al giocatore dopo che muore
	 * @param hits 
	 * @param x la casella iniziale in cui sta il giocatore in x
	 * @param y la casella iniziale in cui sta il giocatore in x
	 * @param speed La velocità del giocatore MOLTIPLICATA PER DELTATIME (deltaTime = 16 a 60FPS), che saranno i pixel di movimento
	 * @param canWalkThroughSoftBlocks vero se il nemico può attraversare le scatole
	 * @param canWalkThroughBombs vero se il nemico può attraversare le bombe
	 * @param startImage l'icona che verrà messa sul JLabel del nemico inizialmente
	 * @param boss vero se il nemico è un boss
	 */
	public Enemy(int score, int hits, int x, int y, double speed, boolean canWalkOnSoftBlocks, boolean canWalkOnBombs, ImageIcon startImage, boolean boss)
	{
		this.boss = boss;
		this.score = score;
		this.hits = hits;
		hp = hits;
		this.x = x*64;
		this.y = y*64;
		this.speed = speed;
		this.canWalkOnBombs = canWalkOnBombs;
		this.canWalkOnSoftBlocks = canWalkOnSoftBlocks;
		
		view = new EnemyView(this, boss);
		view.setSize(startImage.getIconWidth(), startImage.getIconHeight());
		
		
		notifyTheObserver(startImage);
	}
	/**
	 *  Comportamento nemico ogni frame. Ricorda di chiamare la funzione notifyTheObserver per un cambiamento visivo
	 */
	abstract protected void behaviour();
	
	final public void behave()
	{
		if(beenHit)
			view.showHit();
		else 
		{
			
			behaviour();
			squareCollidesWithPlayer(this.x, this.y);
			
			if(!view.isVisible())
				view.setVisible(true);
		}
			
	}
	boolean beenHit = false;
	
	public void hit()
	{
		System.out.println("hit");
		hp--;
		beenHit = true;
		new Timer().schedule(new TimerTask()
				{

					@Override
					public void run() {
						beenHit = false;
						if(hp<1)
						{
							die();
						}
					}
					
				}, 1000);
		
	}
	protected void die()
	{
		GameManager.addScore(score);
		GameManager.killEnemy(this);
		GameWindow.removeLabelFromFrame(view);
	}
	
	/**
	 * Muove il nemico in una direzione basato su coordinate x,y dove se una ha un valore diverso da 0, l'altro deve avere come valore 0. Ritorna vero se il movimento ha avuto successo
	 * @param x numero con possibili valori [-1, 0, +1] che rappresenta la direzione sull'asse x in cui si vuole andare
	 * @param ynumero con possibili valori [-1, 0, +1] che rappresenta la direzione sull'asse y in cui si vuole andare
	 * @return true se è riuscito a muoversi
	 */
	final public boolean move(int x, int y)
	{
		int toMoveY = (int) (y*speed*GameManager.getDeltaTime());
		int toMoveX = (int) (x*speed*GameManager.getDeltaTime());
		int[] coords = calculateCollisions(toMoveX, toMoveY);
		
		if(Arrays.equals(coords, new int[] {0, 0}))
		{
			return false;
		}
		this.x = this.x + coords[0];
		this.y = this.y + coords[1];
		return true;
	}
	/**
	 * Returns true if a square made with the points [toMoveX,toMoveY] and [toMoveX+63,toMoveY+63] is in the hitbox of the player
	 * @param toMoveX
	 * @param toMoveY
	 * @return
	 */
	private boolean squareCollidesWithPlayer(int toMoveX, int toMoveY) 
	{
		if(PlayerManager.playerInstance.getX() >= toMoveX-63 && PlayerManager.playerInstance.getX() <= toMoveX+63 && 
			PlayerManager.playerInstance.getY()+48 >= toMoveY-63 && PlayerManager.playerInstance.getY()+48 <= toMoveY+63)
		{
			GameManager.Die();
			return true;
		}
		return false;
	}
	/**
	 * notifica gli osservatori
	 * @param immagine
	 */
	final public void notifyTheObserver(ImageIcon immagine)
	{
		ArrayList<Object> data = new ArrayList<>();
		data.add(this.x);
		data.add(this.y);
		data.add(immagine);
		setChanged();		
		notifyObservers(data);
	}
	
	/**
	 * calcola le collisione
	 * @param x
	 * @param y
	 * @return
	 */
	private int[] calculateCollisions(int x, int y)
	{
		int toMoveY = y;
		int toMoveX = x;
		
		y = toMoveY==0? 0 : toMoveY>0? 1 : -1;
		x = toMoveX==0? 0 : toMoveX>0? 1 : -1;
		
    	int[] out = {toMoveX, toMoveY};
    	
    	//Clipping personaggio: se si muove in una direzione, centralo nell'altra (se è abbastanza vicino)
    	if(y!=0)
    	{
    		if(this.x%64<20)
    			this.x -=this.x%64;
    		if(this.x%64>44)
    			this.x += 64-this.x%64;
    	}
    		
    	else
    	{
    		//si puo accorciare in:
    		//this.y = this.y%64<20? this.y-this.y%64 : this.y%64>44? this.y+64-this.y%64 : this.y;
    		if(this.y%64<20)
    			this.y -=this.y%64;
    		else if(this.y%64>44)
    			this.y += 64-this.y%64;
    	}
    	
    	//Logica funzione:
    	//Mettiamo due punti sul hitbox del personaggio in base alla direzione di movimento.
    	//Se il personaggio si muove, i due punti sono dentro delle caselle libere. 
    	//Se si allora applica il movimento, altrimenti no
    	
    	Point punto1 = new Point(this.x+toMoveX, this.y+toMoveY);
    	Point punto2 = new Point(this.x+toMoveX+63, this.y+toMoveY+63);
    	
    	if(y==-1)		//su
    		punto2.y-=63;
    	else if(y==1)	//giu
    		punto1.y+=63;
    	else if(x==-1)	//sinistra
    		punto2.x-=63;
    	else			//destra
    		punto1.x+=63;
    	
    	//Se in nessuno dei due punti cè un blocco o una bomba, ci puoi passare
		if((GameManager.getArena().getGriglia()[punto1.y/64][punto1.x/64]==0 && GameManager.getArena().getGriglia()[punto2.y/64][punto2.x/64]==0 
				&& (canWalkOnBombs || (!GameManager.bombOnTile(punto1.y/64, punto1.x/64) && !GameManager.bombOnTile(punto1.y/64, punto1.x/64))))
			|| (canWalkOnSoftBlocks && GameManager.getArena().getGriglia()[punto1.y/64][punto1.x/64]==2 && GameManager.getArena().getGriglia()[punto2.y/64][punto2.x/64]==2))
			return out;
		else
		{
			//return new int[]{0,0};
			
			//qui si dovrebbe cambiare la posizione nel caso cè un blocco, e di spostare il personaggio ma solo fino a toccarlo
			if(x ==-1)
			{
				out[0] = ((this.x/64)*64)-this.x;
			}
			else if(x == 1)
			{
				if(this.x%64!=0)
					out[0] = (((this.x/64)+1)*64)-this.x;
				else
					out[0] = 0;
			}
			else if (y == -1)
			{
				out[1] = ((this.y/64)*64)-this.y;
			}
			else
			{
				if(this.y%64!=0)
					out[1] = (((this.y/64)+1)*64)-this.y;
				else
					out[1] = 0;
			}
		}
    	return out;
	}
		
}
