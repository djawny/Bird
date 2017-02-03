package sda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    private GamePanel gamePanel;

    public GameWindow() {
        SwingUtilities.invokeLater(() -> {
            Thread mp3Player = new Thread(new Mp3Player());
            mp3Player.start();
            setTitle("Flappy Angry Bird");
            setSize(new Dimension(830, 600));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLayout(new FlowLayout());
            gamePanel = new GamePanel();
            gamePanel.setPreferredSize(gamePanel.SIZE);
            gamePanel.setBackground(Color.cyan);
            add(gamePanel);
            setVisible(true);
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamePanel.verticalDirection = -1;
            gamePanel.bird.setYLimit(gamePanel.bird.getY() - 40);
            gamePanel.bird.setStep(25);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gamePanel.gameOver) {
            gamePanel.restart();
        }
        if (e.getKeyCode() == KeyEvent.VK_EQUALS || e.getKeyCode() == KeyEvent.VK_ADD) {
            gamePanel.gameTimer.setDelay(gamePanel.gameTimer.getDelay() + 1);
        }
        if ((e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT)
                && gamePanel.gameTimer.getDelay() > 0) {
            gamePanel.gameTimer.setDelay(gamePanel.gameTimer.getDelay() - 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

