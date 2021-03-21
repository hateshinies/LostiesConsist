import domain.Owner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataSource {

    List<Owner> owners = new ArrayList<>();

    public Connection getConnection() throws SQLException {
        String connectString = "jdbc:postgresql://localhost:5432/consist";
        String username = "postgres";
        String password = "postgres";
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        return DriverManager.getConnection(connectString, props);
    }

    public void execQuery(Connection connection, String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
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
    }
}
