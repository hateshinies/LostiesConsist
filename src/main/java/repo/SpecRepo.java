package repo;

import specification.Specification;

import java.io.Serializable;

public interface SpecRepo {
    /**
     * Find by a given identifier (primary key).
     *
     * @param clazz the entity class
     * @param id the identifier (primary key)
     * @return the found entity instance or null if not found
     */
    <T> T find(Class<T> clazz, Serializable id);

    /**
     * Find by a given specification.
     *
     * @param clazz the entity class
     * @param spec the specification
     */
    <T> SpecificationResult<T> find(Class<T> clazz, Specification<T> spec);

    /**
     * Find all instances of T entity.
     *
     * @param clazz the entity class
     * @return the <code>SpecificationResult</code> instance
     */
    <T> SpecificationResult<T> find(Class<T> clazz);

    /**
     * Find entities by example.
     *
     * @return the <code>SpecificationResult</code> instance
     */
    <T> SpecificationResult<T> find(T example);

    /**
     * Count all entities of T entity.
     *
     * @param clazz the entity class
     * @return number of entities
     */
    <T> long count(Class<T> clazz);

    /**
     * Count entities by a given specification.
     *
     * @param clazz the entity class
     * @param spec the specification
     */
    <T> long count(Class<T> clazz, Specification<T> spec);

    /*
     * EXECUTE OPERATIONS
     */
    /**
     * Save a new entity.
     */
    <T> void save(T t);

    /**
     * Save and flush entity into database.
     */
    <T> void saveAndFlush(T t);

    /**
     * Update an existed entity.
     */
    <T> T update(T t);

    /**
     * Update an existed entity then flush it into database.
     */
    <T> T updateAndFlush(T t);

    /**
     * Delete an entity.
     */
    <T> void removeAndFlush(T t);

    /**
     * Delete an entity.
     */
    <T> void remove(T t);

    /**
     * Return the underlying provider object for the <code>Repository</code>, if
     * available. The result of this method is implementation specific.
     *
     * @return underlying provider object for Repository
     */
    Object getDelegate();
}