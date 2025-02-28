package komponentowe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {
    @Test
    @DisplayName("Check If clone method for box works correctly")
    public void TestCloneBox() throws CloneNotSupportedException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        board.solveGame();

        SudokuBox box = new SudokuBox(List.of(board.getBox(0, 0)));
        SudokuBox copiedBox = (SudokuBox) box.clone();

        assertNotSame(box, copiedBox);

    }


}