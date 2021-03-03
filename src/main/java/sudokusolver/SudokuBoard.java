package sudokusolver;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoard implements Board {

    private final Square[][] board;

    private static final List<int[]> centreSquares = new ArrayList<>();
    static {
        centreSquares.add(new int[]{1, 1});
        centreSquares.add(new int[]{1, 4});
        centreSquares.add(new int[]{1, 7});
        centreSquares.add(new int[]{4, 1});
        centreSquares.add(new int[]{4, 4});
        centreSquares.add(new int[]{4, 7});
        centreSquares.add(new int[]{7, 1});
        centreSquares.add(new int[]{7, 4});
        centreSquares.add(new int[]{7, 7});
    }

    public static List<int[]> getCentreSquares() {
        return centreSquares;
    }

    public SudokuBoard(String board) {
        this.board = new Square[9][9];
        String[] splitContent = board.split("\\n");
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                this.board[x][y] = new Square(x, y, splitContent[x].charAt(y));
            }
        }
    }

    public Square[][] getBoard() {
        return board;
    }

    public void printBoard() {
        System.out.println("==========");
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(board[x][y].getValue());
            }
            System.out.println();
        }
        System.out.println("==========");
    }

    public Square[] getSubSquare(int xCoordinate, int yCoordinate) {
        int[] centreSquareCoordinates = findNearestCentreSquare(xCoordinate, yCoordinate);
        assert centreSquareCoordinates != null;
        return getSurroundingSubSquareFromSquare(centreSquareCoordinates[0], centreSquareCoordinates[1]);
    }

    private int[] findNearestCentreSquare(int x, int y) {
        for (int[] centreSquare : centreSquares) {
            int xOffset = Math.abs(x - centreSquare[0]);
            int yOffset = Math.abs(y - centreSquare[1]);
            if (xOffset + yOffset <= 2) {
                return centreSquare;
            }
        }
        return null;
    }

    private Square[] getSurroundingSubSquareFromSquare(int x, int y) {
        return new Square[]{board[x - 1][y - 1], board[x - 1][y], board[x - 1][y + 1],
                board[x][y - 1], board[x][y], board[x][y + 1],
                board[x + 1][y - 1], board[x + 1][y], board[x + 1][y + 1]};
    }

    public Square[] getRow(int x) {
        return board[x];
    }

    public Square[] getColumn(int y) {
        Square [] column = new Square[9];
        for (int x = 0; x < 9; x++) {
            column[x] = board[x][y];
        }
        return column;
    }

    public List<Square> getEmptySquares() {
        List<Square> squares = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (this.board[x][y].getValue() != '.') {
                    squares.add(board[x][y]);
                }
            }
        }
        return squares;
    }

    public boolean equals(SudokuBoard board) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y <9; y++) {
                if (this.board[x][y].getValue() != board.getBoard()[x][y].getValue())
                return false;
            }
        }
        return true;
    }
}
