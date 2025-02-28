package komponentowe.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicExceptionTest {

    @Test
    void testBasicExceptionImposibleCloneExceptionConstructor() {
        Throwable cause = new Throwable("Cause of the exception");

        BasicException exception = new BasicException(cause);
        assertNotNull(exception);
    }

}