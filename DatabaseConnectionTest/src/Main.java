import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;


public class Main {
    public static void main(String[] args) {
        System.out.println("Started!");
        Connection connection = DbConnection.connect();
        try {
            String selectQuery = "SELECT * FROM User";
            ResultSet resultSet = DbConnection.executeQuery(selectQuery, connection);

            // Process the result set, if needed
            if (resultSet != null) {
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    int userId = resultSet.getInt("id");
                    String username = resultSet.getString("name");

                    // Process the data as needed
                    System.out.println("User ID: " + userId + ", Username: " + username);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
}