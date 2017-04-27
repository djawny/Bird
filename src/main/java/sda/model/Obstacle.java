package sda.model;

public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int gapY;
    private int gapHeight;

    public Obstacle(int x, int y, int width, int height, int gapY, int gapHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGapY() {
        return gapY;
    }

    public void setGapY(int gapY) {
        this.gapY = gapY;
    }

    public int getGapHeight() {
        return gapHeight;
    }
}
