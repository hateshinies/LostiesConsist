package repository;

import domain.Owner;
import specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OwnerRepository implements Repository<Owner> {

    private Connection connection;
    private List<Owner> owners = new ArrayList<>();
    String GET_ALL_QUERY = "select * from owner;";
    String GET_ONE_QUERY = "select * from owner where ownerId = ?;";
    String CREATE_QUERY = "insert into owner values(?,?,?,?);";
    String DELETE_QUERY = "delete from owner where ownerId = ?;";


    public OwnerRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Owner owner) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setLong(1, owner.getOwnerId());
            preparedStatement.setString(2, owner.getName());
            preparedStatement.setString(3, owner.getPhone());
            preparedStatement.setString(4, owner.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("rs: " + resultSet.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Owner owner) {

    }

    @Override
    public void delete(Owner owner) {
        try {
            Long ownerId = owner.getOwnerId();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setLong(1, ownerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("rs: " + resultSet.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Owner> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("ownerId");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");

                Owner owner = new Owner();
                owner.setOwnerId(id);
                owner.setName(name);
                owner.setPhone(phone);
                owner.setEmail(email);
                owners.add(owner);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return owners;
    }

    @Override
    public Optional<Owner> query(SqlSpecification sqlSpecification) {
//        return Optional.ofNullable(owners.get(sqlSpecification.toSQLClauses().indexOf()));
        return null;
    }
}
