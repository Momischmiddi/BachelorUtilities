package backend.database.dbConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBOpenConnection {

	public DBConnection createConnection(DBCredentials credentials) throws Exception{
		DBConnection dbconnection;
		
		Connection connection = openConnection(credentials);
		Statement statement = createStatement(connection);
		
		dbconnection = new DBConnection(connection, statement);
		return dbconnection;
	}

	private Statement createStatement(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		
		/*String createTable = "CREATE DATABASE IF NOT EXISTS BachelorUtilities";
		statement.execute(createTable);
		System.out.println("Successful created database BachelorUtilities");*/
		return statement;
	}

	private Connection openConnection(DBCredentials credentials) throws Exception {
		String url = "jdbc:mysql://" + credentials.getHostAdress() + 
				":"+ credentials.getPort() + "/"+credentials.getDatabase();
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection(url, 
            		credentials.getUsername(), 
            		credentials.getPassword());
        
		return connection;
    }
	
	private void test(){
String url = "jdbc:mysql://localhost:3306/";
        
        /**
         * The MySQL user.
         */
        String user = "root";
        
        /**
         * Password for the above MySQL user. If no password has been 
         * set (as is the default for the root user in XAMPP's MySQL),
         * an empty string can be used.
         */
        String password = "";
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, user, password);
            
            Statement stt = con.createStatement();
            
     
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

}