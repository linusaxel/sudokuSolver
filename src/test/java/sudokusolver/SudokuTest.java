package sudokusolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    private static final String pathInvalidPuzzle1 = "src/main/resources/InvalidPuzzleRepeatingDigitsFirstColumn.txt";
    private static final String pathInvalidPuzzle2 = "src/main/resources/InvalidPuzzleRepeatingDigitsFirstColumn.txt";
    private static final String pathInvalidPuzzle3 = "src/main/resources/InvalidPuzzleRepeatingDigitsFirstColumn.txt";
    private static final String pathInvalidPuzzle4 = "src/main/resources/InvalidPuzzleRepeatingDigitsFirstColumn.txt";
    private static final String pathInvalidPuzzle5 = "src/main/resources/InvalidPuzzleRepeatingDigitsFirstColumn.txt";
    private static final String pathInvalidPuzzle6 = "src/main/resources/InvalidPuzzleTooShort.txt";
    private static final String pathInvalidPuzzle7 = "src/main/resources/InvalidPuzzleWithLetter.txt";
    private static final String pathUnsolvedPuzzle = "src/main/resources/Puzzle1.txt";
    private static final String pathSolvedPuzzle = "src/main/resources/SolvedPuzzle1.txt";

    @DisplayName("Should throw exception on invalid sudoku")
    @ParameterizedTest
    @ValueSource(strings = { pathInvalidPuzzle1, pathInvalidPuzzle2, pathInvalidPuzzle3, pathInvalidPuzzle4, pathInvalidPuzzle5, pathInvalidPuzzle6, pathInvalidPuzzle7 })
    void illegal_sudoku(String path) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Sudoku().load(path),
                "Invalid sudoku"
        );
    }

    @Test
    @DisplayName("Should solve sudoku correctly")
    void sudoku_solves_correctly() {
        Sudoku sudoku = new Sudoku();
        sudoku.load(pathUnsolvedPuzzle);
        sudoku.solve();
        SudokuBoard solution = sudoku.getSolvedBoard();

        Sudoku solvedSudoku = new Sudoku();
        solvedSudoku.load(pathSolvedPuzzle);
        SudokuBoard solved = solvedSudoku.getBoard();

        assertTrue(solution.equals(solved));
    }

}