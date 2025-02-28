package komponentowe;

import komponentowe.exception.DaoException;
import komponentowe.exception.JdbcDaoException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    static final private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    final private BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    final private SudokuBoard sudokuBoard = new SudokuBoard(solver);
    final private SudokuBoard sudokuBoard2 = new SudokuBoard(solver);
    private static Dao<SudokuBoard> fileSudokuBoardDao;
    private static Dao<SudokuBoard> fileSudokuBoardDao2;
    static String directory = "test";
    static String fileName = "testBoard";

    @BeforeAll
    static void setUp() {
        fileSudokuBoardDao = factory.getFileDao(directory);
    }

    @AfterAll
    static void tearDown() throws IOException {
        try {
            Files.deleteIfExists(Paths.get(directory, fileName));
            Files.deleteIfExists(Paths.get(directory, fileName+ "1"));
            Files.deleteIfExists(Paths.get(directory, fileName+ "2"));
            Files.deleteIfExists(Paths.get(directory + "1"));
            Files.deleteIfExists(Paths.get(directory + "2"));
            Files.deleteIfExists(Paths.get("testSudokuBoard.dat"));
            Files.deleteIfExists(Paths.get(directory));
            Files.deleteIfExists(Paths.get("invalidDirectory"));
            Files.deleteIfExists(Paths.get(directory + "1000"));
        } catch (IOException e) {
            e.printStackTrace();
       }
    }

    @Test
    @DisplayName("Test if constructor creates directory")
    void constructorTest(){
        File directoryPath = new File(directory+"1000");
        directoryPath.delete();
        fileSudokuBoardDao2 = factory.getFileDao(directory+"1000");
        assertTrue(directoryPath.exists());
    }

    @Test
    @DisplayName("Test when directory is empty is name Empty")
    void namesWhenDirectoryIsEmpty() throws JdbcDaoException {
        fileSudokuBoardDao2 = factory.getFileDao(directory+"1");
        List<String> names = fileSudokuBoardDao2.names();

        assertNotNull(names);
        assertTrue(names.isEmpty());
    }

    @Test
    @DisplayName("Test writing and reading a SudokuBoard object using FileSudokuBoardDao ")
    void writeAndReadTest() throws IOException, ClassNotFoundException, SQLException {

        sudokuBoard.solveGame();
        fileSudokuBoardDao.write(fileName, sudokuBoard);
        SudokuBoard sudokuBoardRead = fileSudokuBoardDao.read(fileName);

        assertEquals(sudokuBoard, sudokuBoardRead);
        assertNotNull(sudokuBoardRead);

    }

    @Test
    @DisplayName("If names giving the right number of files in directory ")
    void testNamesWithSudokuFiles() throws IOException, ClassNotFoundException, JdbcDaoException {
        sudokuBoard.solveGame();
        sudokuBoard2.solveGame();
        fileSudokuBoardDao.write(fileName, sudokuBoard);
        fileSudokuBoardDao.write(fileName+"1", sudokuBoard);
        fileSudokuBoardDao.write(fileName+"2", sudokuBoard2);


        List<String> names = fileSudokuBoardDao.names();
        assertNotNull(names);
        assertEquals(3, names.size());
    }

    @Test
    void testReadThrowsFileOperationException() {
        String invalidFileName = "invalidBoard.dir";
        FileSudokuBoardDao dao = new FileSudokuBoardDao("invalidDirectory"); // Zła ścieżka, aby wymusić wyjątek

        assertThrows(DaoException.class, () -> {
            dao.read(invalidFileName);
        });
        File file = new File("invalidDirectory", "invalidBoard.ser");
        file.delete();
    }

    @Test
    void testWriteThrowsFileOperationException() {
        String invalidFileName = "invalidBoard23.ser";
        File file = new File("invalidDirectory", "invalidBoard23.ser");
        file.mkdir();
        file.setReadOnly();
        FileSudokuBoardDao dao = new FileSudokuBoardDao("invalidDirectory");

        assertThrows(DaoException.class, () -> {
            dao.write(invalidFileName, sudokuBoard);
        });
        file.delete();
    }

    @Test
    void testCloseDoesNotThrowException() throws DaoException {

        FileSudokuBoardDao dao = new FileSudokuBoardDao("invalidDirectory");

        assertDoesNotThrow(() -> dao.close());
    }

}