import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreate {
    public static void main (String[] args){

        Connection connection = null;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:./Databases/CounterDB.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists commendmentCounter");
            statement.executeUpdate("create table commendmentCounter (commendeeID long, commendeeName string, commendmentCounter integer)");

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}
