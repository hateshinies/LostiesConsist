package specification;

import domain.Owner;

public class LostySpecificationByOwnerId implements Specification, SqlSpecification {

    int ownerId;

    public LostySpecificationByOwnerId(int ownerId) {
        super();
        this.ownerId = ownerId;
    }

    @Override
    public boolean specified(Owner owner) {
        return owner.hasOwnerId(ownerId);
    }

    @Override
    public String toSQLClauses() {
        return String.format("ownerId exists %s", ownerId);
    }
}
