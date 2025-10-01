package Project;

class ExpandPaddlePowerUp extends PowerUp {
    private float extraWidth;

    public ExpandPaddlePowerUp(float x, float y, float width, float height, int duration) {
        super(x, y, width, height, "ExpandPaddle", duration);
    }

    @Override
    public void applyEffect(GameManager game) {
        game.getPaddle().width += extraWidth;
    }

    @Override
    public void removeEffect(GameManager game) {
        game.getPaddle().width -= extraWidth;
    }
}
