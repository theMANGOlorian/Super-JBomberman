package model;

import java.awt.*;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

import controller.MusicAudio;
import controller.PlayerController;
import controller.PlayerManager;
import controller.SoundEffect;


/**
 * Rappresenta il personaggio giocatore nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi 
 *
 */
public class Player extends Observable{
	long timeAtLastMovement = 0;
    private int x; // posizione x del giocatore
    private int y; // posizione y del giocatore
    private final double baseSpeed = 0.256;	//numero di pixel da muoversi ogni millisecondo
    private float speedMultiplier = 1;
    private int nOfBombs = 1;
    private int bombSize = 1; //per quante caselle si estende l'esplosione (0-10)
    boolean isDying = false; //stay alive
    private static int lives = 5;
    private static boolean invincible = false;
    
    private int spriteAnimationNumber = 2;
    private boolean crescente = true;
    
    private long lastAnimationTime = System.currentTimeMillis();
    private long delayAnimation = 200;
    private boolean walkOnBombs = false;
    
    //sprites animazione movimento
    private Image 
    up1 = ImageLoader("/resources/up1.png"),
    up2 = ImageLoader("/resources/up2.png"),
    up3 = ImageLoader("/resources/up3.png"),
    down1 = ImageLoader("/resources/down1.png"),
    down2 = ImageLoader("/resources/down2.png"),
    down3 = ImageLoader("/resources/down3.png"),
    left1 = ImageLoader("/resources/left1.png"),
    left2 = ImageLoader("/resources/left2.png"),
    left3 = ImageLoader("/resources/left3.png"),
    right1 = ImageLoader("/resources/right1.png"),
    right2 = ImageLoader("/resources/right2.png"),
    right3 = ImageLoader("/resources/right3.png");
    
    SoundEffect walking = new SoundEffect();
    SoundEffect death = new SoundEffect();
    
    public boolean canWalkOnBombs() {return walkOnBombs;}
    public void makeBombsWalkable() {walkOnBombs = true;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getBombSize() {return bombSize;}
    public boolean isInvincible() {return invincible;}
    public int getLives() {	return lives; }
    
    public void removeALife() { lives--; }
    public void addALife() { lives = lives<9? lives+1 : 9; }
    public void expandBombSize(boolean maximum) { bombSize = (maximum || bombSize>10)? 10 : bombSize+1; }
    
    /**
     * Costruttore del giocatore.
     */
    public Player() {
        // Costruttore del giocatore
        x = 64;
        y = 20;
        
    }
    
    /**
     * Carica un'immagine da file ed esegue il ridimensionamento.
     *
     * @param imagePath Il percorso dell'immagine da caricare.
     * @return L'immagine ridimensionata.
     */
    public Image ImageLoader(String imagePath) {
    	Image sprite = null;
    	try {
			sprite = ImageIO.read(getClass().getResourceAsStream(imagePath));
			int newWidth = sprite.getWidth(null) * 4; // Esempio: raddoppia la larghezza
	        int newHeight = sprite.getHeight(null) * 4; // Esempio: raddoppia l'altezza

	        // Scala l'immagine alla nuova dimensione
	        sprite = sprite.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);	        
        } catch (IOException e) {
			e.printStackTrace();
		}
    	return sprite;
    }


    /**
     * Aumenta la velocità del giocatore.
     */
    public void raiseSpeed()
    {
    	speedMultiplier+=0.25f;
    	delayAnimation= (long) (delayAnimation*0.75);
    }
    
