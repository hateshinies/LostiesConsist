package repository;

import domain.Owner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerRepository implements Repository<Owner> {

    private final DbExecutor executor;
    private final Connection connection;

    public OwnerRepository(Connection connection, DbExecutor executor) {
        this.connection = connection;
        this.executor = executor;
    }

    @Override
    public long create(Owner owner) throws SQLException {
        String[] fields = owner.getFieldsArray();

        if (fields == null)
            throw new IllegalStateException("fieldsArray is null");
        return executor.insert(connection, fields);
    }

    @Override
    public long update(Owner owner) throws SQLException {
        String[] fields = owner.getFieldsArray();

        if (fields == null)
            throw new IllegalStateException("fieldsArray is null");
        return executor.update(connection, fields);
    }

    @Override
    public long delete(Long id) throws SQLException {
        return executor.delete(connection, id);
    }

    @Override
    public Optional<Owner> getOne(Long id) throws SQLException {
        String[] fields = executor.getById(connection, id);

        if (fields.length == 0)
            return Optional.empty();
        else {
            Owner owner = new Owner();
            owner.setFieldsArray(fields);
            return Optional.of(owner);
        }
    }

    @Override
    public List<Owner> getAll() throws SQLException {
        List<String[]> fieldsList = executor.getAll(connection);

        if (fieldsList.isEmpty())
            throw new IllegalStateException("table is empty!");

        List<Owner> owners = new ArrayList<>();
        for (String[] fields : fieldsList) {
            Owner owner = new Owner();
            owner.setFieldsArray(fields);
            owners.add(owner);
        }
        return owners;
    }
}
