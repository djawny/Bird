package sda;

public class Bird {
    private int x;
    private int y;
    private int diameter;
    private int step;
    private int yLimit;

    public Bird(int x, int y, int diameter, int step, int yLimit) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.step = step;
        this.yLimit = yLimit;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getYLimit() {
        return yLimit;
    }

    public void setYLimit(int yLimit) {
        this.yLimit = yLimit;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDiameter() {
        return diameter;
    }
}