    /**
     * Rende il giocatore invincibile per un periodo di tempo specificato in millisecondi.
     *
     * @param milliseconds La durata dell'invincibilità in millisecondi.
     */
    public void setInvincible(int milliseconds)
    {
    	invincible = true;
    	System.out.println("You are now invincible");
    	new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // Questo codice verrà eseguito dopo 2 secondi
            	invincible = false;
            	System.out.println("You are no longer invincible");
                
            }
        }, milliseconds);
    }
    
    /**
     * Rende il giocatore invincibile indefinitamente.
     */
    public void setInvincible()
    {
    	invincible = true;
    	System.out.println("You are now invincible indefinitely");
    }
    
    /**
     * Calcola le collisioni del giocatore con il terreno e le eventuali regole di movimento.
     *
     * @param y       Movimento verticale (-1 su, 1 giù, 0 nessuno)
     * @param x       Movimento orizzontale (-1 sinistra, 1 destra, 0 nessuno)
     * @param toMoveY Quantità da muovere verticalmente
     * @param toMoveX Quantità da muovere orizzontalmente
     * @return Un array contenente le quantità aggiornate da muovere verticalmente e orizzontalmente dopo le collisioni.
     */
    private int[] calculateCollisions(int y, int x, int toMoveY, int toMoveX)
    {
    	int normalizedy = this.y+48;
    	
    	int[] out = {toMoveY, toMoveX};
    	
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
    		//this.y = normalizedy%64<20? this.y-normalizedy%64 : normalizedy%64>44? this.y+64-normalizedy%64 : this.y;
    		if(normalizedy%64<20)
    			this.y -=normalizedy%64;
    		else if(normalizedy%64>44)
    			this.y += 64-normalizedy%64;
    	}
    	
    	//Logica funzione:
    	//Mettiamo due punti sul hitbox del personaggio in base alla direzione di movimento.
    	//Se il personaggio si muove, i due punti sono dentro delle caselle libere. 
    	//Se si allora applica il movimento, altrimenti no
    	
    	Point punto1 = new Point(this.x+toMoveX, this.y+48+toMoveY);
    	Point punto2 = new Point(this.x+toMoveX+63, this.y+48+toMoveY+63);
    	
    	if(y==-1)		//su
    		punto2.y-=63;
    	else if(y==1)	//giu
    		punto1.y+=63;
    	else if(x==-1)	//sinistra
    		punto2.x-=63;
    	else			//destra
    		punto1.x+=63;
    	
    	
    	
    	//Se in nessuno dei due punti cè un blocco o una bomba, ci puoi passare
		if(GameManager.getArena().getGriglia()[punto1.y/64][punto1.x/64]==0 && GameManager.getArena().getGriglia()[punto2.y/64][punto2.x/64]==0 && (canWalkOnBombs() || (!GameManager.bombOnTile(punto1.y/64, punto1.x/64) && !GameManager.bombOnTile(punto1.y/64, punto1.x/64))))
			return out;
		else
		{
			//return new int[]{0,0};
			
			//qui si dovrebbe cambiare la posizione nel caso cè un blocco, e di spostare il personaggio ma solo fino a toccarlo
			if(x ==-1)
			{
				out[1] = ((this.x/64)*64)-this.x;
			}
			else if(x == 1)
			{
				if(this.x%64!=0)
					out[1] = (((this.x/64)+1)*64)-this.x;
				else
					out[1] = 0;
			}
			else if (y == -1)
			{
				out[0] = ((normalizedy/64)*64)-normalizedy;
			}
			else
			{
				if(normalizedy%64!=0)
					out[0] = (((normalizedy/64)+1)*64)-normalizedy;
				else
					out[0] = 0;
			}
		}
    	return out;
    }
    
    static int prev_up = 0;
    static int prev_right = 0;
    /**
     * Elabora i comandi di movimento.
     *
     * @param y Movimento verticale (-1 su, 1 giù, 0 nessuno)
     * @param x Movimento orizzontale (-1 sinistra, 1 destra, 0 nessuno)
     * @return Un array contenente il movimento verticale e orizzontale dopo l'elaborazione.
     */
    public int[] processDirections(int y, int x)
    {
    	int[] out = new int[2];
    	if(y!=0 && x!=0)
		{
			if(prev_up!=y)
				x=0;
			else if(prev_right!=x)
				y=0;
		}
		else
		{
			prev_up = y;
			prev_right = x;
		}
    	out[0] = y;
    	out[1] = x;
    	return out;
    }

    
/**
 * Aggiorna le coordinate del giocatore e gestisce collisioni, regole di movimento e lo sprite per l'aniamzione di movimento.
 *
 * @param y Movimento verticale (-1 su, 1 giù, 0 nessuno)
 * @param x Movimento orizzontale (-1 sinistra, 1 destra, 0 nessuno)
 */
