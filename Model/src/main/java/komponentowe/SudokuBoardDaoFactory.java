package komponentowe;

import java.sql.SQLException;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao<SudokuBoard> getJdbcDao(String dbUrl, String dbUser,
                                       String dbPassword) throws SQLException, SQLException {
        return new JdbcSudokuBoardDao(dbUrl, dbUser, dbPassword);
    }

}