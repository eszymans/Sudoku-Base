package komponentowe;

import komponentowe.exception.ImposibleCloneException;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow extends SudokuFieldGroup implements Cloneable {

    public SudokuRow(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            SudokuRow copy = (SudokuRow) super.clone();
            List<SudokuField> fields = new ArrayList<>();
            for (SudokuField field : fields) {
                fields.add((SudokuField) field.clone());
            }
            copy = new SudokuRow(fields);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new ImposibleCloneException();
        }
    }
}