package sudokusolver.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import sudokusolver.logic.Board;
import sudokusolver.logic.Logic;
import sudokusolver.logic.FillResult;

public class UserInterface implements Runnable {

    private JFrame frame;
    private JTextField[][] cells;
    private JButton buttonSolve;
    private JButton buttonReset;

    private final int CELL_SIZE = 60;
    private final int BLOCK_GAP = 10;
    private final int BUTTON_HEIGHT = 50;
    private final int INSET_TOP = 26;
    private final int INSET_LEFT = 3;
    private final int INSET_RIGHT = 3;
    private final int INSET_BOTTOM = 3;

    public UserInterface() {
        this.cells = new JTextField[9][9];
    }

    @Override
    public void run() {
        frame = new JFrame("Sudoku Solver");
        frame.setPreferredSize(new Dimension(INSET_LEFT + INSET_RIGHT + 9 * CELL_SIZE + 2 * BLOCK_GAP, INSET_TOP + INSET_BOTTOM + 9 * CELL_SIZE + 2 * BLOCK_GAP + BUTTON_HEIGHT));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        presetBoard();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createComponents(Container container) {

        container.setLayout(null);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField();
                //textField.setDocument(new JTextFieldLimit(1));
                textField.setBounds(i * CELL_SIZE + (i / 3) * BLOCK_GAP, j * CELL_SIZE + (j / 3) * BLOCK_GAP, CELL_SIZE, CELL_SIZE);
                textField.setFont(new Font("Arial", Font.PLAIN, 50));
                textField.setHorizontalAlignment(JTextField.CENTER);
                cells[i][j] = textField;
                container.add(textField);
            }
        }

        buttonSolve = new JButton("Solve");
        buttonSolve.setBounds(0, CELL_SIZE * 9 + 2 * BLOCK_GAP, CELL_SIZE * 9 / 2 + BLOCK_GAP, BUTTON_HEIGHT);
        buttonSolve.addActionListener(new MyButtonListener(this, buttonSolve));
        buttonSolve.setFocusPainted(false);
        container.add(buttonSolve);

        buttonReset = new JButton("Reset");
        buttonReset.setBounds(CELL_SIZE * 9 / 2 + BLOCK_GAP, CELL_SIZE * 9 + 2 * BLOCK_GAP, CELL_SIZE * 9 / 2 + BLOCK_GAP, BUTTON_HEIGHT);
        buttonReset.addActionListener(new MyButtonListener(this, buttonReset));
        buttonReset.setFocusPainted(false);
        container.add(buttonReset);

    }

    public int[][] readCells() {

        int[][] readCells = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String cellText = cells[j][i].getText();
                if (cellText.equals("")) {
                    readCells[j][i] = 0;
                } else {
                    readCells[j][i] = Integer.parseInt(cellText);
                }
            }
        }
        return readCells;
    }

    public Board convertReadCellsToBoard() {
        Board board = new Board();
        int[][] readCells = readCells();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int cellValue = readCells[i][j];
                if (cellValue != 0) {
                    board.setFilledValue(i, j, cellValue);
                }
            }
        }
        return board;
    }

    public void solveReadBoard() {
        Board startingBoard = convertReadCellsToBoard();
        System.out.println(convertBoardToString(startingBoard));
        FillResult solvedBoard = new Logic(startingBoard).solve();
        if (solvedBoard.isValidBoard()) {
            displayResult(startingBoard, solvedBoard.getBoard());
        } else {
            System.out.println("Invalid board given");
        }
        buttonSolve.setEnabled(false);
        System.out.println("Solving complete.");
    }

    public JButton getButton(String buttonName) {
        if (buttonName.equals("Solve")) {
            return buttonSolve;
        } else if (buttonName.equals("Reset")) {
            return buttonReset;
        } else {
            return null;
        }
    }

    public JTextField[][] getCells() {
        return cells;
    }

    public String convertBoardToString(Board board) {
        String str = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                str += board.getFilledValue(j, i);
            }
            str += "\n";
        }
        return str;
    }

    public Board convertStringToBoard(String str) {
        Scanner reader = new Scanner(str);
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            String line = reader.nextLine();
            for (int j = 0; j < 9; j++) {
                int value = Character.getNumericValue(line.charAt(j));
                if (value != 0) {
                    board.setFilledValue(j, i, value);
                }
            }
        }
        return board;
    }

    public void displayBoard(Board board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setForeground(Color.black);
                if (board.getFilledValue(i, j) != 0) {
                    // Starting number.
                    cells[i][j].setText(String.valueOf(board.getFilledValue(i, j)));
                } else {
                    cells[i][j].setText("");
                }
            }
        }
    }

    public void displayResult(Board startingBoard, Board solvedBoard) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (startingBoard.getFilledValue(i, j) != 0) {
                    // Starting number.
                    cells[i][j].setForeground(Color.black);
                    cells[i][j].setText(String.valueOf(startingBoard.getFilledValue(i, j)));
                } else {
                    // Solved number.
                    if (solvedBoard.getFilledValue(i, j) != 0) {
                        cells[i][j].setForeground(Color.red);
                        cells[i][j].setText(String.valueOf(solvedBoard.getFilledValue(i, j)));
                    } else {
                        cells[i][j].setForeground(Color.black);
                        cells[i][j].setText("");
                    }
                }
            }
        }
    }

    private void presetBoard() {
        // Harder
        String test1 = "";
        test1 += "120400300" + "\n";
        test1 += "300010050" + "\n";
        test1 += "006000100" + "\n";
        test1 += "700090000" + "\n";
        test1 += "040603000" + "\n";
        test1 += "003002000" + "\n";
        test1 += "500080700" + "\n";
        test1 += "007000005" + "\n";
        test1 += "000000098" + "\n";

        String test2 = "";
        test2 += "080400000" + "\n";
        test2 += "000005670" + "\n";
        test2 += "007600028" + "\n";
        test2 += "800000010" + "\n";
        test2 += "042010590" + "\n";
        test2 += "030000007" + "\n";
        test2 += "470009100" + "\n";
        test2 += "056200000" + "\n";
        test2 += "000006040" + "\n";

        displayBoard(convertStringToBoard(test1));

    }
}
