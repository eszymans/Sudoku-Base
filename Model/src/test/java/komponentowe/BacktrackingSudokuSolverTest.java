package komponentowe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    boolean checkRow(SudokuBoard sudokuBoard) {
        int tile;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                tile = sudokuBoard.get(x, y);

                for (int i = x + 1; i < 9; i++) {
                    if (tile == sudokuBoard.get(i, y)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean checkColumn(SudokuBoard sudokuBoard) {
        int tile;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                tile = sudokuBoard.get(x, y);

                for (int i = y + 1; i < 9; i++) {
                    if(tile == sudokuBoard.get(x, i)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    boolean checkBox3x3(SudokuBoard sudokuBoard) {
        int tile;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                tile = sudokuBoard.get(x, y);

                int rowStart = (x / 3) * 3;
                int colStart = (y / 3) * 3;

                for (int i = rowStart; i < rowStart + 3; i++) {
                    for (int j = colStart; j < colStart + 3; j++) {
                        if (!(i == x && j == y)) {
                            if(tile == sudokuBoard.get(i, j)){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Test
    @DisplayName("check if sudoku is correct ")
    public void testIfSudokuIsCorrect()
    {
        int tile;
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        assertTrue(checkRow(sudokuBoard));
        assertTrue(checkColumn(sudokuBoard));
        assertTrue(checkBox3x3(sudokuBoard));
    }

    @Test
    @DisplayName("check if two sudoku is different ")
    public void testIfTwoSudokuIsDifferent()
    {
        int tile1;
        int tile2;
        int different = 0;
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                tile1 = sudokuBoard1.get(x, y);
                tile2 = sudokuBoard2.get(x, y);

                if (tile1 != tile2) {
                    different++;
                }
            }
        }
        assertTrue(different != 0);
    }

    @Test
    @DisplayName("Check if is equal method works correctly for equal objects")
    public void TestIfIsEqualMethodWorksCorrectlyForEqualObjects() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();

        assertTrue(solver1.equals(solver1));
    }

    @Test
    @DisplayName("Check if is equal method works correctly for different objects")
    public void TestIfIsEqualMethodWorksCorrectlyForDifferentObjects() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver1);

        assertFalse(solver1.equals(sudokuBoard1));
    }

    @Test
    @DisplayName("Check equals method when compared with different Instance of same class")
    public void testEqualsWithDifferentInstanceOfSameClass() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();
        BacktrackingSudokuSolver solver2 = new BacktrackingSudokuSolver();
        assertTrue(solver1.equals(solver2));
    }

    @Test
    @DisplayName("Check equals method when compared with null")
    public void testEqualsWithNull() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();

        assertFalse(solver.equals(null));
    }

}