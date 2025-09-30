package Project;

abstract class Brick extends GameObject {
    protected int hitPoints;
    protected String type;

    public Brick(float x, float y, float width, float height, int hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public abstract void takeHit();

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }
}
