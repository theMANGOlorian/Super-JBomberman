package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JLabel;

import model.GameManager;

/**
 * Questa classe rappresenta un pulsante di menu personalizzato.
 * Estende la classe JLabel e implementa l'interfaccia MouseListener.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class PulsanteMenu extends JLabel implements MouseListener
{
	Method func;
	private boolean isEnabled = true;
	/**
	 * @param text Testo che il pulsante fa vedere
	 * @param num Il numero che appartiene al pulsante
	 * @param funcWhenCliccked Il nome della funzione che viene richiamata in classOfFuncWhenCliccked qunando il pulsante viene premuto (Attenzione, assicurati che la funzione sia statica)
	 * @param classOfFuncWhenCliccked La classe da cui verrà richiamata la funzione funcWhenCliccked
	 */
	PulsanteMenu(String text, float num, String funcNameWhenCliccked, Class<?> classOfFuncWhenCliccked)
	{
		setEnabled(false);
		setVisible(false);
		try 
		{
			func = classOfFuncWhenCliccked.getDeclaredMethod(funcNameWhenCliccked);
		} 
		
		catch (SecurityException e) 
		{
			System.out.println("La funzione del pulsante \""+text+"\" non è raggiungibile. Assicurati che la funzione \""+funcNameWhenCliccked+"\" sia publica e statica");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("la funzione non esiste");
			e.printStackTrace();
		}
		
		setText(text);
		
		setSize(text.length()*35, 75);
		setLocation(GameWindow.windowWidth/2-getWidth()/2,(GameWindow.windowHeight/2-getHeight()/2)+50+(int)(100*num));
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		
		setForeground(Color.orange);
		
		//setOpaque(true);	//Remove comment to debug border
		//setBackground(Color.magenta);
		
		
		
		addMouseListener(this);
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
	}
	/**
	 *Imposta lo stato di abilitazione del pulsante.
     *
     * @param value true se il pulsante è abilitato, false altrimenti
	 */
	public void setEnabled(boolean value)
	{
		isEnabled = value;
	}
	
	/**
	 *
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(isEnabled)
		try {
			func.invoke(null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			System.out.println("La funzione dichiarata ha dei parametri errati. Assicurati che la funzione non ne richieda alcuni e che esso sia statica");
			e1.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setForeground(Color.magenta);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setForeground(Color.orange);
	}

}
