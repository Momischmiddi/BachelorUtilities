package backend.database.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection;
	private Statement statement;

	public DBConnection(Connection connection, Statement statement) {
		this.connection = connection;
		this.statement = statement;
	}

	public void closeConnection() {
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
		// for debug-reasons

		// ResultSet rs;
		// try {
		// rs = statement.executeQuery("select * from Topic");
		// ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		//
		// for (int i = 1; i < 10; i++) {
		// System.out.println("No. of columns : " + rsmd.getColumnCount());
		// System.out.println("Column name of "+ i + "st column : " +
		// rsmd.getColumnName(i));
		// System.out.println("Column type of "+ i + "st column : " +
		// rsmd.getColumnType(i));
		// System.out.println("Column Set of "+ i + "st column : " +
		// rsmd.getColumnCharacterSet(i));
		// System.out.println("Column className of "+ i + "st column : " +
		// rsmd.getColumnClassName(i));
		// System.out.println("Column label of "+ i + "st column : " +
		// rsmd.getColumnLabel(i));
		// System.out.println("__________________________________________________\n");
		// }
		// } catch (SQLException e) {
		// }
		return statement;
	}
}
