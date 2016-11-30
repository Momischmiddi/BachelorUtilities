package backend.database.dbConnection;
import java.sql.Connection;
import java.sql.Statement;

import backend.database.dbQueries.Init;

import java.sql.DriverManager;
import java.sql.SQLException;


public class DBOpenConnection {

	public DBConnection createConnection(DBCredentials credentials) throws Exception{
		DBConnection dbconnection;
		
		Connection connection = openConnection(credentials);
		Statement statement = createStatement(connection);
		
		dbconnection = new DBConnection(connection, statement);
		
		new Init(dbconnection);
		
		return dbconnection;
	}

	private Statement createStatement(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
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
	

}