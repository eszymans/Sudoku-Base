package komponentowe;

import java.util.Random;

public enum SudokuBoardLevelDifficulty {
    EASY(20),
    MEDIUM(40),
    HARD(60),
    ;

    private final int emptyFields;

    SudokuBoardLevelDifficulty(int emptyFields) {
        this.emptyFields = emptyFields;
    }

    public int getEmptyFields() {
        return emptyFields;
    }

    public SudokuBoard sudokuBoardCellsRemover(SudokuBoard board) throws CloneNotSupportedException {

        SudokuBoard clone = (SudokuBoard) board.clone();
        int cellsToRemove = this.getEmptyFields();
        Random rand = new Random();

        while (cellsToRemove > 0) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (clone.get(row, col) != 0) {
                clone.set(row, col, 0);
                cellsToRemove--;
            }
        }
        return clone;
    }

}
