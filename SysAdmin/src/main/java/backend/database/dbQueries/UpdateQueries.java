package backend.database.dbQueries;

import backend.database.DBStructure;
import backend.database.dbConnection.DBConnection;

public class UpdateQueries {

	private DBConnection connectin;

	public UpdateQueries(DBConnection connection){
		this.connectin = connection;
	}
	
	public void updateTopic(int topicID, String referenceTable, int referenceTableID){
		String updateTopicStatement = 
				  "UPDATE " + DBStructure.TABLE_TOPIC
				+ "SET " + referenceTable + "=" + referenceTableID
				+ "WHERE ID =" + topicID;
	}
}
