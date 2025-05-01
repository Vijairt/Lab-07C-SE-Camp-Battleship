import java.util.ArrayList;

public class Ship {
    private ArrayList<Cell> cells;

    public Ship() {
        cells = new ArrayList<>();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public boolean isSunk() {
        for (Cell c : cells) {
            if (!c.isHit()) return false;
        }
        return true;
    }

    public int size() {
        return cells.size();
    }
}
