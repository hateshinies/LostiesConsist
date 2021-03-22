package repository;

import domain.Owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

public class OwnerRepository implements Repository<Owner> {

    private Connection connection;
    private List<Owner> owners = new ArrayList<>();
    String GET_ALL_QUERY = "select * from owner;";
    String GET_ONE_QUERY = "select * from owner where \"ownerId\" = ?;";
    String CREATE_QUERY = "insert into owner values(?,?,?,?);";
    String DELETE_QUERY = "delete from owner where \"ownerId\" = ?;";
    String UPDATE_QUERY = "update owner set name = ?, phone = ?, email = ? where \"ownerId\" = ?;";


    public OwnerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Owner owner) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            preparedStatement.setInt(1, owner.getOwnerId());
            preparedStatement.setString(2, owner.getName());
            preparedStatement.setString(3, owner.getPhone());
            preparedStatement.setString(4, owner.getEmail());
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Owner owner) {
        try {
            //
            Integer ownerId = owner.getOwnerId();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, owner.getName());
            preparedStatement.setString(2, owner.getPhone());
            preparedStatement.setString(3, owner.getEmail());
            preparedStatement.setInt(4, ownerId);
            preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Owner owner) {
        try {
            Integer ownerId = owner.getOwnerId();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, ownerId);
            preparedStatement.executeQuery();
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
                Integer id = resultSet.getInt("ownerId");
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
    public Optional<Owner> getById(Integer id) {
        Owner owner = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_QUERY);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            //учесть случай, если такого объекта нет!
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                owner = new Owner();
                owner.setOwnerId(id);
                owner.setName(name);
                owner.setPhone(phone);
                owner.setEmail(email);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(owner);
    }

}
