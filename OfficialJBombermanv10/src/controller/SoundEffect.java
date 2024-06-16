package controller;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Classe per la riproduzine degli effetti sonori
 * @author Benjamin Ruff & Mattia Pandolfi
 *
 */
public class SoundEffect {
	private static Clip clip;

    /**
     * riproduce l'audio.wav passato come parametro
     * @param audioFilePath path del file.wav
     */
    public void play(String audioFilePath) {
        try {

            // Apri il file audio
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundEffect.class.getResource(audioFilePath));

            // Ottieni il formato audio del file
            AudioFormat format = audioInputStream.getFormat();

            // Crea una nuova clip audio
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);

            // Apri il flusso audio
            clip.open(audioInputStream);

            // Riproduci l'audio
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ferma l'audio
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * mette in pausa l'audio
     */
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * riprende la riproduzione dell'audio
     */
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }
}
