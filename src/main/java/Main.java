import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String SQL_SELECT = "Select * from owner";

        DataSource dataSource = new DataSource();

        Connection connection = dataSource.getConnection();
        dataSource.execQuery(connection, SQL_SELECT);

    }
}
