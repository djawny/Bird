package sda;

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

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public void setGapHeight(int gapHeight) {
        this.gapHeight = gapHeight;
    }
}
