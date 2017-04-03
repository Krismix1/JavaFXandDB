package technicalservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Chris on 24-Mar-17.
 */
public class DBConnection
{
    private final static String URL = "jdbc:mysql://localhost:3306/";
    private final static String DB_NAME = "dat16j";
    private final static String USER = "root";
    private final static String PASSWORD = "";


    public static Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
            return con;
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
