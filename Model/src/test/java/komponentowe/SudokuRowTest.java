package komponentowe;

import komponentowe.exception.ImposibleCloneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    @Test
    @DisplayName("Check If clone method for row works correctly")
    public void TestCloneRow() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        SudokuRow row = new SudokuRow(List.of(board.getRow(0)));
        SudokuRow copiedRow = (SudokuRow) row.clone();

        assertNotSame(row, copiedRow);
    }

    @Test
    public void TestCloneRowCheck() throws CloneNotSupportedException {
        List<SudokuField> fields = List.of();
        SudokuRow row = new SudokuRow(fields);
        SudokuRow copiedRow = (SudokuRow) row.clone();
        assertNotNull(copiedRow);
    }

}