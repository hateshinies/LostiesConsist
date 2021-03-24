/*
package specification;

import domain.Owner;

public class LostySpecificationByOwnerId implements Specification, SqlSpecification {

    Long ownerId;

    public LostySpecificationByOwnerId(Long id) {
        super();
        this.ownerId = id;
    }

    @Override
    public boolean specified(Owner owner) {
        return owner.hasId(ownerId);
    }

    @Override
    public String toSQLClauses() {
        return String.format("ownerId exists %s", ownerId);
    }
}
*/
