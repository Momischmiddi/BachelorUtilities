package backend.database.dbQueries;

import java.sql.SQLException;

import backend.database.DBStructure;
import backend.database.dbConnection.DBConnection;

public class UpdateQueries {

	private DBConnection connection;

	public UpdateQueries(DBConnection connection){
		this.connection = connection;
	}
	
	public int updateTopic(int topicID, String referenceTable, int referenceTableID){
		String updateTopicStatement = 
				  "UPDATE " + DBStructure.TABLE_TOPIC + " "
				+ "SET " + referenceTable + "=" + referenceTableID + " "
				+ "WHERE ID=" + topicID;
		try {
			connection.getStatement().executeUpdate(updateTopicStatement);
		} catch (SQLException e) {
			System.err.println("Error updating reference to topic");
			e.printStackTrace();
			return DBStructure.DBError.UPDATE_REFERENCE_TO_TOPIC_ERROR.getValue();
		}
		return 1;
	}
}
