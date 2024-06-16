package levels;

/**
 * Questa classe rappresenta i livelli del gioco.
 * Contiene la definizione di ciascun livello sotto forma di matrice di interi.
 * Ogni livello è rappresentato come una matrice bidimensionale di interi.
 * I numeri interi rappresentano diversi elementi nel livello.
 * 
 * Gli elementi del livello sono codificati come segue:
 * 0 = Terra
 * 1 = Muro
 * 2 = Scatola vuota
 * 3 = Scatola con Portale
 * 4 = Extra bomb
 * 5 = Explosion expander
 * 6 = Accelerator
 * 7 = Armor
 * 8 = Bomberman
 * 9 = Maximum explosion
 * 10 = Walkable bombs
 * 11 = Carbonara molto buona (rappresenta un power-up specifico)
 * 12 = Puropen (rappresenta un nemico specifico)
 * 13 = Denkyun (rappresenta un nemico specifico)
 * 14 = Star Nuts (rappresenta un nemico specifico)
 * 15 = Cupper (rappresenta un nemico specifico)
 * 16 = Kierun (rappresenta un nemico specifico)
 * 17 = Bigaron (rappresenta un boss specifico)
 * 18 = Clown Face (rappresenta un boss specifico)
 * 
 * Il tema del livello può essere impostato come 1 o 2.
 * 
 * I livelli sono organizzati in un array tridimensionale dove ciascuna dimensione rappresenta:
 * - Il numero del livello
 * - Le righe della matrice del livello
 * - Le colonne della matrice del livello
 * 
 * Il metodo `getLevel` restituisce la matrice del livello richiesto in base all'ID del livello.
 * Il metodo `getTheme` restituisce il tema corrente del livello.
 * 
 * Questa classe è progettata con un costruttore privato e metodi statici per garantire che sia utilizzata solo per l'accesso ai livelli e al tema.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
/**
 * @author mattp
 *
 */
public final class Livelli 
{
	
