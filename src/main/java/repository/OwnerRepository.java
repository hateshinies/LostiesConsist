package repository;

import domain.Owner;

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
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);
            /*      preparedStatement.setInt(1, owner.getOwnerId());
            preparedStatement.setString(2, owner.getName());
            preparedStatement.setString(3, owner.getPhone());
            preparedStatement.setString(4, owner.getEmail());*/
            /*            String[] array = new String[]{
                    owner.getOwnerId().toString(),
                    owner.getName(),
                    owner.getPhone(),
                    owner.getEmail()};*/
            String[] fields = owner.getFieldsArray();
            for (int i = 0; i < fields.length; i++) {
                if (isNumeric(fields[i]))
                    statement.setInt(i + 1, Integer.parseInt(fields[i]));
                else
                    statement.setString(i + 1, fields[i]);
            }
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Owner owner) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
            String[] fields = owner.getFieldsArray();
            for (int i = 0; i < fields.length; i++) {
                if (isNumeric(fields[i]))
                    /*первым идет ID сущности, его ставим в конец @statement, исходя из синтаксиса sql*/
                    statement.setInt(fields.length, Integer.parseInt(fields[i]));
                else
                    /* остальные элементы @fields ставим по порядку*/
                    statement.setString(i, fields[i]);
            }
            /*statement.setString(1, owner.getName());
            statement.setString(2, owner.getPhone());
            statement.setString(3, owner.getEmail());
            statement.setInt(4, ownerId);*/
            statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Owner owner) {
        try {
            String[] fields = owner.getFieldsArray();
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            //находим id(он идет первым в массиве)
            if (!isNumeric(fields[0]))
                throw new Exception("ownerId not found in fieldsArray");
            int ownerId = Integer.parseInt(fields[0]);
            statement.setInt(1, ownerId);
            statement.executeQuery();
        } catch (Exception throwables) {
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
            //todo учесть случай, если такого объекта нет!
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                String[] fields = new String[]{id.toString(), name, email, phone};
                owner = new Owner();
                owner.setFieldsArray(fields);
                /*owner.setOwnerId(id);
                owner.setName(name);
                owner.setPhone(phone);
                owner.setEmail(email);*/
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(owner);
    }

    /**
     * Проверяет, является ли строка числом
     *
     * @param string - строка, для которой выполняется проверка
     * @return возвращает true - если это число, false - если нет.
     */
    private boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
