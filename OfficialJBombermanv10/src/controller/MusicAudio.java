package controller;

import javax.sound.sampled.*;

/**
 * classe per gestire i file .wav per le musiche del gioco
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class MusicAudio {

    private static Clip clip;

    /**
     * play() viene richiamato quando occorre aggiungere della musica nel gioco passandogli un .wav tra i parametri
     * @param audioFilePath path del file .wav
     */
    public static void play(String audioFilePath) {
        try {
            stop(); // Ferma la riproduzione audio precedente, se presente

            // Apri il file audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(MusicAudio.class.getResource(audioFilePath));

            // Ottieni il formato audio del file
            AudioFormat format = audioInputStream.getFormat();

            // Crea una nuova clip audio
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);

            // Apri il flusso audio
            clip.open(audioInputStream);

            // Riproduci l'audio
            clip.start();
            
            //loop audio
            setLoop(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * metodo per mandare un audio in loop
     * @param value
     */
    public static void setLoop(boolean value)
    {
    	if(value)
    		clip.loop(Clip.LOOP_CONTINUOUSLY);
    	else
    		clip.loop(0);
    }
    /**
     * metodo per fermare un audio
     */
    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * metodo per mettere in pausa un audio
     */
    public static void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * metodo per riprendere un audio interrotto
     */
    public static void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

}