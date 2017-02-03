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
        createAndAddGamePanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        addKeyListener(this);
    }

    private void createAndAddGamePanel() {
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(gamePanel.getPANEL_SIZE());
        gamePanel.setBackground(Color.cyan);
        add(gamePanel);
    }

    private void speedUpGameOnPlusPress(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT)
                && gamePanel.getGameTimer().getDelay() > 0) {
            gamePanel.getGameTimer().setDelay(gamePanel.getGameTimer().getDelay() - 1);
        }
    }

    private void slowDownGameOnMinusPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_EQUALS || e.getKeyCode() == KeyEvent.VK_ADD) {
            gamePanel.getGameTimer().setDelay(gamePanel.getGameTimer().getDelay() + 1);
        }
    }

    private void restartGameOnEscPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && gamePanel.isGameOver()) {
            gamePanel.restart();
        }
    }

    private void birdJumpOnSpacePress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            gamePanel.setVerticalDirection(-1);
            gamePanel.getBird().setYLimit(gamePanel.getBird().getY() - 40);
            gamePanel.getBird().setStep(25);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        birdJumpOnSpacePress(e);
        restartGameOnEscPress(e);
        slowDownGameOnMinusPress(e);
        speedUpGameOnPlusPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}

