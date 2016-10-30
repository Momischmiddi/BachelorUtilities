package backend.database.dbConnection;
import java.sql.Connection;
import java.sql.Statement;

public class DBConnection {

	private Connection connection;
	private Statement statement;
	
	public DBConnection(Connection connection, Statement statement){
		this.connection = connection;
		this.statement = statement;
	}

	public Connection getConnection() {
		return connection;
	}
	public Statement getStatement() {
		return statement;
	}
}
