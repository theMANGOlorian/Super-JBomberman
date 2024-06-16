package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.stream.IntStream;

import controller.MouseController;
import controller.MusicAudio;
import controller.PlayerController;
import controller.PlayerManager;
import view.GameWindow;
import view.MenuPanel;
import view.PlayerView;
import view.GameWindow.Scene;
import controller.SoundEffect;

/**
 * gestione del gioco
 * @author Mattia Pandolfi & Benjamin Ruff
 *
 */
public class GameManager extends Observable
{
	
	private static PlayerView playerView;
	private static PlayerController playerController;
	private static Arena lv;
	private static GameWindow finestra;
	private static GameManager game;
	private static ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private static Profile[] profiles = new Profile[3];
	private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();	//all enemies on the current level
	private static ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	
	//Costanti e Variabili gameLoop
	private static int deltaTime;
	private static final int maxFPS = 60;	//Può essere impostato 4-60. Altri valori causano problemi con il sistema di collisioni
	public static final int minMS = 1000/maxFPS;	//calcola i ms tra frame minimi (da non calcolarlo ogni frame)
	
	//Variabili partita
	private static int score = 0;
	private static int next1Up = 0;	//5000*2^next1Up sono i punti da raggiungere per prendere +1 vite
	private static int level = 0;	//livello su cui deve stare il giocatore
	
	//Effetti sonori
	static SoundEffect boom = new SoundEffect();
	static SoundEffect placeBomb = new SoundEffect();
	static SoundEffect intro = new SoundEffect();
	static SoundEffect collectPU = new SoundEffect();
	
	private static int selectedProfile = 1;
	private static boolean playWithMouse = false;
	
	//Funzioni Getter
	public static int getScore() {return score;}
	public static int enemiesLeft() {return enemies.size();}
	public static int getSelectedProfileNumber() {return selectedProfile; }
	public static int getDeltaTime() {return deltaTime; }
	public static PlayerView getPlayerView() { return playerView; }
	public static Arena getArena() { return lv; }
	public static ArrayList<Bomb> getBombs() { return bombs; }
	public static boolean isPlayingWithMouse() { return playWithMouse;}
	
	public static void setPlayWithMouse(boolean value) { playWithMouse = value;}
	public static void changePlayWithMouse() { playWithMouse = !playWithMouse;}
	
	/**
	 * metodo che utilizza una stream per caricare i nemici nei livelli
	 */
	public static void enemyLoader() {
	    int[][] matrix = lv.getGrigliaIniziale();

	    IntStream.range(0, matrix.length)
	            .boxed()
	            .flatMap(row -> IntStream.range(0, matrix[row].length)
	                    .filter(col -> matrix[row][col] >= 12 && matrix[row][col] <= 18)
	                    .mapToObj(col -> {
	                        int enemyType = matrix[row][col]; // Get the tile value
	                        int startX = col; // You can adjust this based on your matrix layout
	                        int startY = row; // You can adjust this based on your matrix layout
	                        
	                        // Create different Enemy objects based on enemyType
	                        Enemy enemy;
	                        switch (enemyType) {
	                            case 12:
	                                enemy = new Puropen(startX, startY);	                               
	                                break;
	                            case 13:
	                                enemy = new Denkyun(startX, startY);
	                                break;
	                            case 14:
	                                enemy = new StarNuts(startX, startY);
	                                break;
	                            case 15:
	                                enemy = new Cupper(startX, startY);
	                                break;
	                            case 16:
	                                enemy = new Kierun(startX, startY);
	                                break;
	                            case 17:
	                                enemy = new Bigaron(startX, startY);
	                                break;
	                            case 18:
	                                enemy = new ClownMask(startX, startY);
	                                break;
	                                
	                            // Add more cases for other enemy types
	                            default:
	                                // Handle the case when enemyType doesn't match any known type
	                                enemy = null;
	                                break;
	                        }
	                        return enemy;
	                    })
	            )
	            .forEach(enemy -> {
	                enemies.add(enemy); // Add the Enemy object to your static ArrayList
	            });
	}
	
