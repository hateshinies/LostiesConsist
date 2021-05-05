package specification;

import repo.Repo;

public interface Specification<T> {

    boolean specified(Repo<T> t);
}