public void newCoordinate(int y, int x){
    	
    	
    	Image image = down1;
    	switch(y+""+x)
    	{
    		case "-10":
                if(spriteAnimationNumber == 1)
                    image = up1;
                if(spriteAnimationNumber == 2)
                    image = up2;
                if(spriteAnimationNumber == 3)
                    image = up3;
                break;
                
            case "10":
                if(spriteAnimationNumber == 1)
                    image = down1;
                if(spriteAnimationNumber == 2)
                    image = down2;
                if(spriteAnimationNumber == 3)
                    image = down3;
                break;
            
            case "01":
                if(spriteAnimationNumber == 1)
                    image = right1;
                if(spriteAnimationNumber == 2)
                    image = right2;
                if(spriteAnimationNumber == 3)
                    image = right3;
                break;
            
            case "0-1":
                if(spriteAnimationNumber == 1)
                    image = left1;
                if(spriteAnimationNumber == 2)
                    image = left2;
                if(spriteAnimationNumber == 3)
                    image = left3;
                break;
    			
    		default:
    			break;
    	}
    	
    	int toMoveY = (int)(baseSpeed*speedMultiplier*y*GameManager.getDeltaTime());
		int toMoveX = (int)(baseSpeed*speedMultiplier*x*GameManager.getDeltaTime());
		
		
		int[] newCoords = calculateCollisions(y,x,toMoveY, toMoveX);
		this.y += newCoords[0];
		this.x += newCoords[1];
    	
    	
    	
    	ArrayList<Object> data = new ArrayList<>();
    	data.add(this.x);
    	data.add(this.y);
    	data.add(image);
    	
    	

        //animation player
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastAnimationTime >= delayAnimation) {
            lastAnimationTime = currentTime;
            
            walking.play("/resources/Walking.wav");
            
            if (crescente) {
                spriteAnimationNumber++;
                if (spriteAnimationNumber > 3) {
                    crescente = false;
                    spriteAnimationNumber = 2;
                }
            } else {
                spriteAnimationNumber--;
                if (spriteAnimationNumber < 1) {
                    crescente = true;
                    spriteAnimationNumber = 2;
                }
            }
        }
        
    	notifyO(data);
    	onPowerUp();
    	onPortal();
    }

	/**
	 * Gestisce l'evento di attraversamento del portale.
	 */
	private void onPortal()
	{		
		if(GameManager.getArena().getCella((this.x+32)/64, (this.y+80)/64) == 3 && GameManager.enemiesLeft() == 0)
		{
			
			ArrayList<Object> data = new ArrayList<>();
			this.x = ((this.x+32)/64)*64;
			this.y = ((this.y+80)/64)*64-48;
			System.out.println(this.x + " " + this.y);
			data.add(this.x);
            data.add(this.y);
            data.add(down2);
            notifyO(data);
			
            GameManager.levelCleared();
			System.out.println("I'm on a portal. and now what happens?");
//            ArrayList<Object> data = new ArrayList<>();
//            this.x = 64; this.y = 16;
//            data.add(x);
//            data.add(y);
//            data.add(down1);
//            notifyO(data);
		}
	}
	/**
	 * Gestisce l'evento di raccolta dei power-up.
	 */
	private void onPowerUp() {
	
		int playerX = (this.x+32)/64;
		int playerY = (this.y+80)/64;
	
		PowerUp pu = GameManager.powerUpOnTile(playerY, playerX);
		if(pu!=null)	//maggiore di 2 perche va dal portale (3) fino all'ultimo power up
		{
			pu.pickup();
		}
	}
    
    /**
     * Rilascia una bomba se possibile.
     */
    public void releaseBomb()
    {
    	if(nOfBombs!=0)
    	{
    		int playerTileY = (this.y+48+32)/64;
    		int playerTileX = (this.x+32)/64;
    		
    		Iterator<Bomb> i = GameManager.getBombs().iterator();
    		while(i.hasNext())
    		{
    			Bomb next = i.next();
    			if(next.getX() == playerTileX && next.getY() == playerTileY)
    				return;
    		}
    		nOfBombs--;

    		GameManager.addBomb(new Bomb(playerTileX, playerTileY));
    		System.out.println("gonna blow! at (x, y) = "+playerTileX+", "+playerTileY);
    	}
    }
    /**
     * Funzione chiamata da Bomb quando explode
     */
    public void regainBomb()
    {
    	nOfBombs = nOfBombs<9? nOfBombs+1 : nOfBombs;
    }
    /**
     * Aumenta il numero massimo di bombe
     */
    public void addABomb()
    {
    	nOfBombs = nOfBombs<9? nOfBombs+1 : nOfBombs;
    }
    /**
     * Riposiziona il giocatore in una posizione di spawn.
     */
    public void spawn()
	{
    	setInvincible(10000);
    	ArrayList<Object> data = new ArrayList<>();
		PlayerController.enableInput(true);
        x = 64;
        y = 16;

        data.add(64);
        data.add(16);
        data.add(down2);
        
        notifyO(data);
	}
    /**
     * Esegui l'animazione di morte del giocatore.
     */
    public void deathAnimation() {
        ArrayList<Object> data = new ArrayList<>();
        
        death.play("/resources/death.wav");
        setInvincible(12000);
        PlayerController.enableInput(false);
        data.add(x);
        data.add(y);
        data.add(ImageLoader("/resources/death" + 1 + ".png"));
        notifyO(data);
        data.clear();
        
        new Timer().schedule(new TimerTask()
        		{

					@Override
					public void run() {
						data.add(x);
			            data.add(y);
			            data.add(ImageLoader("/resources/death" + 2 + ".png"));
			            notifyO(data);
			            data.clear();
			            new Timer().schedule(new TimerTask()
		        		{

							@Override
							public void run() {
								data.add(x);
					            data.add(y);
					            data.add(ImageLoader("/resources/death" + 3 + ".png"));
					            notifyO(data);
					            data.clear();
					            new Timer().schedule(new TimerTask()
				        		{

									@Override
									public void run() {
										data.add(x);
							            data.add(y);
							            data.add(ImageLoader("/resources/death" + 4 + ".png"));
							            notifyO(data);
							            data.clear();
										if(lives>=0)
										{
											new Timer().schedule(new TimerTask()
							        		{

												@Override
												public void run() {
													
													PlayerController.enableInput(true);
											        x = 64;
											        y = 16;

											        data.add(64);
											        data.add(16);
											        data.add(down2);
											        
											        notifyO(data);
													
												}
							        	
							        		}, 500);
										}
									}
				        	
				        		}, 500);
							}
							
		        	
		        		}, 500);
					}
        	
        		}, 500);
    }    
    /**
     * Notifica gli osservatori con i dati specificati (nuove coordinate e sprite).
     *
     * @param data I dati da notificare agli osservatori.
     */
    private void notifyO(ArrayList data) {
    	setChanged();
    	notifyObservers(data);
    }
    
}