	private static int currentTheme = 0;
	private static int[][][] livelli = 
		{
			/*
			0 = Terra
			1 = Muro
			2 = Scatola vuota
			3 = Scatola con Portale
			4 = extra bomb
			5 = explosion expander
			6 = accelerator
			7 = armor
			8 = bomberman 
			9 = maximum explosion 
			10 = walkable bombs
			11 = carbonara molto buona
				
			nemici
			12 = puropen
			13 = denkyun
			14 = star nuts
			15 = cupper
			16 = kierun
			
			bosses
			17 = bigaron
			18 = clown face
				*/
				
				
				//Level 1 (powerup presenti : 4-5-6-8-10-11)
				{					
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,2,2,1,0,0,0,0,2,0,0,12,1},		
					{1,0,1,0,1,1,1,0,1,4,1,0,1,0,1},		
					{1,12,2,0,6,0,0,0,0,0,2,1,0,2,1},		
					{1,0,1,0,1,0,1,0,1,0,1,0,1,1,1},		
					{1,0,10,0,2,3,2,2,5,2,0,2,0,0,1},		
					{1,2,1,1,1,0,1,8,1,0,1,0,1,2,1},		
					{1,0,0,0,0,0,2,0,0,0,0,6,0,0,1},
					{1,1,1,0,1,2,1,2,1,2,1,0,1,12,1},
					{1,2,0,0,0,1,0,0,2,0,2,0,2,1,1},
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
					{1,12,0,11,0,2,2,2,0,0,2,0,2,1,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
					
				},
				
				//Level 2 (powerup presenti : 4-7-8-11)
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,0,11,0,0,0,0,0,0,2,0,0,1},		
					{1,0,1,2,1,0,1,0,1,0,1,3,1,0,1},		
					{1,0,1,0,2,0,2,0,13,0,0,1,0,2,1},		
					{1,2,1,2,1,0,1,0,1,2,1,2,1,0,1},		
					{1,0,0,2,2,0,0,2,0,0,0,2,0,0,1},		
					{1,1,1,13,1,2,1,0,1,2,1,2,1,0,1},		
					{1,0,0,0,7,0,0,0,2,0,2,0,0,0,1},
					{1,0,1,0,1,2,1,2,1,2,1,0,1,1,1},
					{1,2,0,2,0,0,0,8,2,0,2,2,2,0,1},
					{1,0,1,0,1,0,1,0,1,2,1,0,1,0,1},
					{1,0,0,4,2,1,0,0,0,2,13,0,1,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
				
				//level 3 (powerup presenti : 5-8-11)
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,2,0,14,0,0,0,2,0,0,0,0,1},		
					{1,0,1,0,1,0,1,2,1,0,1,2,1,0,1},		
					{1,2,0,0,2,0,0,11,0,0,1,1,0,2,1},		
					{1,2,1,2,1,0,1,1,1,0,1,0,1,2,1},		
					{1,0,1,2,2,2,0,2,0,0,0,14,0,2,1},		
					{1,0,1,0,1,0,1,2,1,0,1,0,1,0,1},		
					{1,2,0,5,0,2,2,0,0,2,0,8,2,0,1},
					{1,0,1,0,1,2,1,0,1,2,1,0,1,1,1},
					{1,0,0,14,0,0,2,0,0,2,0,0,0,0,1},
					{1,2,1,0,1,0,1,0,1,0,1,0,1,0,1},
					{1,0,2,1,2,0,0,0,2,2,0,3,0,2,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
				//boss 1
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},		
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},		
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},		
					{1,0,1,0,1,0,1,17,1,0,1,0,1,0,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
					{1,3,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
				//level 4 (powerup presenti : 4-7-8-11)
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,0,2,2,0,1,2,0,0,11,0,0,1},		
					{1,0,1,7,1,0,1,0,1,0,1,0,1,0,1},		
					{1,2,0,0,15,0,0,2,0,0,0,0,0,0,1},		
					{1,2,1,0,1,1,1,0,1,0,1,2,1,2,1},		
					{1,0,0,0,2,2,0,2,2,0,2,2,2,0,1},		
					{1,0,1,0,1,0,1,0,1,0,1,0,1,1,1},		
					{1,0,0,4,0,2,1,0,2,0,2,1,0,3,1},
					{1,0,1,0,1,0,1,0,1,1,1,0,1,0,1},
					{1,0,12,0,2,0,0,2,0,0,12,0,2,0,1},
					{1,8,1,0,1,0,1,2,1,2,1,0,1,0,1},
					{1,0,0,0,0,2,0,2,2,0,2,0,2,2,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
				//level 5 (powerup presenti : 9-11)
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,0,0,0,2,2,2,13,2,2,0,0,1},		
					{1,0,1,0,1,0,1,9,1,0,1,1,1,0,1},		
					{1,0,2,0,2,0,0,0,2,2,0,2,0,0,1},		
					{1,0,1,0,1,0,1,2,1,0,1,2,1,2,1},		
					{1,0,0,16,0,0,0,2,0,0,0,1,0,0,1},		
					{1,0,1,0,1,0,1,2,1,0,1,2,1,0,1},		
					{1,3,0,1,2,0,0,0,0,0,0,0,2,0,1},
					{1,1,1,0,1,16,1,0,1,0,1,1,1,2,1},
					{1,2,0,0,2,0,0,2,2,0,0,0,2,0,1},
					{1,0,1,2,1,0,1,0,1,0,1,0,1,0,1},
					{1,0,0,11,1,0,0,2,2,0,2,2,2,2,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
				//level 6 (powerup presenti : 4-6-11)
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,2,0,2,0,2,2,0,0,0,0,0,1},		
					{1,0,1,4,1,0,1,2,1,2,1,3,1,0,1},		
					{1,2,0,0,0,0,0,0,2,1,2,0,2,2,1},		
					{1,0,1,2,1,0,1,0,1,0,1,2,1,0,1},		
					{1,0,1,0,0,1,2,0,0,2,0,2,2,0,1},		
					{1,0,1,0,1,2,1,0,1,0,1,1,1,0,1},		
					{1,0,0,2,0,0,0,0,2,0,0,0,0,0,1},
					{1,12,1,0,1,1,1,0,1,2,1,0,1,0,1},
					{1,0,0,11,0,2,2,0,0,0,2,2,6,0,1},
					{1,2,1,0,1,1,1,0,1,0,1,0,1,0,1},
					{1,0,0,2,0,0,0,0,0,0,0,2,2,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
				//boss 2 
				{
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},		
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},		
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},		
					{1,0,1,0,1,0,1,18,1,0,1,0,1,0,1},		
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
					{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
					{1,3,0,0,0,0,0,0,0,0,0,0,0,0,1},
					{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},	
				},
		};

	private Livelli() {}
	/**
	 * Restituisce la matrice del livello richiesto in base all'ID del livello.
     * 
     * @param levelID L'ID del livello da restituire.
     * @return La matrice del livello corrispondente all'ID specificato.
	 */
	public static int[][] getLevel(int levelID)
	{
		currentTheme = levelID > 3 ? 2 : 1;
		return livelli[levelID];
		
	}
	/**
	 * Restituisce il tema corrente del livello.
     * 
     * @return Il tema corrente del livello.
	 */
	public static int getTheme()
	{
		return currentTheme;
	}
}