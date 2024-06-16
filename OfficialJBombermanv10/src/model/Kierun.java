package model;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Rappresenta il nemico "Kierun" nel gioco.
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class Kierun extends Enemy {
    private final static ImageIcon moving = new ImageIcon(new ImageIcon(Puropen.class.getResource("/resources/Kierun.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));
    private final static ImageIcon notMoving = new ImageIcon(new ImageIcon(Puropen.class.getResource("/resources/Kierun3.gif")).getImage().getScaledInstance(64, 96, Image.SCALE_DEFAULT));

    private boolean isMoving = true; // Inizia come true
    private Timer notMovingTimer;

    /**
     * Crea un nuovo nemico "Kierun" con valori di attributi specificati.
     *
     * @param x La coordinata x del nemico.
     * @param y La coordinata y del nemico.
     */
    public Kierun(int x, int y) {
        super(200, 1, x, y, 0.15f, false, false, moving, false);
    }

    private int moveX = 1; // Inizia muovendosi a destra
    private int moveY = 0;
    boolean previouslyMoved = true;
    private ImageIcon currentImage = moving;

    private boolean state = false;
    /**
     *definisce il comportamento di Kierun nel gioco
     *Il nemico può muoversi in orizzontale o verticale e ha una possibilità di fermarsi e diventare invisibile.
     */
    @Override
    public void behaviour() {
        Random random = new Random();
        
        if (isMoving) {
        	System.out.println("Moving");

            boolean moved = move(moveX, moveY);
            if (!moved) {
                if (moveX != 0) {
                    if (previouslyMoved) {
                        moveX = -moveX;
                        previouslyMoved = false;
                    } else {
                        moveX = 0;
                        moveY = 1;
                        previouslyMoved = true;
                    }
                } else {
                    if (previouslyMoved) {
                        moveY = -moveY;
                        previouslyMoved = false;
                    } else {
                        moveY = 0;
                        moveX = 1;
                        previouslyMoved = true;
                    }
                }
            } else {
                previouslyMoved = true;
            }
        }

        if(random.nextDouble() < 0.01 && state == false) {
        	currentImage = notMoving;
        	isMoving = false;
        	state = false;
        	
        }
        
        if (isMoving == false && state == true) {// 10% di probabilità di fermarsi
        	System.out.println("not Moving");
            // Avvia un timer per ripristinare isMoving dopo 10 secondi
            notMovingTimer = new Timer();
            notMovingTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    state = false;
                    isMoving = true;
                    currentImage = moving;
                    notMovingTimer.cancel();
                }
            }, 7000);
        }
        if(!isMoving) {state = true;}

        notifyTheObserver(currentImage);
        
        // Questo notifica l'osservatore con l'immagine corrente
        
    }
}
