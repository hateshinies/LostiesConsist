package repo;

import specification.Specification;

import java.io.Serializable;
import java.util.List;

public interface Repo<T> {

    long create(T entity) throws Exception;

    long update(T entity) throws Exception;

    long delete(T entity) throws Exception;

    T getOne(T entity) throws Exception;

    List<T> getAll() throws Exception;

    boolean hasId(long id);

    /**
     * Find by a given identifier (primary key).
     *
     * @param clazz the entity class
     * @param id the identifier (primary key)
     * @return the found entity instance or null if not found
     * @throws IllegalArgumentException if <code>clazz</code> is does not denote
     *         an entity or <code>id</code> is null
     */
    T find(Serializable id);

    /**
     * Find by a given specification.
     *
     * @param clazz the entity class
     * @param spec the specification
     * @return the <code>SpecificationResult</code> instance
     * @throws IllegalArgumentException if <code>clazz</code> is does not denote
     *         an entity
     */
    SpecificationResult<T> find(Specification<T> spec);

    /**
     * Find all instances of T entity.
     *
     * @param clazz the entity class
     * @return the <code>SpecificationResult</code> instance
     */
    SpecificationResult<T> find();

    /**
     * Finds entities by example.
     *
     * @return the <code>SpecificationResult</code> instance
     */
    SpecificationResult<T> find(T example);

    /**
     * Count all entities of T entity.
     *
     * @param clazz the entity class
     * @return number of entities
     */
    long count();

    /**
     * Count entities by a given specification.
     *
     * @param clazz the entity class
     * @param spec the specification
     */
    long count(Specification<T> spec);

    /*
     * EXECUTE OPERATIONS
     */
    /**
     * Save a new entity.
     */
    void save(T t) ;

    /**
     * Update an existed entity.
     *
     * @return instance of T
     */
    T update(T t) ;

    /**
     * Delete an entity.
     **/
    void remove(T t) ;

    /**
     * Return the underlying provider object for the <code>Repository</code>, if
     * available. The result of this method is implementation specific.
     *
     * @return underlying provider object for Repository
     */
    Object getDelegate();
}