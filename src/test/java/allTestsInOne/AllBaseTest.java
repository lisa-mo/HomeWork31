package allTestsInOne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AllBaseTest {
    Connection connection = null;

    public Connection toConnect(String url, String userName, String password) {

        try {
            connection = DriverManager.getConnection(url, userName, password);
            if (connection != null) {
                System.out.println("Connected to the database");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot connect");
        }
        return connection;
    }
}
