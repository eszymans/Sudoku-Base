package komponentowe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    @Test
    @DisplayName("Test If copy method for column works correctly")
    public void testCopyColumn() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        SudokuColumn column = new SudokuColumn(List.of(board.getColumn(0)));
        SudokuColumn copiedColumn = (SudokuColumn) column.clone();

        assertNotSame(column, copiedColumn);
    }
}