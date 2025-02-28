package komponentowe;

import komponentowe.exception.ImposibleCloneException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SudokuBoard implements Serializable, Cloneable {
    private Map<String, SudokuField> board; // Mapa dla pól Sudoku
    private SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;
        board = new HashMap<>();
        // Inicjalizacja planszy 9x9
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                board.put(x + "," + y, new SudokuField()); // Klucz to "x,y"
            }
        }
    }

    /**
     * A function that gives back number which is placed at (x,y).
     *
     * @param x the location on the x-axis where the number should be plotted.
     * @param y the location on the y-axis where the number should be plotted.
     * @return number which is placed at (x,y)
     * @throws IndexOutOfBoundsException if x or y is out of bounds
     */
    public int get(int x, int y) {
        return board.get(x + "," + y).getFieldValue(); // Pobierz wartość pola
    }

    public void set(int x, int y, int value) {
       if (value < 0 || value > 9) {
           throw new IllegalArgumentException("Value must be between 0 and 9");
       }
       board.get(x + "," + y).setFieldValue(value);
    }

    public void solveGame() {
        solver.solve(this);
    }

    public SudokuField[] getRow(int y) {
        SudokuField[] fields = new SudokuField[9];
        for (int x = 0; x < 9; x++) {
            fields[x] = board.get(x + "," + y);
        }
        return fields;
    }

    public SudokuField[] getColumn(int x) {
        SudokuField[] fields = new SudokuField[9];
        for (int y = 0; y < 9; y++) {
            fields[y] = board.get(x + "," + y);
        }
        return fields;
    }

    public SudokuField[] getBox(int x, int y) {
        SudokuField[] fields = new SudokuField[9];
        int startRow = x / 3 * 3; // Wyznaczenie wiersza dla pudełka
        int startCol = y / 3 * 3; // Wyznaczenie kolumny dla pudełka

        // Wypełnienie tablicy polami z pudełka
        int index = 0;
        for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
            for (int colOffset = 0; colOffset < 3; colOffset++) {
                fields[index] = board.get((startRow + rowOffset) + "," + (startCol + colOffset));
                index++;
            }
        }
        return fields;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("board", board)
                .append("solver", solver)
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
        SudokuBoard that = (SudokuBoard) obj;
        return new EqualsBuilder()
                .append(board, that.board)
                .append(solver, that.solver)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(board)
                .append(solver)
                .toHashCode();
    }

    @Override
    protected Object clone() throws ImposibleCloneException {
        Object result;
        try {
            SudokuBoard clonedBoard = (SudokuBoard) super.clone();
            clonedBoard.board = new HashMap<>();

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    String key = x + "," + y;
                    SudokuField originalField = this.board.get(key);

                    // Tworzenie nowego obiektu SudokuField na podstawie oryginalnego
                    SudokuField clonedField = new SudokuField();
                    clonedField.setFieldValue(originalField.getFieldValue());

                    // Dodawanie sklonowanego pola do mapy
                    clonedBoard.board.put(key, clonedField);
                }
            }
            result = clonedBoard;
        } catch (CloneNotSupportedException ex) {
            throw new ImposibleCloneException();
        }
        return result;
    }

}



