package komponentowe;

import komponentowe.exception.JdbcDaoException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {
    private static JdbcSudokuBoardDao dao;
    private static final String DB_URL =  "jdbc:sqlserver://localhost:1433;database=TestDB;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "Password_123";

    @BeforeAll
    static void setupDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            String createBoardsTable = """
                IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='sudoku_boards' AND xtype='U')
                CREATE TABLE sudoku_boards (
                    id INT IDENTITY(1,1) PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    x INT NOT NULL,
                    y INT NOT NULL,
                    value INT NOT NULL
                );
                """;
            statement.execute(createBoardsTable);
        }
    }

    @BeforeEach
    void setup() throws SQLException {
        dao = new JdbcSudokuBoardDao(DB_URL, DB_USER, DB_PASSWORD);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM sudoku_boards;");
        }
    }

    @Test
    void testWriteAndRead() throws IOException, ClassNotFoundException, JdbcDaoException {
        SudokuBoard board = new SudokuBoard(new SudokuSolver() {
            @Override
            public void solve(SudokuBoard board) {
            }
        });
        board.set(0, 0, 5);
        board.set(1, 1, 8);
        board.set(2, 2, 3);

        // Zapis planszy
        dao.write("TestBoard", board);

        // Odczyt planszy
        SudokuBoard loadedBoard = dao.read("TestBoard");

        // Porównanie wartości wczytanej planszy
        assertEquals(5, loadedBoard.get(0, 0));
        assertEquals(8, loadedBoard.get(1, 1));
        assertEquals(3, loadedBoard.get(2, 2));
    }

    @Test
    void testWriteDuplicate() throws IOException, ClassNotFoundException, JdbcDaoException {
        SudokuBoard board = new SudokuBoard(new SudokuSolver() {
            @Override
            public void solve(SudokuBoard board) {
            }
        });
        board.set(0, 0, 5);


        dao.write("TestBoard", board);


        board.set(0, 0, 8);
        dao.write("TestBoard", board);


        SudokuBoard loadedBoard = dao.read("TestBoard");


        assertEquals(8, loadedBoard.get(0, 0));
    }

   @Test
    void testReadNonExistingBoard() {
        // Próba odczytu planszy, która nie istnieje
        Exception exception = assertThrows(IOException.class, () -> dao.read("NonExistentBoard"));
        assertFalse(exception.getMessage().contains("Błąd przy odczycie planszy Sudoku"));
    }

    @Test
    void testListNames() throws IOException, JdbcDaoException {
        SudokuBoard board = new SudokuBoard(new SudokuSolver() {
            @Override
            public void solve(SudokuBoard board) {
            }
        });
        dao.write("Board1", board);
        dao.write("Board2", board);
        dao.write("Board3", board);

        // Pobranie nazw plansz
        List<String> names = dao.names();

        // Sprawdzenie poprawności nazw
        assertEquals(3, names.size());
        assertTrue(names.contains("Board1"));
        assertTrue(names.contains("Board2"));
        assertTrue(names.contains("Board3"));
    }

    @Test
    void testClose() throws SQLException {
        // Zamknięcie DAO
        dao.close();

        // Próba użycia DAO po zamknięciu
        assertThrows(NullPointerException.class, () -> dao.names());
    }


}

