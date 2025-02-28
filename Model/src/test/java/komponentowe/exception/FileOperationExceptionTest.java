package komponentowe.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileOperationExceptionTest {

    @Test
    void testFileOperationExceptionWithMessageAndCause() {
        Throwable cause = new Throwable("Cause of the exception");

        FileOperationException exception = new FileOperationException(cause);
        assertNotNull(exception);
    }

}