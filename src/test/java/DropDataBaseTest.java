import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.assertNotEquals;

public class DropDataBaseTest {
    Connection connection = null;
    String myDataBaseName = "my_db_1";

    @BeforeClass
    public void toConnect() {
        String url = "jdbc:mysql://127.0.0.1:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "0000";

        try {
            connection = DriverManager.getConnection(url, userName, password);
            if (connection != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot connect");
        }

    }

    @Test
    public void checkIsDBExist() throws Exception {
        ResultSet resultSet = connection.getMetaData().getCatalogs();

        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);
            System.out.println(databaseName);
            assertNotEquals(myDataBaseName, databaseName);
        }
    }

    @AfterClass
    public void closeConnection() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
