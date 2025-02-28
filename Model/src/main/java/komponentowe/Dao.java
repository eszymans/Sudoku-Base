package komponentowe;

import komponentowe.exception.DaoException;
import komponentowe.exception.JdbcDaoException;

import java.util.List;

public interface Dao<T> {
    T read(String name) throws DaoException, JdbcDaoException;

    void write(String name,T t) throws DaoException, JdbcDaoException;

    List<String> names() throws JdbcDaoException;
}
