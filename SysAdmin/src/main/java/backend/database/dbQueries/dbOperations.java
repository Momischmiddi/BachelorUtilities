package backend.database.dbQueries;

import java.sql.SQLException;
import java.sql.Statement;

import backend.database.dbConnection.DBConnection;

public abstract class dbOperations {

	
	public static String deleteLastCharComma(String str) {
		if (str.substring(str.length() - 1).equals(",")) {
			return str.substring(0, str.length() - 1)+" ";
		} else {
			return str;
		}
	}
	
	public static Statement getNewStatement(DBConnection connection) {
		try {
			Statement tmpStatement = connection.getConnection().createStatement();
			return tmpStatement;
		} catch (SQLException e1) {
		}
		return null;
	}
}
