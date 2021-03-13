import java.sql.*;

public class DatabaseInteraction {
    public static Connection connectToDB(String databaseName){
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:./Databases/"+databaseName+".db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static void CounterDBInsert(long commendeeID, String comendeeName) {
        boolean checkIfInDB = false;
        int commendmentCounterValue = 0;
        Connection connection = null;

        String sqlUpdate = "UPDATE commendmentCounter SET commendmentCounter = ?"
                + "WHERE commendeeID = ?";

        String sqlInsert = "INSERT INTO commendmentCounter(commendeeID, commendeeName, commendmentCounter) VALUES(?,?,?)";

        try {
            connection = connectToDB("CounterDB");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("SELECT * FROM commendmentCounter");

            while (rs.next()) {
                if(rs.getLong("commendeeID") == commendeeID){
                    checkIfInDB = true;
                } else {
                    checkIfInDB = false;
                }
            }

            if(checkIfInDB){
                commendmentCounterValue = CheckForID.query(commendeeID);

                try (PreparedStatement pstmt = connection.prepareStatement(sqlUpdate)) {
                    pstmt.setInt(1, commendmentCounterValue+1);
                    pstmt.setLong(2,commendeeID);

                    pstmt.executeUpdate();
                } catch (SQLException e){
                    System.out.println(e.getMessage());
                }
            } else {
                try (PreparedStatement pstmt = connection.prepareStatement(sqlInsert)) {
                    System.out.println("Adding a new Person to the DB");
                    pstmt.setLong(1, commendeeID);
                    pstmt.setString(2, comendeeName);
                    pstmt.setInt(3,1);

                    pstmt.executeUpdate();
                } catch(SQLException e){
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try{
                if(connection != null)
                    connection.close();
            } catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }


}
