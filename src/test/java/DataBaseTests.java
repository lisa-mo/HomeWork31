import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

import static org.testng.Assert.assertEquals;

public class DataBaseTests {
    Connection connection = null;
    Statement statement = null;
    String tableName = "Authors";
    String dataBaseName = "my_db_1";
    String firstName[] = {"Agatha", "Stephen", "Terry", "Isaac"};
    String lastName[] = {"Christie", "King", "Brooks", "Asimov"};
    int yearOfBirth[] = {1890, 1947, 1944, 1920};

    @BeforeClass
    public void toConnect() {

        String url = "jdbc:mysql://127.0.0.1:3306/my_db_1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "0000";

        try {
            connection = DriverManager.getConnection(url, userName, password);
            statement = connection.createStatement();
            if (connection != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot connect");
        }
    }

    @Test(priority = 1)
    public void createTable() {
        try {
            statement.executeUpdate("CREATE TABLE " + tableName + " (" +
                    "Author_id INT (10) NOT NULL, " +
                    "First_Name VARCHAR(255), " +
                    "Last_Name VARCHAR(255), " +
                    "Year_of_birth INT (4), " +
                    "PRIMARY KEY (Author_id)" +
                    ");");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void fillInTable() {
        try {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO "
                    + tableName + " (Author_id, First_Name, Last_Name, Year_of_birth) "
                    + " VALUES (?, ?, ?, ?)");

            for (int i = 1; i <= firstName.length; ++i) {
                insert.setInt(1, i);
                insert.setString(2, firstName[i - 1]);
                insert.setString(3, lastName[i - 1]);
                insert.setInt(4, yearOfBirth[i - 1]);
                insert.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 3)
    public void selectTable() {
        for (int i = 1; i <= firstName.length; i++) {
            try {
                String temp = String.valueOf(i);
                ResultSet resultSet = statement.executeQuery("SELECT * FROM my_db_1.Authors WHERE Author_id = " + temp + ";");
                while (resultSet.next()) {
                    String firstNameTemp = resultSet.getString("First_Name");
                    assertEquals(firstName[i - 1], firstNameTemp);
                    String lastNameTemp = resultSet.getString("Last_Name");
                    assertEquals(lastName[i - 1], lastNameTemp);
                    int yearOfBirthTemp = resultSet.getInt("Year_of_birth");
                    assertEquals(yearOfBirth[i - 1], yearOfBirthTemp);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test(priority = 4)
    public void updateSell() {
        try {
            statement.executeUpdate("UPDATE my_db_1.Authors SET Year_of_birth = 1000 WHERE Author_id = 1;");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 5)
    public void selectToCheckUpdate() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_db_1.Authors WHERE Author_id = 1;");
            while (resultSet.next()) {
                int yearOfBirthTemp = resultSet.getInt("Year_of_birth");
                assertEquals(1000, yearOfBirthTemp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 6)
    public void deleteRow() {
        try {
            statement.executeUpdate("DELETE FROM my_db_1.Authors WHERE Author_id = 1;");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 7)
    public void selectToCheckDeletion() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_db_1.Authors WHERE Author_id = 1;");
            while (resultSet.next()) {
                int authorIDTemp = resultSet.getInt("Author_id");
                assertEquals("NULL", authorIDTemp);
                String firstNameTemp = resultSet.getString("First_Name");
                assertEquals("NULL", firstNameTemp);
                String lastNameTemp = resultSet.getString("Last_Name");
                assertEquals("NULL", lastNameTemp);
                int yearOfBirthTemp = resultSet.getInt("Year_of_birth");
                assertEquals("NULL", yearOfBirthTemp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 8)
    public void dropTable() {
        try {
            statement.executeUpdate("DROP TABLE my_db_1.Authors");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test(priority = 9)
    public void checkIsTableExist() throws Exception {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        if (tables.next()) {
            throw new Exception("Table exists");
        } else {
            System.out.println("Table does not exist");
        }
    }

    @Test(priority = 10)
    public void dropDataBase() {
        try {
            statement.executeUpdate("DROP DATABASE " + dataBaseName);
        } catch (SQLException ex) {
            ex.printStackTrace();
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
