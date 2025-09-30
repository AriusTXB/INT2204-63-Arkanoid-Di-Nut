package Project;

class NormalBrick extends Brick {
    public NormalBrick(float x, float y, float width, float height) {
        super(x, y, width, height, 1, "Normal");
    }

    @Override
    public void takeHit() {
        hitPoints = 0;
    }
}
