package controller;

/**
 * classe per la gestione dell'input del personaggio
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class PlayerController{
	
    private static KeyHandler key = new KeyHandler();
    private final int msBetweenBombings = 200;
    private static boolean inputEnabled = true;
    private static long lastBombing = 0;
    
	/**
	 * metodo richiamato nel game loop per muovere il personaggio
	 */
	public void move() {
		
		int up = 0;
		int right = 0;		
		
		if(inputEnabled)
		{
			if(key.upPressed)
				up--;
			if(key.downPressed)
				up++;
			if(key.rightPressed)
				right++;
			if(key.leftPressed)
				right--;
			if(key.spacePressed && System.currentTimeMillis() - lastBombing > msBetweenBombings)
			{
				PlayerManager.playerInstance.releaseBomb();
				lastBombing = System.currentTimeMillis();
			}
			//calcola in base a input passate la direzione in cui bomberman deve andare
			int[] dir = PlayerManager.playerInstance.processDirections(up, right);
			
			if(!(dir[0]==0 && dir[1]==0)) //se dir diverso da (0,0) aggiorna coordinate
				PlayerManager.playerInstance.newCoordinate(dir[0], dir[1]);
		}
		
	}
	
	/**
	 * @return istanza del keyHandler
	 */
	public static KeyHandler getKey() {
		return key;
	}
	/**
	 * metodo per abilitare/disattivare il player input
	 * @param value
	 */
	public static void enableInput(boolean value)
	{
		inputEnabled = value;
	}

	/**
	 * ritorna lo stato dell'inputEnabled
	 * @return stato dell'inputEnabled
	 */
	public static boolean inputEnabled() {
		return inputEnabled;
	}
}
