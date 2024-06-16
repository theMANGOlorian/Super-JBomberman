package controller;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Classe per gestire l'input del mouse
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class MouseController implements MouseListener{
	static MouseController singleton;
	static boolean holding;
	static Component component;
	
	private MouseController() {
	}
	/**
	 * permette di instanziare UN solo mouse (singleton pattern)
	 * @return instanza del Mouse
	 */
	public static MouseController getMouseControls()
	{
		if(singleton==null)
			singleton = new MouseController();
		return singleton;
	}
	/**
	 * @param parentComponent
	 * @return instanza del Mouse
	 */
	public static MouseController getMouseControls(Component parentComponent)
	{
		component = parentComponent;
		return getMouseControls();
	}
	
	/**
	 * Given two points, returns the direction the second is from the first in x,y format (values can be -1 0 1)
	 * @param point1 the first point (usually, the player)
	 * @param point2 the second point (usually, the mouse)
	 * @return direction of second point relative from the first point
	 */
	private static int[] pointsDirection(Point point2, Point point1) 
	{
		//point1 sarebbe il giocatore, mentre point2 sarebbe il mouse
        int[] direction = {0, 0};
        if(point2==null || point1 == null)
        	return direction;

        if(Math.abs(point2.x-point1.x) > Math.abs(point2.y-point1.y))
        {
        	direction[0] = point2.x-point1.x>0 ? -1 : 1;
        	direction[1] = 0;
        }
        else
        {
        	direction[0] = 0;
        	direction[1] = point2.y-point1.y>0 ? -1 : 1;
        }

        return direction;
    }
	/**
	 * metodo chiamato nel game loop per vedere se il player
	 */
	public static void move()
	{
		if(PlayerController.inputEnabled() && holding)
		{
			System.out.println("cliccking!!!! mouse is at " + component.getMousePosition());
			System.out.println("Player x="+PlayerManager.playerInstance.getX()+",y="+PlayerManager.playerInstance.getY());
			int[] coords = pointsDirection(new Point(PlayerManager.playerInstance.getX()+72,PlayerManager.playerInstance.getY()+204),component.getMousePosition());
			System.out.println(coords[0] + " " + coords[1]);
			PlayerManager.playerInstance.newCoordinate(coords[1], coords[0]);
		}
		
	}
	
	private static final int msBetweenBombings = 200;
    private static long lastBombing = 0;
	/**
	 * se il tasto destro del mouse è cliccato allora rilascia una bomba
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e))
			if(System.currentTimeMillis() - lastBombing > msBetweenBombings)
			{
				PlayerManager.playerInstance.releaseBomb();
				lastBombing = System.currentTimeMillis();
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e))
			holding = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e))
			holding = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
