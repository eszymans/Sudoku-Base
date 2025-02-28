package komponentowe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldGroupTest {
    boolean checkOnePart(SudokuBoard sudokuBoard, int rowIndex) {
        int tile;
        for (int x = 0; x < 9; x++) {
            tile = sudokuBoard.get(x, rowIndex);
            for (int i = x + 1; i < 9; i++) {
                if (tile == sudokuBoard.get(i, rowIndex)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    @DisplayName("Test of Constructor")
    public void test() {
        SudokuFieldGroup row1 = new SudokuRow (null);
        assertNotEquals(row1,null);
    }

    @Test
    @DisplayName("Test checks if FieldGroup is filled correctly")
    public void TestIfVerifyFieldGroupIsCorrect(){
        SudokuSolver solver1 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver1);
        sudokuBoard1.solveGame();

        SudokuSolver solver2 = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver2);

        int y = 0;
        SudokuFieldGroup row1 = new SudokuRow (List.of(sudokuBoard1.getRow(y)));
        SudokuFieldGroup row2 = new SudokuRow (List.of(sudokuBoard2.getRow(y)));

        assertEquals(checkOnePart(sudokuBoard1, y), row1.verify());
        assertFalse(row2.verify());
    }

    @Test
    @DisplayName("Test checks if field group is filled wrong")
    public void TestIfVerifyFieldGroupIsGivingFalse(){

        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            fields[i] = new SudokuField();
            fields[i].setFieldValue(5);
        }
        SudokuRow row = new SudokuRow(List.of(fields));
        assertFalse(row.verify());
    }

    @Test
    @DisplayName("Test checks if FieldGroup is filled wrong")
    public void TestIfVerifyFieldGroupIsGivingFalseWhenIsNumberBigger(){

        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            fields[i] = new SudokuField();
            fields[i].setFieldValue(i*2);
        }
        SudokuRow row = new SudokuRow(List.of(fields));
        assertFalse(row.verify());
        fields[0].setFieldValue(20);
        assertFalse(row.verify());
    }

    @Test
    @DisplayName("Check if toString method works correctly")
    public void TestToString(){
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            fields[i] = new SudokuField();
            fields[i].setFieldValue(i);
        }
        SudokuRow row = new SudokuRow(List.of(fields));
        row.toString();
        assertTrue(row.toString().contains("part"));
    }

    @Test
    @DisplayName("Check if equals method works correctly for equal objects")
    public void TestEquals(){
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            fields[i] = new SudokuField();
            fields[i].setFieldValue(i);
        }
        SudokuRow row = new SudokuRow(List.of(fields));

        assertTrue(row.equals(row));
    }

    @Test
    @DisplayName("Check if equals method works correctly for different objects")
    public void TestNotEqual() {
        SudokuField[] fields1 = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields1[i] = new SudokuField();
            fields1[i].setFieldValue(i);
        }
        SudokuRow row1 = new SudokuRow(List.of(fields1));

        SudokuField[] fields2 = new SudokuField[9];
        for (int i = 0; i < 9; i++) { // Zmieniono warunki pętli
            fields2[i] = new SudokuField();
            fields2[i].setFieldValue(9 - i); // Indeksowanie poprawione
        }
        SudokuRow row2 = new SudokuRow(List.of(fields2));

        assertFalse(row1.equals(row2));
    }


    @Test
    @DisplayName("Check if equals method works correctly for null objects")
    public void TestNull(){
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++){
            fields[i] = new SudokuField();
            fields[i].setFieldValue(i);
        }
        SudokuRow row = new SudokuRow(List.of(fields));

        assertFalse(row.equals(null));
    }

    @Test
    @DisplayName("Check equals method when compared with different Instance of same class")
    public void testEqualsWithDifferentInstanceOfSameClass() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        assertTrue(field1.equals(field2));
    }

    @Test
    @DisplayName("Equals method returns false when compared with null")
    public void testEqualsWithNull() {
        SudokuFieldGroup group = new SudokuFieldGroup(List.of(new SudokuField()));
        assertFalse(group.equals(null));
    }

    @Test
    @DisplayName("Equals method returns false when compared with an object of a different class")
    public void testEqualsWithDifferentClass() {
        SudokuFieldGroup group = new SudokuFieldGroup(List.of(new SudokuField()));
        String differentClassObject = "String";

        assertFalse(group.equals(differentClassObject));
    }

}