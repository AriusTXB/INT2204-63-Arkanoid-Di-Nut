package Project;

abstract class PowerUp extends GameObject {
    protected String type;
    protected int duration;

    public PowerUp(float x, float y, float width, float height, String type, int duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    public abstract void applyEffect(GameManager game);
    public abstract void removeEffect(GameManager game);

    @Override
    public void update() {
        y += 1;
    }

    @Override
    public void render() {

    }
}

