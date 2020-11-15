package allTestsInOne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AllBaseTest {
    Connection connection = null;
//    String url = "jdbc:mysql://127.0.0.1:3306/my_db_1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    String userName = "root";
//    String password = "0000";

//    public void toConnectWithOutDB() {
//        String url = "jdbc:mysql://127.0.0.1:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//        String userName = "root";
//        String password = "0000";
//
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            if (connection != null) {
//                System.out.println("Connected to the database");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Cannot connect");
//        }
//    }

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
