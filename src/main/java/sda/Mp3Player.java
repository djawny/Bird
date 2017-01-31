package sda;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class Mp3Player implements Runnable {
    @Override
    public void run() {
        try{
            InputStream inputStream = getClass().getResourceAsStream("/soundtrack.mp3");
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            Player playMP3 = new Player(bis);
            playMP3.play();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }
}
