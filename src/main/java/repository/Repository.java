package repository;

import specification.SqlSpecification;

import java.util.List;

public interface Repository<T> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> getAll();

    List query(SqlSpecification sqlSpecification);
}
