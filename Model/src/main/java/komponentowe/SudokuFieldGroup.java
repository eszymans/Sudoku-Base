package komponentowe;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class SudokuFieldGroup {
    private List<SudokuField> part;

    public SudokuFieldGroup(List<SudokuField> part) {
        if (part != null) {
            this.part = part;
        }
    }

    public boolean verify() {
        int[] tab = new int[9];
        for (int i = 0; i < 9; i++) {
            int value = part.get(i).getFieldValue();
            if (value < 1 || value > 9) {
                return false;
            }
            tab[value - 1]++;
            if (tab[value - 1] > 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("part", part)
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
        SudokuFieldGroup that = (SudokuFieldGroup) obj;
        return new EqualsBuilder()
                .append(part, that.part)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(part)
                .toHashCode();
    }
}
