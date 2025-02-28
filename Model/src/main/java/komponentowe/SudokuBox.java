package komponentowe;

import komponentowe.exception.ImposibleCloneException;

import java.util.ArrayList;
import java.util.List;

public class SudokuBox extends SudokuFieldGroup implements Cloneable {

    public SudokuBox(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    protected Object clone() throws ImposibleCloneException {
        try {
            SudokuBox copy = (SudokuBox) super.clone();
            List<SudokuField> fields = new ArrayList<>();
            for (SudokuField field : fields) {
                fields.add((SudokuField) field.clone());
            }
            copy = new SudokuBox(fields);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new ImposibleCloneException();
        }
    }
}