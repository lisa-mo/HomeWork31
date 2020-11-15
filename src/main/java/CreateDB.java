import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {

    public static void main(String[] args) {
        String dataBaseName = "my_db_1";
        String userName = "root";
        String password = "0000";
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        try {
            connection = DriverManager.getConnection(url, userName, password);
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE DATABASE " + dataBaseName);
                System.out.println("DB is created");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Cannot connect");
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
