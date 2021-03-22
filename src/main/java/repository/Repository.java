package repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> getAll();

    Optional<T> getById(Integer id);
}
