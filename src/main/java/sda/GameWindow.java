package sda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    private GamePanel gamePanel;

    public GameWindow() {
        setTitle("Flappy Angry Bird");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        createGamePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(this);
    }

    private void createGamePanel() {
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(gamePanel.getSIZE());
        gamePanel.setBackground(Color.cyan);
        add(gamePanel);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        birdJump(e);
        restartGame(e);
        addDelay(e);
        subtractDelay(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void subtractDelay(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT)
                && gamePanel.getGameTimer().getDelay() > 0) {
            gamePanel.getGameTimer().setDelay(gamePanel.getGameTimer().getDelay() - 1);
        }
    }

    private void addDelay(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_EQUALS || e.getKeyCode() == KeyEvent.VK_ADD) {
            gamePanel.getGameTimer().setDelay(gamePanel.getGameTimer().getDelay() + 1);
        }
    }

    private void restartGame(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gamePanel.isGameOver()) {
            gamePanel.restart();
        }
    }

    private void birdJump(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamePanel.setVerticalDirection(-1);
            gamePanel.getBird().setYLimit(gamePanel.getBird().getY() - 40);
            gamePanel.getBird().setStep(25);
        }
    }
}

