package repository;

import specification.SqlSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryImpl implements Repository<Object> {
    private List<Object> objects = new ArrayList<>();

    public RepositoryImpl(List<Object> objects) {
        this.objects = objects;
    }

    @Override
    public void create(Object entity) {
//sql
    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void delete(Object entity) {

    }

    @Override
    public List<Object> getAll() {
        return objects;
    }

    @Override
    public Optional<Object> query(SqlSpecification sqlSpecification) {
//        return Optional.ofNullable(objects.get(0));
        return null;
    }
}
