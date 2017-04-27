package sda;

import sda.runnable.Mp3Player;
import sda.ui.GameWindow;

import javax.swing.*;

public class GameMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            playSoundTrack();
            new GameWindow();
        });
    }

    private static void playSoundTrack() {
        Thread mp3Player = new Thread(new Mp3Player());
        mp3Player.start();
    }
}
