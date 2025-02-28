package komponentowe.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImposibleCloneExceptionTest {

    @Test
    void testImposibleCloneExceptionConstructor() {
        ImposibleCloneException exception = new ImposibleCloneException();

        assertNotNull(exception);
    }

}