package komponentowe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {

    @Test
    @DisplayName("Test of function create SudokuBoardDaoFactory ")
    void getFileDao() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        String fileName = "testSudokuBoard.dat";
        Dao<SudokuBoard> dao = factory.getFileDao(fileName);

        assertNotNull(dao);
    }
}