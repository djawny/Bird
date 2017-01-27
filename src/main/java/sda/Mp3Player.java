package sda;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class Mp3Player implements Runnable {
    @Override
    public void run() {
        try{
            FileInputStream fis = new FileInputStream(getClass().getResource("/soundtrack.mp3").getFile());
            Player playMP3 = new Player(fis);
            playMP3.play();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }
}
