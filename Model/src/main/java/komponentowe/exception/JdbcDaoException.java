package komponentowe.exception;

import java.sql.SQLException;

public class JdbcDaoException extends SQLException {

    public JdbcDaoException(Throwable cause) {
        super(cause);
    }
}
