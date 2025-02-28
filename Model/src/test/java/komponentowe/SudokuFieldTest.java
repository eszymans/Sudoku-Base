package komponentowe;

import komponentowe.exception.DaoException;
import komponentowe.exception.ImposibleCloneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    @DisplayName("Check if isEqual method works correctly for equal objects")
    public void TestIfIsEqual() {
        SudokuField sudokuField = new SudokuField();

        assertTrue(sudokuField.equals(sudokuField));
    }

    @Test
    @DisplayName("Check if isEqual method works correctly for different objects")
    public void TestIfIsNotEqual() {
        SudokuField sudokuField = new SudokuField();
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();

        assertFalse(sudokuField.equals(backtrackingSudokuSolver));
    }

    @Test
    @DisplayName("Check equals method when compared with null")
    public void testEqualsWithNull() {
        SudokuField sudokuField = new SudokuField();

        assertFalse(sudokuField.equals(null));
    }

    @Test
    @DisplayName("Check If clone method for field works correctly")
    public void TestCloneRow() throws CloneNotSupportedException {
        SudokuField sudokuField = new SudokuField();
            SudokuField sudokuFieldClone = (SudokuField) sudokuField.clone();
            assertNotSame(sudokuField, sudokuFieldClone);
    }

    @Test
    @DisplayName("Check If compareTo method works correctly for field")
    public void TestCompareTo() {
        SudokuField sudokuField1 = new SudokuField();
        sudokuField1.setFieldValue(7);

        SudokuField sudokuField2 = new SudokuField();
        sudokuField2.setFieldValue(10);

        int result1 = sudokuField1.compareTo(sudokuField2);
        int result2 = sudokuField2.compareTo(sudokuField1);

        assertTrue(result1 < 0);
        assertTrue(result2 > 0);

    }

    @Test
    @DisplayName("Check If compareTo method works correctly with exception for field")
    public void TestCompareToCatch() {
        SudokuField sudokuField1 = new SudokuField();
        sudokuField1.setFieldValue(7);

        assertThrows(Exception.class, () -> sudokuField1.compareTo(null));

    }

    @Test
    public void testCloneException() {
        SudokuField original = new SudokuField() {
            @Override
            protected Object clone() throws ImposibleCloneException {
                throw new ImposibleCloneException();
            }
        };

        // Ensure ImposibleCloneException is thrown
        assertThrows(ImposibleCloneException.class, original::clone, "Expected ImposibleCloneException to be thrown");
    }


}