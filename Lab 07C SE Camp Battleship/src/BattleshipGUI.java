import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleshipGUI extends JFrame {
    private JButton[][] buttons = new JButton[10][10];
    private JLabel missLabel, strikeLabel, totalMissLabel, totalHitLabel;
    private JButton playAgainBtn, quitBtn;

    private GameBoard board;
    private int missCounter = 0;
    private int strikeCounter = 0;
    private int totalMiss = 0;
    private int totalHit = 0;

    public BattleshipGUI() {
        board = new GameBoard();
        setTitle("Battleship");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        Font font = new Font("Arial", Font.BOLD, 12);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton("~");
                btn.setFont(font);
                int row = i, col = j;
                btn.addActionListener(e -> handleClick(row, col));
                buttons[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        JPanel statusPanel = new JPanel(new GridLayout(2, 2));
        missLabel = new JLabel("Misses: 0");
        strikeLabel = new JLabel("Strikes: 0");
        totalMissLabel = new JLabel("Total Misses: 0");
        totalHitLabel = new JLabel("Total Hits: 0");
        statusPanel.add(missLabel);
        statusPanel.add(strikeLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);

        JPanel controlPanel = new JPanel();
        playAgainBtn = new JButton("Play Again");
        quitBtn = new JButton("Quit");

        playAgainBtn.addActionListener(e -> resetGame());
        quitBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?");
            if (choice == JOptionPane.YES_OPTION) System.exit(0);
        });

        controlPanel.add(playAgainBtn);
        controlPanel.add(quitBtn);

        add(gridPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleClick(int row, int col) {
        JButton btn = buttons[row][col];
        Cell cell = board.getCell(row, col);

        if (cell.isHit()) return;

        cell.hit();
        btn.setEnabled(false);

        if (cell.hasShip()) {
            btn.setText("X");
            btn.setForeground(Color.RED);
            totalHit++;
            missCounter = 0;
            checkShipSunk();
            if (board.allShipsSunk()) {
                JOptionPane.showMessageDialog(this, "You won!");
                if (JOptionPane.showConfirmDialog(this, "Play again?") == JOptionPane.YES_OPTION)
                    resetGame();
            }
        } else {
            btn.setText("M");
            btn.setForeground(Color.ORANGE);
            totalMiss++;
            missCounter++;
            if (missCounter == 5) {
                strikeCounter++;
                missCounter = 0;
                if (strikeCounter == 3) {
                    JOptionPane.showMessageDialog(this, "You lost!");
                    if (JOptionPane.showConfirmDialog(this, "Play again?") == JOptionPane.YES_OPTION)
                        resetGame();
                }
            }
        }

        updateLabels();
    }

    private void updateLabels() {
        missLabel.setText("Misses: " + missCounter);
        strikeLabel.setText("Strikes: " + strikeCounter);
        totalMissLabel.setText("Total Misses: " + totalMiss);
        totalHitLabel.setText("Total Hits: " + totalHit);
    }

    private void checkShipSunk() {
        for (Ship s : board.getShips()) {
            if (s.isSunk()) {
                JOptionPane.showMessageDialog(this, "You sunk a ship of size " + s.size() + "!");
            }
        }
    }

    private void resetGame() {
        dispose();
        new BattleshipGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleshipGUI::new);
    }
}
