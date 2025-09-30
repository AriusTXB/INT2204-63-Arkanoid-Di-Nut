package Project;

class StrongBrick extends Brick {
    public StrongBrick(float x, float y, float width, float height, int hitPoints) {
        super(x, y, width, height, hitPoints, "Strong");
    }

    @Override
    public void takeHit() {
        hitPoints--;
    }
}
