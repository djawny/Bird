package sda;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final Dimension SIZE = new Dimension(800, 550);
    private final int OBSTACLE_WIDTH = 75;
    private final int GAP_CHANGE = 50;
    private final int OBSTACLE_GROUND_STEP = 2;
    private final int GAP_HEIGHT = 150;
    private Integer score;
    private Obstacle[] obstacles = new Obstacle[3];
    private Ground[] grounds = new Ground[5];
    private Bird bird;
    private Timer gameTimer;
    private int verticalDirection;
    private boolean gameOver;
    private BufferedImage backgroundImg;
    private BufferedImage birdImg;
    private BufferedImage topTubeImg;
    private BufferedImage bottomTubeImg;
    private BufferedImage groundImg;

    public GamePanel() {
        gameTimer = new Timer(30, this);
        try {
            getResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
        restart();
    }

    public Dimension getSIZE() {
        return SIZE;
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Bird getBird() {
        return bird;
    }

    public void setVerticalDirection(int verticalDirection) {
        this.verticalDirection = verticalDirection;
    }

    private void getResources() throws IOException {
        backgroundImg = ImageIO.read(getClass().getResourceAsStream("/bg.png"));
        birdImg = ImageIO.read(getClass().getResourceAsStream("/angry.png"));
        topTubeImg = ImageIO.read(getClass().getResourceAsStream("/tube1a.jpg"));
        bottomTubeImg = ImageIO.read(getClass().getResourceAsStream("/tube2a.jpg"));
        groundImg = ImageIO.read(getClass().getResourceAsStream("/ground.png"));
    }

    public void restart() {
        score = 0;
        gameOver = false;
        bird = new Bird(150, 275, 50, 3, 350);
        int quarterWidth = (int) (SIZE.getWidth() / 4);
        for (int i = 0; i < grounds.length; i++) {
            grounds[i] = new Ground(i * quarterWidth, (int) SIZE.getHeight() - 50, quarterWidth, 50);
        }
        Random random = new Random();
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Obstacle((int) SIZE.getWidth() + i * 300, 0, OBSTACLE_WIDTH
                    , (int) SIZE.getHeight() - grounds[0].getHeight(), random.nextInt(350), GAP_HEIGHT);
        }
        verticalDirection = -1;
        gameTimer.setDelay(30);
        gameTimer.start();
    }

    private void moveBird() {
        if (bird.getY() <= bird.getYLimit()) {
            verticalDirection = 1;
            bird.setStep(3);
        }
        if (bird.getY() < 0) {
            verticalDirection = 1;
            bird.setStep(3);
        }
        bird.setY(bird.getY() + verticalDirection * bird.getStep());
        if (bird.getY() >= SIZE.getHeight() - grounds[0].getHeight() - bird.getDiameter()) {
            gameOver = true;
        }
    }

    private void moveObstacle(Obstacle obstacle) {
        if (obstacle.getX() - bird.getX() < OBSTACLE_GROUND_STEP + 1 && obstacle.getX() - bird.getX() > 0) {
            score++;
            if (score % 5 == 0 && gameTimer.getDelay() >= 2) {
                gameTimer.setDelay(gameTimer.getDelay() - 2);
            }
        }
        if (obstacle.getX() < -OBSTACLE_WIDTH) {
            int sign;
            if (obstacle.getGapY() <= obstacle.getGapHeight()) {
                sign = 1;
            } else if (obstacle.getGapY() >= SIZE.getHeight() - obstacle.getGapHeight() - GAP_CHANGE - grounds[0].getHeight()) {
                sign = -1;
            } else {
                Random random = new Random();
                sign = (random.nextInt(2) == 0 ? 1 : -1);
            }
            obstacle.setX((int) SIZE.getWidth());
            obstacle.setGapY(obstacle.getGapY() + GAP_CHANGE * sign);
        }
        obstacle.setX(obstacle.getX() - OBSTACLE_GROUND_STEP);
    }

    private void moveGround() {
        for (Ground ground : grounds) {
            if (ground.getX() < -ground.getWidth()) {
                ground.setX((int) SIZE.getWidth());
            }
            ground.setX(ground.getX() - OBSTACLE_GROUND_STEP);
        }
    }

    private void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImg, 0, 0, (int) SIZE.getWidth(), (int) SIZE.getHeight(), this);
    }

    private void drawBird(Graphics2D g2) {
        g2.drawImage(birdImg, bird.getX(), bird.getY(), bird.getDiameter(), bird.getDiameter(), this);
    }

    private void drawObstacles(Graphics2D g2) {
        for (Obstacle obstacle : obstacles) {
            g2.drawImage(topTubeImg, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getGapY(), this);
            g2.drawImage(bottomTubeImg, obstacle.getX(), obstacle.getGapY() + obstacle.getGapHeight(), obstacle.getWidth()
                    , obstacle.getHeight() - (obstacle.getGapY() + obstacle.getGapHeight()), this);
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("Score: " + score.toString(), 700, 50);
    }

    private void drawGround(Graphics g2) {
        for (Ground ground : grounds) {
            g2.drawImage(groundImg, ground.getX(), ground.getY(), ground.getWidth() + 2, ground.getHeight(), this);
        }
    }

    private void drawGameOver(Graphics2D g2) {
        if (gameOver) {
            g2.setColor(Color.MAGENTA);
            g2.setFont(new Font("SansSerif", Font.BOLD, 100));
            g2.drawString("GAME OVER", 100, 260);
            g2.setFont(new Font("SansSerif", Font.BOLD, 40));
            g2.drawString("Press ESC to start again.", 200, 330);
            gameTimer.stop();
        }
    }

    private boolean checkCollision(Obstacle obstacle) {
        return bird.getX() + bird.getDiameter() > obstacle.getX()
                && bird.getX() + bird.getDiameter() < obstacle.getX() + obstacle.getWidth()
                && (bird.getY() < obstacle.getGapY()
                || bird.getY() + bird.getDiameter() > obstacle.getGapY() + obstacle.getGapHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBackground(g2);
        drawObstacles(g2);
        drawBird(g2);
        drawScore(g2);
        drawGround(g2);
        drawGameOver(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBird();
        for (Obstacle obstacle : obstacles) {
            moveObstacle(obstacle);
            if (checkCollision(obstacle)) {
                gameOver = true;
            }
        }
        moveGround();
        repaint();
    }
}