	/**
	 * aggiunge punti
	 * @param points
	 */
	public static void addScore(int points)
	{
		score += points;
		while(score>(5000*(Math.pow(2, next1Up))))
		{
			PlayerManager.playerInstance.addALife();
			next1Up++;
		}
		game.setChanged();
		game.notifyObservers(new int[] {PlayerManager.playerInstance.getLives(), score});
	}
	/**
	 * controlla se c'è una bomba su x y
	 * @param y
	 * @param x
	 * @return se cè una bomba su x y
	 */
	public static boolean bombOnTile(int y, int x)
    {
		Iterator<Bomb> i = bombs.iterator(); 
    	while(i.hasNext())
		{
			Bomb next = i.next();
			if(next.getX() == x && next.getY() == y)
				return true;
		}
    	return false;
    }
	/**
	 * controlla se c'è un powerup su x y
	 * @param y
	 * @param x
	 * @return ritorna powerUp
	 */
	public static PowerUp powerUpOnTile(int y, int x)
    {
    	Iterator<PowerUp> i = powerUps.iterator(); 
    	while(i.hasNext())
		{
			PowerUp next = i.next();
			if(next.getX() == x && next.getY() == y)
				return next;
		}
    	return null;
    }
	/**
	 * rimuove le entità (bombe, nemici e powerup)
	 */
	private static void removeEntities()
	{
		bombs.clear();
		enemies.clear();
		powerUps.clear();
	}
	
	/**
	 * seleziona il profilo
	 * @param n
	 */
	public static void selectProfile(int n)
	{
		
		selectedProfile = n;
	}
	
	/**
	 * Ritorna l'unica istanza di gioco, che viene creata nel caso non è stato istanziato
	 * @return il gioco di tipo GameManager
	 */
	public static GameManager getGameManager()
	{
		if (game==null)
			game = new GameManager();
		return game;
	}
	
	/**
	 *  costruttore della classe
	 */
	private GameManager()
	{
		
		game = this;
		lv = new Arena(); //creazione livello
	    lv.setTema(3);
	    lv.caricaProssimoLivello();
	    
		PlayerManager.playerInstance=new Player();
	    playerView = new PlayerView(PlayerManager.playerInstance);
	    playerController = new PlayerController();
	    
	    
	    finestra = new GameWindow();
	    finestra.setScene(Scene.mainMenu);
	    
	    
	    //Menu loop
	    while(finestra.isEnabled() && level==0)
	    {
	    	long timeBeforeFrame = System.currentTimeMillis();
	    	
	    	//Behaviour every frame
	    	
	    	MenuPanel.moveBaloon();
	    	
        	
	    	delayFrame(timeBeforeFrame);
	    	deltaTime = (int) (System.currentTimeMillis()- timeBeforeFrame);	//time it took to execute one frame
	    }
	    //Game loop
	    finestra.setScene(Scene.normalGame);
	    
	    enemyLoader();
	    
	    //Hitbox
//	    JLabel playerpointRed = finestra.addToFrame(PlayerManager.playerInstance.getX(), PlayerManager.playerInstance.getY()+48, 10, 10, "tile01.png");
//	    playerpointRed.setIcon(null);
//	    playerpointRed.setOpaque(true);
//	    playerpointRed.setBackground(Color.RED);
//	    
//	    JLabel playerpointBlue = finestra.addToFrame(PlayerManager.playerInstance.getX()+54, PlayerManager.playerInstance.getY()+48+54, 10, 10, "tile01.png");
//	    playerpointBlue.setIcon(null);
//	    playerpointBlue.setOpaque(true);
//	    playerpointBlue.setBackground(Color.BLUE);
	    
	    if(!playWithMouse)
	    {
	    	while(finestra.isEnabled() && level==1) 
	    	{        	
		    	long timeBeforeFrame = System.currentTimeMillis();
		    	
		    	//Behaviour every frame
		    	
		    	playerController.move();
		    	playerView.showInvincible();
		    	
		    	
		    	for (Enemy enemy : enemies) {
		            enemy.behave();
		        }
		    		
		    	
		    	//enemy.move();
		    	//check enemy and player collision
		    	
		    	//Hitbox
	//	    	playerpointRed.setLocation(PlayerManager.playerInstance.getX(), PlayerManager.playerInstance.getY()+48);
	//	    	playerpointBlue.setLocation(PlayerManager.playerInstance.getX()+54, PlayerManager.playerInstance.getY()+48+54);
		    	delayFrame(timeBeforeFrame);
		    	deltaTime = (int) (System.currentTimeMillis()- timeBeforeFrame);	//time it took to execute one frame
		    	//FPS = 1000/deltaTime
		    	//deltaTime = 1000/FPS
	    	}
	    }
	    else
	    {
	    	while(finestra.isEnabled() && level==1) 
		    {        	
		    	long timeBeforeFrame = System.currentTimeMillis();
		    	
		    	//Behaviour every frame
		    	
		    	MouseController.move();
		    	playerView.showInvincible();
		    	for (Enemy enemy : enemies) {
		            enemy.behave();
		        }
		    	//check enemy and player collision
		    	
		    	delayFrame(timeBeforeFrame);
		    	deltaTime = (int) (System.currentTimeMillis()- timeBeforeFrame);	//time it took to execute one frame
		    	//FPS = 1000/deltaTime
		    	//deltaTime = 1000/FPS
		    }
	    }
	    closeGame();
	}
	
