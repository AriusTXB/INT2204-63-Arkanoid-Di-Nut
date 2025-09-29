package Project;

abstract class MovableObject extends GameObject {
    protected float dx, dy;

    public MovableObject(float x, float y, float width, float height, float dx, float dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;
    }
}

