package sudokusolver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku implements Puzzle {

    public SudokuBoard getBoard() {
        return board;
    }

    private SudokuBoard board;
    private SudokuBoard solvedBoard;
    private static final int MAX_SIZE = 9;


    public SudokuBoard getSolvedBoard() {
        return solvedBoard;
    }

    @Override
    public boolean isSolved() {
        return solvedBoard.getEmptySquares().size() == 0;
    }

    @Override
    public boolean isValid() {
        for (int i = 0; i < 9; i++) {
            //Row constraint
            Square[] row = board.getRow(i);
            if (!hasUniqueValues(filterOutEmptySquares(row))) {
                return false;
            }
            //Column constraint
            Square[] column = board.getColumn(i);
            if (!hasUniqueValues(filterOutEmptySquares(column))) {
                return false;
            }
            //Sub square constraint
            Square[] subSquare = board.getSubSquare(SudokuBoard.getCentreSquares().get(i)[0], SudokuBoard.getCentreSquares().get(i)[1]);
            if (!hasUniqueValues(filterOutEmptySquares(subSquare))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Sudoku solve() {
        solvedBoard = board;
        if (solveSuduko(0, 0)) {
            System.out.println("Solved! Solution:");
            solvedBoard.printBoard();
        }
        return this;
    }

    public boolean solveSuduko(int x, int y) {
        if (x == MAX_SIZE - 1 && y == MAX_SIZE) {
            return true;
        }

        if (y == MAX_SIZE) {
            x++;
            y = 0;
        }

        if (solvedBoard.getBoard()[x][y].getValue() != '.')
            return solveSuduko(x, y + 1);

        for (int num = 1; num < 10; num++) {
            solvedBoard.getBoard()[x][y].setValue(Character.forDigit(num, 10));
            if (isValid()) {
                if (solveSuduko(x, y + 1))
                    return true;
            }
            solvedBoard.getBoard()[x][y].setValue('.');
        }
        return false;
    }


    @Override
    public void load(String path) {
        String fileContent = "";
        try {
            fileContent = Files.readString(Path.of(path), StandardCharsets.US_ASCII);
        } catch (IOException e) {
            System.out.println("Couldn't read file.");
        }

        checkSudokuRegex(fileContent);

        board = new SudokuBoard(fileContent);

        if (!isValid()) {
            throw new IllegalArgumentException("Invalid sudoku");
        }

        System.out.println("Loaded sudoku board:");
        board.printBoard();
    }

    @Override
    public void save(String path) {
        FileWriter fw;
        try {
            fw = new FileWriter(path);
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    fw.write(solvedBoard.getBoard()[x][y].getValue());
                }
                if (x != 8) fw.write("\n");
            }
            fw.close();
            System.out.printf("File saved at %s%n", path);
        } catch (IOException e) {
            System.out.println("Couldn't save file.");
            e.printStackTrace();
        }
    }

    private static final String LINE = "[1-9.]{9}";
    private static final String NEW_LINE = "\\n";
    private static final String VALID_SUDOKU_CHARACTERS_AND_LENGTH = LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE + NEW_LINE +
            LINE;

    public static boolean hasUniqueValues(Square[] squares) {
        Set<Character> set = new HashSet<>();
        for (Square square : squares) {
            if (!set.add(square.getValue())) {
                return false;
            }
        }
        return true;
    }

    static Square[] filterOutEmptySquares(Square[] row) {
        List<Square> squares = new ArrayList<>();
        for (Square square : row) {
            if (square.getValue() != '.') {
                squares.add(square);
            }
        }
        return squares.toArray(new Square[squares.size()]);
    }

    public static void checkSudokuRegex(String fileContent) {
        if (!fileContent.matches(VALID_SUDOKU_CHARACTERS_AND_LENGTH)) {
            throw new IllegalArgumentException("Invalid sudoku");
        }
    }
}
