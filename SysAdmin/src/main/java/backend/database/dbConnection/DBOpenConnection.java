package backend.database.dbConnection;
import java.sql.Connection;
import java.sql.Statement;

import backend.database.dbQueries.Init;

import java.sql.DriverManager;
import java.sql.SQLException;


public class DBOpenConnection {

	public DBConnection createConnection(DBCredentials credentials) {
		DBConnection dbconnection = null;
		

		Connection connection = openConnection(credentials);
		if (null == connection) {
			return null;
		}
		try {
			dbconnection = new DBConnection(connection, createStatement(connection));
			new Init(dbconnection);
			return dbconnection;
		} catch (SQLException e) {
			System.out.println("Error establishing connection with credentials:\nHostAdress " +
								credentials.getHostAdress() + "\nPassword: " + 
								credentials.getPassword() + "\nPort" + 
								credentials.getPort() + "\nUserName" + 
								credentials.getUsername() + "\nDataBase" + credentials.getDatabase());
			System.exit(-1);
		}
		return null;
	}

	private Statement createStatement(Connection connection) throws SQLException {
		return connection.createStatement();
	}

	private Connection openConnection(DBCredentials credentials) {
		String url = "jdbc:mysql://" + credentials.getHostAdress() + 
				":"+ credentials.getPort() + 
				"/"+credentials.getDatabase();
        
		Connection connection = null;
        try {
        	Object instance = Class.forName("com.mysql.jdbc.Driver").newInstance();
        	connection = DriverManager.getConnection(url, credentials.getUsername(), credentials.getPassword());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        	System.out.println("Could not establish connection with :" + credentials.toString());
        	return null;
        }
        return connection;
    }
}