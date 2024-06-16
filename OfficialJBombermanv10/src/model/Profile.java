package model;

/**
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Profile 
{
	private String name;
	private int avatarID;

	//profile stats	
	private int lastScore;
	private int totalScore;
	private int gamesWon;
	private int gamesLost;
	
	public Profile()
	{
		
	}
	/**
	 * Costruttore per un nuovo profilo con tutte le informazioni iniziali.
     *
     * @param profileName    Il nome del profilo.
     * @param avatarNumber   L'ID dell'avatar associato al profilo.
     * @param lastGameScore  Il punteggio dell'ultima partita.
     * @param summedTotalScore  Il punteggio totale accumulato in tutte le partite.
     * @param nOfGamesWon    Il numero di partite vinte.
     * @param nOfGamesLost   Il numero di partite perse.
	 */
	public Profile(String profileName, int avatarNumber, int lastGameScore, int summedTotalScore, int nOfGamesWon, int nOfGamesLost)
	{
		name = profileName;
		avatarID = avatarNumber;
		lastScore = lastGameScore;
		totalScore = summedTotalScore;
		gamesWon = nOfGamesWon;
		gamesLost = nOfGamesLost;
	}
	/**
	 * Reimposta il profilo con valori predefiniti.
     *
     * @param profileN Il numero del profilo.
	 */
	public void resetProfile(int profileN)
	{
		name = "Profile#"+profileN;
		avatarID = 0;
		lastScore = 0;
		totalScore = 0;
		gamesWon = 0;
		gamesLost = 0;
	}
	
	//Setter
	
	//profile settings
	public void setName(String profileName) { name = profileName; }
	public void setAvatar(int avatarNumber) { avatarID = avatarNumber; }
	
	//profile stats
	public void setLastScore(int lastGameScore) { lastScore = lastGameScore; }
	public void setTotalScore(int summedTotalScore) { totalScore = summedTotalScore; }
	public void setGamesWon(int nOfGamesWon) { gamesWon = nOfGamesWon; }
	public void setGamesLost(int nOfGamesLost) { gamesLost = nOfGamesLost; }
	
	
	//Getter
	
	//profile settings
	public String getName() { return name; }
	public int getAvatar() { return avatarID; }
	
	//profile stats
	public int getLastScore() { return lastScore; }
	public int getTotalScore() { return totalScore; }
	public int getGamesWon() { return gamesWon; }
	public int getGamesLost() { return gamesLost; }
	
	public String getAllInfo() { return name+"\n"+avatarID+"\n"+lastScore+"\n"+totalScore+"\n"+gamesWon+"\n"+gamesLost; }
	
	//add a game's stats
	/**
	 * aggiunge le statistiche del gioco
	 * @param win 	se hai vinto
	 * @param score il punteggio
	 */
	public void addGame(boolean win, int score) 
	{
		if(win)
			gamesWon++;
		else
			gamesLost++;
		
		lastScore = score;
		totalScore += score;
	}
}
