import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    private Cell[][] board;
    private ArrayList<Ship> ships;

    public GameBoard() {
        board = new Cell[10][10];
        ships = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                board[i][j] = new Cell();

        placeShips();
    }

    private void placeShips() {
        int[] shipSizes = {5, 4, 3, 3, 2};
        Random rand = new Random();

        for (int size : shipSizes) {
            boolean placed = false;
            while (!placed) {
                boolean vertical = rand.nextBoolean();
                int row = rand.nextInt(10);
                int col = rand.nextInt(10);
                if (canPlaceShip(row, col, size, vertical)) {
                    Ship ship = new Ship();
                    for (int i = 0; i < size; i++) {
                        Cell cell = vertical ? board[row + i][col] : board[row][col + i];
                        cell.placeShip();
                        ship.addCell(cell);
                    }
                    ships.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean vertical) {
        if (vertical && row + size > 10) return false;
        if (!vertical && col + size > 10) return false;

        for (int i = 0; i < size; i++) {
            Cell cell = vertical ? board[row + i][col] : board[row][col + i];
            if (cell.hasShip()) return false;
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) return false;
        }
        return true;
    }
}
