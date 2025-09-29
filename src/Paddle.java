package Project;

class Paddle extends MovableObject {
    private float speed;
    private PowerUp currentPowerUp;

    public Paddle(float x, float y, float width, float height, float dx, float dy, float speed) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.currentPowerUp = null;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void applyPowerUp(PowerUp powerUp, GameManager game) {
        this.currentPowerUp = powerUp;
        powerUp.applyEffect(game);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render() {

    }
}