	/**
	 * limita gli FPS
	 * @param timeBeforeFrame
	 */
	private void delayFrame(long timeBeforeFrame)
	{
		try
		{
    		//if it tries to be faster than 60fps, wait 'till its not
    		long timeItTook = (System.currentTimeMillis()- timeBeforeFrame);
    		
			if(timeItTook < minMS)
			{
				Thread.sleep((minMS)-timeItTook);
			}
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
    }
	/**
	 * ritorna i profili
	 * @param n
	 * @return
	 */
	public static Profile getProfile(int n)
	{
		if(n<1 || n>3)
			n=1;
		return profiles[n-1];
	}
	/**
	 * aggiorna i dati del profilo
	 * @param n
	 */
	public static void updateProfileData(int n)
	{
		FileWriter myWriter;
		try {
			myWriter = new FileWriter(System.getProperty("user.dir")+"\\src\\saves\\profile"+n+".dat");
			myWriter.write(profiles[n-1].getAllInfo());
		    myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * carica i dati dei profili
	 * @throws IOException
	 */
	public static void loadProfileData() throws IOException
	{
		loadProfileData(1);
		loadProfileData(2);
		loadProfileData(3);
	}
	/**
	 * carica i dati dei profili
	 * @param profileN
	 * @throws IOException
	 */
	public static void loadProfileData(int profileN) throws IOException
	{
		if(profileN < 1 || profileN > 3)
		{
			System.out.println("Invalid Profile number to update. Updating all profiles...");
			loadProfileData();
			return;
		}
		Profile p = new Profile();
		
		BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\src\\saves\\profile" + profileN + ".dat" ));
		
		//profile settings
		p.setName(br.readLine());
		p.setAvatar(Integer.parseInt(br.readLine()));
		
		//profile stats
		p.setLastScore(Integer.parseInt(br.readLine()));
		p.setTotalScore(Integer.parseInt(br.readLine()));
		p.setGamesWon(Integer.parseInt(br.readLine()));
		p.setGamesLost(Integer.parseInt(br.readLine()));
		
		profiles[profileN-1] = p;
		
		br.close();
	}
	//metodi di menu
	/**
	 * va al game loop
	 */
	public static void newGame()
	{
		level++;
		PlayerManager.playerInstance.spawn();
		//per adesso carica subito il livello, da cambiare
		
		MusicAudio.stop();
		intro.play("/resources/Intro.wav");
		
		Timer timer = new Timer();
        int delay = 3000;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Questo codice verrà eseguito dopo 2 secondi

                timer.cancel(); // Interrompi il timer
                
                MusicAudio.play("/resources/lv1.wav");
            }
        }, delay);
        
	}
	/**
	 * chiude il gioco
	 */
	public static void closeGame()
	{
		System.exit(0);	//questo invece è finito, è solo una riga, bel metodo insomma
	}
	
