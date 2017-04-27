package sda.ui;

import sda.model.Bird;
import sda.model.Ground;
import sda.model.Obstacle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GamePanel extends JPanel implements ActionListener {
    private static final Dimension PANEL_SIZE = new Dimension(800, 550);
    private static final int INIT_GAME_TIMER_DELAY = 30;
    private static final int OBSTACLE_WIDTH = 75;
    private static final int GAP_CHANGE = 50;
    private static final int OBSTACLE_GROUND_STEP = 2;
    private static final int GAP_HEIGHT = 150;
    private BufferedImage backgroundImg;
    private BufferedImage birdImg;
    private BufferedImage topTubeImg;
    private BufferedImage bottomTubeImg;
    private BufferedImage groundImg;
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Ground> grounds = new ArrayList<>();
    private Integer score;
    private Bird bird;
    private Timer gameTimer;
    private int verticalDirection;
    private boolean gameOver;

    public GamePanel() {
        gameTimer = new Timer(30, this);
        try {
            getResources();
        } catch (IOException e) {
            System.out.println("Blad wczytania danych");
        }
        restart();
    }

    public Dimension getPANEL_SIZE() {
        return PANEL_SIZE;
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
        gameValuablesInit();
        birdInit();
        groundsInit();
        obstaclesInit();
    }

    private void gameValuablesInit() {
        score = 0;
        gameOver = false;
        verticalDirection = -1;
        gameTimer.setDelay(INIT_GAME_TIMER_DELAY);
        gameTimer.start();
    }

    private void birdInit() {
        bird = new Bird(150, 275, 50, 3, 350);
    }

    private void obstaclesInit() {
        Random random = new Random();
        obstacles.clear();
        IntStream.range(0, 3).forEach(i -> obstacles.add(new Obstacle((int) PANEL_SIZE.getWidth() + i * 300,
                0, OBSTACLE_WIDTH, (int) PANEL_SIZE.getHeight() - grounds.get(0).getHeight(),
                random.nextInt(350), GAP_HEIGHT)));
    }

    private void groundsInit() {
        int quarterWidth = (int) (PANEL_SIZE.getWidth() / 4);
        grounds.clear();
        IntStream.range(0, 5).forEach(i -> grounds.add(new Ground(i * quarterWidth,
                (int) PANEL_SIZE.getHeight() - 50, quarterWidth, 50)));
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
        if (bird.getY() >= PANEL_SIZE.getHeight() - grounds.get(0).getHeight() - bird.getDiameter()) {
            gameOver = true;
        }
    }

    private void moveObstacles() {
        obstacles.forEach(obstacle -> {
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
                        } else if (obstacle.getGapY() >= PANEL_SIZE.getHeight() - obstacle.getGapHeight() -
                                GAP_CHANGE - grounds.get(0).getHeight()) {
                            sign = -1;
                        } else {
                            Random random = new Random();
                            sign = (random.nextInt(2) == 0 ? 1 : -1);
                        }
                        obstacle.setX((int) PANEL_SIZE.getWidth());
                        obstacle.setGapY(obstacle.getGapY() + GAP_CHANGE * sign);
                    }
                    obstacle.setX(obstacle.getX() - OBSTACLE_GROUND_STEP);
                    if (checkCollision(obstacle)) {
                        gameOver = true;
                    }
                }
        );
    }

    private void moveGrounds() {
        grounds.forEach(ground -> {
            if (ground.getX() < -ground.getWidth()) {
                ground.setX((int) PANEL_SIZE.getWidth());
            }
            ground.setX(ground.getX() - OBSTACLE_GROUND_STEP);
        });
    }

    private void drawBackground(Graphics2D g2) {
        g2.drawImage(backgroundImg, 0, 0, (int) PANEL_SIZE.getWidth(), (int) PANEL_SIZE.getHeight(), this);
    }

    private void drawBird(Graphics2D g2) {
        g2.drawImage(birdImg, bird.getX(), bird.getY(), bird.getDiameter(), bird.getDiameter(), this);
    }

    private void drawObstacles(Graphics2D g2) {
        obstacles.forEach(obstacle -> {
            g2.drawImage(topTubeImg, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getGapY(), this);
            g2.drawImage(bottomTubeImg, obstacle.getX(), obstacle.getGapY() + obstacle.getGapHeight(),
                    obstacle.getWidth(), obstacle.getHeight() - (obstacle.getGapY() + obstacle.getGapHeight()), this);
        });
    }

    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("Score: " + score.toString(), 700, 50);
    }

    private void drawGround(Graphics g2) {
        grounds.forEach(ground -> g2.drawImage(groundImg, ground.getX(), ground.getY(), ground.getWidth() + 2,
                ground.getHeight(), this));
    }

    private void drawGameOverMessage(Graphics2D g2) {
        if (gameOver) {
            g2.setColor(Color.MAGENTA);
            g2.setFont(new Font("SansSerif", Font.BOLD, 100));
            g2.drawString("GAME OVER", 100, 260);
            g2.setFont(new Font("SansSerif", Font.BOLD, 40));
            g2.drawString("Press ESC to start again.", 180, 330);
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
        drawGameOverMessage(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBird();
        moveObstacles();
        moveGrounds();
        repaint();
    }
}
