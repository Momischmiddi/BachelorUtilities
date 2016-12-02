package backend.database.dbConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection;
	private Statement statement;
	
	public DBConnection(Connection connection, Statement statement){
		this.connection = connection;
		this.statement = statement;
	}
	
	public void closeConnection(){
		try {
			statement.close();
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			System.out.println("Error closing Connection");
			e.printStackTrace();
		}
		
	}

	public Connection getConnection() {
		return connection;
	}
	public Statement getStatement() {
		return statement;
	}
}
