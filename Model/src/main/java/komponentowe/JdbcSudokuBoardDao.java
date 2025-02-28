package komponentowe;

import komponentowe.exception.DaoException;
import komponentowe.exception.JdbcDaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private Connection connection;

    // Konstruktor otwierający połączenie z bazą danych
    public JdbcSudokuBoardDao(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        connection.setAutoCommit(false);
    }

    // Metoda odczytująca planszę Sudoku po nazwie
    @Override
    public SudokuBoard read(String name) throws DaoException, JdbcDaoException {
        String query = "SELECT x, y, value FROM sudoku_boards WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) { // Sprawdzenie, czy są wyniki
                    throw new DaoException("Nie znaleziono planszy o podanej nazwie.");
                }

                SudokuSolver solver = new BacktrackingSudokuSolver(); // Użyj rzeczywistej implementacji solvera
                SudokuBoard sudokuBoard = new SudokuBoard(solver);

                while (rs.next()) {
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    int value = rs.getInt("value");

                    sudokuBoard.set(x, y, value); // Ustawienie wartości w planszy Sudoku
                }

                return sudokuBoard;
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    @Override
    public void write(String name, SudokuBoard board) throws DaoException, JdbcDaoException {
        String query = """
            MERGE INTO sudoku_boards AS target
            USING (SELECT ? AS name, ? AS x, ? AS y, ? AS value) AS source
            ON (target.name = source.name AND target.x = source.x AND target.y = source.y)
            WHEN MATCHED THEN 
                UPDATE SET value = source.value
            WHEN NOT MATCHED THEN
                INSERT (name, x, y, value)
                VALUES (source.name, source.x, source.y, source.value);
        """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    int value = board.get(x, y);
                    stmt.setString(1, name);
                    stmt.setInt(2, x);
                    stmt.setInt(3, y);
                    stmt.setInt(4, value);
                    stmt.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
    }

    // Metoda zwracająca listę nazw wszystkich plansz w bazie danych
    @Override
    public List<String> names() throws JdbcDaoException {
        List<String> names = new ArrayList<>();
        String query = "SELECT DISTINCT name FROM sudoku_boards";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new JdbcDaoException(e);
        }
        return names;
    }

    // Implementacja AutoCloseable - zamknięcie połączenia z bazą danych
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
}