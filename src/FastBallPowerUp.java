package Project;

class FastBallPowerUp extends PowerUp {
    private float speedBoost;

    public FastBallPowerUp(float x, float y, float width, float height, int duration) {
        super(x, y, width, height, "FastBall", duration);
    }

    @Override
    public void applyEffect(GameManager game) {
        Ball ball = game.getBall();
        ball.setSpeed(ball.getSpeed() + speedBoost);
    }

    @Override
    public void removeEffect(GameManager game) {
        Ball ball = game.getBall();
        ball.setSpeed(ball.getSpeed() - speedBoost);
    }
}