	//metodi di gioco
	/**
	 * aggiunge una bomba
	 * @param bombToAdd
	 */
	public static void addBomb(Bomb bombToAdd)
	{
		bombToAdd.label = finestra.addToFrame(bombToAdd.getX()*64, bombToAdd.getY()*64, 64, 64, "bomb.gif");
		bombs.add(bombToAdd);
		
		placeBomb.play("/resources/PlaceBomb.wav");
	}
	/**
	 * viene richiamato quando entri nel portale e non ci sono piu nemici
	 */
	public static void levelCleared()
	{
		//animazione wowie
		PlayerController.enableInput(false);
		
		
		MusicAudio.stop();
		MusicAudio.play("/resources/StageComplete.wav");
		
		new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            	if(lv.getLevel()<7)
            	{
            		MusicAudio.stop();
                	MusicAudio.play("/resources/Intro.wav");
                	
                	
                	new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                        	//removeEntities();
                        	
                        	lv.caricaProssimoLivello();
                        	finestra.resetArena();
                        	enemyLoader();
                        	
                        	
                        	MusicAudio.stop();
                        	
                        	if(lv.getLevel()<3)
                        		MusicAudio.play("/resources/lv1.wav");
                        	else if(lv.getLevel()==3||lv.getLevel()==7)
                        	{
                        		placeBomb.play("/resources/Roar.wav");
                        		MusicAudio.play("/resources/boss.wav");
                        	}
                        		
                        	else
                        		MusicAudio.play("/resources/lv1.wav"); //cambia a lv2
                        	
                        	
                        	
                    		
                    		
                    		
                    		PlayerManager.playerInstance.spawn();
                    		
                        }
                    }, 2000);
            	}
            	else
            	{
            		//finito ultimo livello, hai vinto
            		MusicAudio.stop();
            		MusicAudio.play("/resources/youWin.wav");
            		finestra.gameOverWindow(true);
            		getProfile(selectedProfile).addGame(true, score);
            		updateProfileData(selectedProfile);
            	}
            	
            }
        }, 4000);
		
		
		
		
	}
	
	/**
	 * gestisce le esplosioni della bomba
	 * @param x
	 * @param y
	 * @param bomb
	 */
	public static void explodeBomb(int x, int y, Bomb bomb)
	{
		//parte visiva
		finestra.removeLabelFromFrame(bomb.label);
		
		boom.play("/resources/BombExplodes.wav");
		
		//parte pratica
		bombs.remove(bomb);
		
		PlayerManager.playerInstance.regainBomb();
		
		int[][] explodingTiles = new int[4*PlayerManager.playerInstance.getBombSize()+1][2];
		
		
		explodingTiles[0] = new int[]{y,x};
		int n = 1;
		
		int southStrength = 0;
		int northStrength = 0;
		int eastStrength = 0;
		int westStrength = 0;
		
		//South
		for(int i = 0; i<PlayerManager.playerInstance.getBombSize(); i++)
		{
			
			//if tile is empty
			if(lv.getGriglia()[y+i+1][x]==0)
			{
				
				explodingTiles[n] = new int[] {y+i+1,x};
				n++;
				southStrength++;
			}
			else 
				if(lv.getGriglia()[y+i+1][x]==1)	//if tile is wall, stop expanding
					break;
				else
				{
					breakBox(x,y+i+1);				//if its neither, its a box. Break it, then stop expanding
					break;
				}
		}
		
		//North
		for(int i = 0; i<PlayerManager.playerInstance.getBombSize(); i++)
		{
			//if tile is empty
			if(lv.getGriglia()[y-i-1][x]==0)
			{
				explodingTiles[n] = new int[] {y-i-1,x};
				n++;
				northStrength++;
				
			}
			else 
				if(lv.getGriglia()[y-i-1][x]==1) //if tile is wall, stop expanding
					break;
				else
				{
					breakBox(x,y-i-1);				//if its neither, its a box. Break it, then stop expanding
					break;
				}
		}
		//East
		for(int i = 0; i<PlayerManager.playerInstance.getBombSize(); i++)
		{
			//if tile is empty
			if(lv.getGriglia()[y][x+i+1]==0)
			{
				
				explodingTiles[n] = new int[] {y,x+i+1};
				n++;
				eastStrength++;
			}
			else 
				if(lv.getGriglia()[y][x+i+1]==1)	//if tile is wall, stop expanding
					break;
				else
				{
					breakBox(x+i+1,y);				//if its neither, its a box. Break it, then stop expanding
					break;
				}
		}
		//West
		for(int i = 0; i<PlayerManager.playerInstance.getBombSize(); i++)
		{
			//if tile is empty
			if(lv.getGriglia()[y][x-i-1]==0)
			{
				
				explodingTiles[n] = new int[] {y,x-i-1};
				n++;
				westStrength++;
			}
			else 
				if(lv.getGriglia()[y][x-i-1]==1)	//if tile is wall, stop expanding
					break;
				else
				{
					breakBox(x-i-1,y);				//if its neither, its a box. Break it, then stop expanding
					break;
				}
					
		}
		//parte view
		
		
		
		int playerX =( PlayerManager.playerInstance.getX()+32)/64;
		int playerY = (PlayerManager.playerInstance.getY()+80)/64;
		
		String dir = "";
		
		int bombSize = PlayerManager.playerInstance.getBombSize();
		
		boolean isdead = false;
		
		for (int i = 0; i<n; i++)
		{
			
			
			//fire direction statements
			
			if(explodingTiles[i][1] == x && explodingTiles[i][0] == y){dir = "fireCenter.gif";}
			else if(explodingTiles[i][1] - bombSize == x && explodingTiles[i][0] == y){dir = "fireRight.gif";}
			else if(explodingTiles[i][1] == x && explodingTiles[i][0] - bombSize == y){dir = "fireDown.gif";}
			else if(explodingTiles[i][1] + bombSize == x && explodingTiles[i][0] == y){dir = "fireLeft.gif";}
			else if(explodingTiles[i][1] == x && explodingTiles[i][0] + bombSize == y){dir = "fireUp.gif";}
			else if(explodingTiles[i][1] > x && explodingTiles[i][0] == y ) {dir = "fireX.gif";}
			else if(explodingTiles[i][1] < x && explodingTiles[i][0] == y ) {dir = "fireX.gif";}
			else if(explodingTiles[i][1] == x && explodingTiles[i][0] > y ) {dir = "fireY.gif";}
			else if(explodingTiles[i][1] == x && explodingTiles[i][0] < y ) {dir = "fireY.gif";}
			
			
			finestra.addExplosion(finestra, explodingTiles[i][1], explodingTiles[i][0], dir);
			
			
			
			//
			if(playerX == explodingTiles[i][1] && playerY == explodingTiles[i][0])
			{
				isdead = true;
				//return;
			}
			
			for(Enemy e : enemies)
			{
				System.out.println(e.isBoss());
				if ((e.getX() + 32) / 64 == explodingTiles[i][1] && (e.getY() + 32) / 64 == explodingTiles[i][0]) 
				    {
				        e.hit();
				    }
			}
			

		}
		if(isdead)
			Die();
		
	}
	/**
	 * rompe i blocchi morbidi (le box)
	 * @param x
	 * @param y
	 */
	private static void breakBox(int x, int y)
	{
		//Play gif
		//wait until gif is finished
		int tileType = lv.getGrigliaIniziale()[y][x];
		
		//animazione esplosione
		JLabel explosion = finestra.addToFrame(x*64, y*64, 64, 64, "boxExplosion"+lv.getTema()+".gif"); //boxExplosion gif
		new Timer().schedule(new TimerTask() {
			  @Override
			  public void run() {
				  	finestra.removeLabelFromFrame(explosion);					
				  	lv.modificaCasella(x, y, 0);
			  }
			}, 600);
			
		
		if (tileType != 2)	//se la scatola non è vuota
		{
			
			//cosa c'è sotto la scatola???
			switch (tileType) {    //se la scatola contiene un portale
            
            	case 3 : 	//se la scatola contiene un portale
            		finestra.addToFrame(x*64, y*64, 64, 64, "portal.gif"); break;
            	case 4 :    //se la scatola contiene un extrabomb item
            		powerUps.add(new ExtraBombPU(finestra.addToFrame(x*64, y*64, 64, 64, "extrabomb.gif"),x,y, 10)); break;
            	case 5 :    //se la scatola contiene un explosion expander item
            		powerUps.add(new ExplosionExpanderPU(finestra.addToFrame(x*64, y*64, 64, 64, "explosionexpander.gif"),x,y, 200)); break;
			    case 6 :    //se la scatola contiene un accelerator item 
			    	powerUps.add(new AccelleratorPU(finestra.addToFrame(x*64, y*64, 64, 64, "accelerator.gif"),x,y, 400)); break;
			    case 7 :    //se la scatola contiene un armoritem
			    	powerUps.add(new ArmorPU(finestra.addToFrame(x*64, y*64, 64, 64, "armor.gif"),x,y, 500)); break;
			    case 8 :    //se la scatola contiene un bomberman item
			    	powerUps.add(new BombermanPU(finestra.addToFrame(x*64, y*64, 64, 64, "bomberman.gif"),x,y, 500));  break;
			    case 9 :    //se la scatola contiene un maximum explosion item
			    	powerUps.add(new MaximumExplosionPU(finestra.addToFrame(x*64, y*64, 64, 64, "maximumexplosion.gif"),x,y, 0)); break;
			    case 10 :    //se la scatola contiene un heart item
			    	powerUps.add(new BombPasserPU(finestra.addToFrame(x*64, y*64, 64, 64, "bombpasser.gif"),x,y,700)) ; break;
			    case 11 :    //se la scatola contiene un carbonara item
			    	powerUps.add(new Carbonara(finestra.addToFrame(x*64, y*64, 64, 64, "carbonara.gif"), x, y, 8000)) ; break;
            } 
		}
	}	
	/**
	 * prende il powerup 
	 * @param pu
	 */
	public static void collectPowerUp(PowerUp pu)
	{
		collectPU.play("/resources/ItemGet.wav");
		finestra.removeLabelFromFrame(pu.getLabel());
		powerUps.remove(pu);
	}
	
	/**
	 * viene richiamato quando il giocatore muore
	 */
	public static void Die()
	{
		Player p = PlayerManager.playerInstance;
		//se il giocatore è invincibile, non può mica morire
		
		
		if(p.isInvincible())
			return;
		
		p.removeALife();
		game.setChanged();
    	game.notifyObservers(new int[] {p.getLives(),score});
		
		
		//così non cè un loop di morte
		if(p.getLives()<-1)
			return;
		if(p.getLives()<0)
			GameOver();
		
		
		
		
		
		p.deathAnimation();
	}
	/**
	 * quando la partita finisce richiama la gameOver window
	 */
	static void GameOver()
	{
		PlayerController.enableInput(false);
		MusicAudio.stop();
		MusicAudio.play("/resources/gameOver.wav");
		MusicAudio.setLoop(false);
		
		//update game data on profile
		getProfile(selectedProfile).addGame(false, score);
		updateProfileData(selectedProfile);
		
		//finestra.setScene(Scene.gameOver);
		new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            	finestra.gameOverWindow(false);
            }
        }, 5000);

		
	}
	/**
	 * aggiunge una vita al giocatore
	 */
	public static void addALife()
	{
		PlayerManager.playerInstance.addALife();
		game.setChanged();
    	game.notifyObservers(new int[] {PlayerManager.playerInstance.getLives(),score});
	}
	
	public static void paintAnimatedTile(int x, int y, ImageIcon image, Graphics g)
	{
		finestra.paintIcon(image, g, x, y);
	}
	/**
	 * rimuove i nemici dalla mappa
	 * @param enemy
	 */
	public static void killEnemy(Enemy enemy) {
		enemies.remove(enemy);
		
	}


	
}
