package Project;

class Ball extends MovableObject {
    private float speed;
    private int directionX, directionY;

    public Ball(float x, float y, float radius, float speed, int directionX, int directionY) {
        super(x, y, radius, radius, speed * directionX, speed * directionY);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        this.dx = directionX * speed;
        this.dy = directionY * speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void bounceOff(GameObject other) {

    }

    public boolean checkCollision(GameObject other) {

        return true;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render() {

    }
}
