package komponentowe;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {

    @Override
    public void solve(SudokuBoard board) {
        solveGame(board);
    }

    /**
     * A function that creates int table with number at random spot.
     * @return table with new position of numbers.
     */
    private List<Integer> randomizeTable() {
        List<Integer> table = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            table.add(i);
        }
        Collections.shuffle(table);
        return table;
    }

    private boolean isValidTile(int x, int y, int tile, SudokuBoard board) {
        // Checking row
        for (int i = 0; i < 9; i++) {
            if (board.get(x, i) == tile) {
                return false;
            }
        }

        // Checking column
        for (int i = 0; i < 9; i++) {
            if (board.get(i, y) == tile) {
                return false;
            }
        }

        // Checking 3x3 square
        int rowStart = x / 3 * 3;
        int colStart = y / 3 * 3;
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                if (board.get(i, j) == tile) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * A function that fills sudoku board with correct number.
     * @param board that the function is executed on
     * @return  if board is filled correctly
     */
    private boolean solveGame(SudokuBoard board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.get(row, col) == 0) {
                    List<Integer> table = randomizeTable();
                    for (int tile: table) {
                        if (isValidTile(row, col, tile, board)) {
                            board.set(row, col, tile);
                            if (solveGame(board)) {
                                return true;
                            } else {
                                board.set(row, col, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("solverType", "BacktrackingSudokuSolver")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // No specific fields to compare in this class
        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .toHashCode();
    }
}
