package model;

import java.util.Arrays;
import java.util.Observable;
import java.util.stream.IntStream;

import levels.Livelli;
import view.ArenaView;

/**
 * Questa classe rappresenta l'arena di gioco.
 * Contiene la griglia di gioco e gestisce il caricamento dei livelli.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Arena extends Observable{
	private static int currentLevel = -1;
	private int theme = 1;
	private static int[][] griglia = new int[15][15];
	private static int[][] grigliaIniziale;
	
	
	//getter
	/**
	 * Restituisce il tema dell'arena.
     *
     * @return Il tema dell'arena.
	 */
	public int getTema() {
		return theme;
	}

	
	//notifica osservatori
	/**
	 * Notifica gli osservatori della modifica dello stato della griglia.
	 */
	private void statoGriglia(){
		setChanged();
		notifyObservers(griglia);
	}
	
	/**
	 * Restituisce la griglia di gioco.
     *
     * @return La griglia di gioco.
	 */
	public int[][] getGriglia(){return griglia;}
	/**
	 * Restituisce la griglia iniziale del livello.
     *
     * @return La griglia iniziale del livello.
	 */
	public int[][] getGrigliaIniziale() {return grigliaIniziale;}
	/**
	 * Modifica il valore di una casella nella griglia di gioco.
     *
     * @param x       La coordinata x della casella.
     * @param y       La coordinata y della casella.
     * @param indice  Il nuovo valore da assegnare alla casella.
	 */
	public void modificaCasella(int x, int y, int indice)
	{
		griglia[y][x] = indice;
		statoGriglia();
	}
	/**
	 * Modifica l'intera mappa di gioco.
     *
     * @param griglia  La nuova mappa di gioco.
	 */
	public void modificaMappa(int[][] griglia)
	{
		this.griglia = griglia;
		statoGriglia();
	}
	/**
	 * Carica il prossimo livello di gioco.
	 */
	public void caricaProssimoLivello()
	{
		currentLevel++;
		this.grigliaIniziale = Livelli.getLevel(currentLevel);
		this.theme = Livelli.getTheme();
		
		//stream per modificare i valori 2 < x < 12
		griglia = Arrays.stream(grigliaIniziale)
                .map(row -> IntStream.of(row)
                        .map(value -> (value > 2 && value < 12) ? 2 : value > 11 ? 0 : value)
                        .toArray())
                .toArray(int[][]::new);
		
		statoGriglia();
	}
	
	/**
	 * Restituisce il valore di una casella nella griglia iniziale del livello.
     *
     * @param x  La coordinata x della casella.
     * @param y  La coordinata y della casella.
     * @return Il valore della casella nella griglia iniziale del livello.
	 */
	public int getCella(int x, int y) {
		return grigliaIniziale[y][x];
		
	}
	/**
	 * Restituisce il numero del livello corrente.
     *
     * @return Il numero del livello corrente.
	 */
	public int getLevel()
	{
		return currentLevel;
	}


	/**
	 * Imposta il tema dell'arena.
     *
     * @param tema Il tema da impostare.
	 */
	public void setTema(int tema) {
		theme = tema;
	}
}
