package komponentowe;

import komponentowe.exception.ImposibleCloneException;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn extends SudokuFieldGroup implements Cloneable {

    public SudokuColumn(List<SudokuField> fields) {
        super(fields);
    }

    @Override
    protected Object clone() throws ImposibleCloneException {
        try {
        SudokuColumn copy = (SudokuColumn) super.clone();
        List<SudokuField> fields = new ArrayList<>();
        for (SudokuField field : fields) {
            fields.add((SudokuField) field.clone());
        }
        copy = new SudokuColumn(fields);
        return copy;
        } catch (CloneNotSupportedException e) {
            throw new ImposibleCloneException();
        }
    }
}
