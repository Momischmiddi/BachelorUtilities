package backend.database.dbQueries;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import backend.database.dbConnection.DBConnection;

public class Init {

	private DBConnection dbconnection;
	
	public Init(DBConnection dbconnection) throws Exception{
		this.dbconnection = dbconnection;
		createTables();
	}
	
	private void createTables() throws Exception{
		Connection connection = dbconnection.getConnection();
		Statement statement = dbconnection.getStatement();
		
		String createTable = "CREATE TABLE IF NOT EXISTS Topic";
		
		statement.execute("");
		
	}
}
