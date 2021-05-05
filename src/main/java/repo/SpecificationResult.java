package repo;

import java.util.List;

public interface SpecificationResult<T> {

    /**
     * Set the number of results to retrieve.
     */
    SpecificationResult<T> size(int size);

    /**
     * Set the from position to start retrieve.
     */
    SpecificationResult<T> from(int from);

    /**
     * Sort results by ascending.
     */
    SpecificationResult<T> ascending(String propertyName);

    /**
     * Sort results by descending.
     */
    SpecificationResult<T> descending(String propertyName);

    /**
     * Set a query property or hint.
     */
    SpecificationResult<T> hint(String name, Object value);

    /**
     * Return single result.
     *
     * @return the found instance of T or null if not found
     */
    T single();

    /**
     * @return list of the results or empty if not result found.
     */
    List<T> list();
}