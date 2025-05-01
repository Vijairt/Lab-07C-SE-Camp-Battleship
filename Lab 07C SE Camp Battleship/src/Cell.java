public class Cell {
    private boolean hasShip;
    private boolean isHit;

    public Cell() {
        this.hasShip = false;
        this.isHit = false;
    }

    public boolean hasShip() {
        return hasShip;
    }

    public void placeShip() {
        this.hasShip = true;
    }

    public boolean isHit() {
        return isHit;
    }

    public void hit() {
        this.isHit = true;
    }
}
