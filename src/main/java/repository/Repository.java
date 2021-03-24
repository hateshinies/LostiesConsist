package repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    long create(T entity) throws SQLException;

    long update(T entity) throws SQLException;

    long delete(Long id) throws SQLException;

    Optional<T> getOne(Long id) throws SQLException;

    List<T> getAll() throws SQLException;
}
