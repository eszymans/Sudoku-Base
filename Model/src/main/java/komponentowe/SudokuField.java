package komponentowe;

import komponentowe.exception.ImposibleCloneException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class SudokuField implements Serializable, Cloneable, Comparable {
    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
            this.value = value;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("value", value)
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
        SudokuField that = (SudokuField) obj;
        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    protected Object clone() throws ImposibleCloneException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ImposibleCloneException();
        }
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException("Cannot compare to null");
        }
        return Integer.compare(this.getFieldValue(), ((SudokuField)o).getFieldValue());
    }
}
