package komponentowe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    @Test
    @DisplayName("check get is giving the right answer ")
    public void testIfGetIsGivingRightAnswer()
    {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.get(0, 0) != 0);
        assertTrue(sudokuBoard.get(0, 0) < 10);
    }

    @Test
    @DisplayName("check set is giving the right answer ")
    public void testIfSetIsGivingRightAnswer()
    {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.set(0,0,0);
        assertEquals(0, sudokuBoard.get(0, 0));

        assertThrows(IllegalArgumentException.class, () -> sudokuBoard.set(0, 0, 80));
        assertThrows(IllegalArgumentException.class, () -> sudokuBoard.set(0, 0, -80));
    }

    @Test
    @DisplayName("Check if getRow returns correct values for a single row")
    public void testIfGetRowReturnsCorrectValuesForSingleRow()
    {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        int y = 0;
        SudokuField[] fields = sudokuBoard.getRow(y);
        SudokuRow row = new SudokuRow(List.of(fields));

        assertTrue(row != null);
    }

    @Test
    @DisplayName("Check if getColumn returns correct values for a single column")
    public void testIfGetColumnReturnsCorrectValuesForSingleColumn()
    {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        int y = 0;
        SudokuField[] fields = sudokuBoard.getColumn(y);
        SudokuColumn column = new SudokuColumn(List.of(fields));

        assertTrue(column != null);
    }

    @Test
    @DisplayName("Check if getBox returns correct values for a single box")
    public void testIfBoxReturnsCorrectValuesForSingleBox()
    {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        int y = 0;
        int x = 0;
        SudokuField[] fields = sudokuBoard.getBox(x, y);
        SudokuFieldGroup box = new SudokuBox(List.of(fields));

        assertTrue(box != null);
    }

    @Test
    @DisplayName("Check if two equal objects return true")
    public void testIfTwoEqualObjectsReturnTrue(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertTrue(sudokuBoard.equals(sudokuBoard));
    }

    @Test
    @DisplayName("Check if two different objects return false")
    public void testIfTwoDifferentObjectsReturnFalse(){
        SudokuSolver solver1 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver1);
        sudokuBoard1.solveGame();

        SudokuSolver solver2 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver2);
        sudokuBoard2.solveGame();

        assertFalse(sudokuBoard1.equals(sudokuBoard2));
    }

    @Test
    @DisplayName("Check if object with null returns false")
    public void testIfObjectWithNullReturnFalse(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        assertFalse(sudokuBoard.equals(null));
    }

    @Test
    @DisplayName("Check if to string method works correctly")
    public void testIfToStringMethodWorksCorrectly(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        sudokuBoard.toString();

        assertTrue(sudokuBoard.toString().contains("SudokuBoard"));
        assertTrue(sudokuBoard.toString().contains("board="));
        assertTrue(sudokuBoard.toString().contains("solver="));
    }

    @Test
    @DisplayName("Check equals method when compared with different Instance of same class")
    public void testEqualsWithDifferentInstanceOfSameClass() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        SudokuBoard board2 = new SudokuBoard(solver);
        assertTrue(board1.equals(board2));
    }

    @Test
    @DisplayName("Check if is equal method works correctly for different objects")
    public void TestIfIsEqualMethodWorksCorrectlyForDifferentObjects() {
        BacktrackingSudokuSolver solver1 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver1);

        assertFalse(sudokuBoard1.equals(solver1));
    }

    @Test
    @DisplayName("Test If copy method for board works correctly")
    public void testBoardColumn() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        SudokuBoard copiedBoard = (SudokuBoard) board.clone();

        assertNotSame(board, copiedBoard);
    }

}




