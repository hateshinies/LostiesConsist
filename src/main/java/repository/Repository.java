package repository;

import specification.Specification;

import java.util.List;

public interface Repository<T> {

    void create(T t) throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;

    void softDelete(T t) throws Exception;

    T getOne(T t) throws Exception;

    List<T> getAll() throws Exception;

    boolean exists(T t) throws Exception;

    void save(T t) throws Exception;

    /**
     * @return underlying provider object for Repository
     */
    Object getDelegate(T t) throws Exception;

    List<T> find(Specification<T> spec) throws Exception;
}