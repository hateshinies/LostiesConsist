import domain.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataSource {

    String SQL_SELECT = "Select * from owner";

    List<Owner> owners = new ArrayList<>();

    public DataSource() {
        simpleQuery(SQL_SELECT);
    }

    public void simpleQuery(String query) {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/consist", props);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
            connection.close();
            System.out.println("Owners size is: " + owners.size());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
