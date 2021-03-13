import java.sql.*;

public class CheckForID {
    public static int query(long userID) {
        int returnAmount = 0;

        String sqlQuery = "SELECT commendmentCounter FROM commendmentCounter WHERE commendeeID = ?";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./Databases/CounterDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            try (PreparedStatement pstmtQUERY = connection.prepareStatement(sqlQuery)) {
                pstmtQUERY.setLong(1, userID);

                ResultSet rs1 = pstmtQUERY.executeQuery();

                while (rs1.next()) {
                    returnAmount = rs1.getInt("commendmentCounter");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return returnAmount;
    }
}