package backend.database.dbQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import backend.database.DBStructure;
import backend.database.dbConnection.DBConnection;

public class DeleteQueries {
	
	private DBConnection connection;
	
	public DeleteQueries(DBConnection connection){
		this.connection = connection;
	}
	
	public void deleteTopic(int topicID){
		
		String deleteQuery = 
				  "DELETE FROM " + DBStructure.TABLE_TOPIC 
				+ " WHERE ID = " + topicID;
		try {
			deleteDatesReferencedToTopic(topicID);
			connection.getStatement().executeUpdate(deleteQuery);
			deleteDataReferencedToTopic(topicID);
		} catch (SQLException e) {
			System.err.println("Error deleting Topic with ID: " + topicID);
			e.printStackTrace();
		}
	}

	private void deleteDatesReferencedToTopic(int topicID) {
		ArrayList<String> dates = new ArrayList<String>();
		String searchQuery = 
				  "SELECT ID"
				+ " FROM " + DBStructure.TABLE_DATE
				+ " WHERE " + DBStructure.TABLE_DATE_TOPIC + " = " + topicID;
		ResultSet searchResults = null;
		Statement tmpStatement = dbOperations.getNewStatement(connection);
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchQuery);
				while(searchResults.next()){
					deleteTableEntry(searchResults.getInt("ID"), 
							DBStructure.TABLE_DATE);
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_AUTHOR);
				e.printStackTrace();
			} 
			try {
				searchResults.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteDataReferencedToTopic(int topicID) {
		String searchQuery = 
				  "SELECT " + DBStructure.TABLE_TOPIC_AUTHOR + ", "
				  			+ DBStructure.TABLE_TOPIC_GRADE + ", " 
				  			+ DBStructure.TABLE_TOPIC_EXPERT_OPINION + ", "
				  			+ DBStructure.TABLE_TOPIC_SECOND_OPINION
				+ " FROM " + DBStructure.TABLE_TOPIC
				+ " WHERE ID = " + topicID;
		ResultSet searchResults = null;
		Statement tmpStatement = dbOperations.getNewStatement(connection);
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchQuery);
				if(searchResults.next()){
					deleteTableEntry(searchResults.getInt(DBStructure.TABLE_TOPIC_AUTHOR), DBStructure.TABLE_TOPIC_AUTHOR);
					deleteTableEntry(searchResults.getInt(DBStructure.TABLE_TOPIC_GRADE), DBStructure.TABLE_TOPIC_GRADE);
					deleteTableEntry(searchResults.getInt(DBStructure.TABLE_TOPIC_EXPERT_OPINION), DBStructure.TABLE_TOPIC_EXPERT_OPINION);
					deleteTableEntry(searchResults.getInt(DBStructure.TABLE_TOPIC_SECOND_OPINION), DBStructure.TABLE_TOPIC_SECOND_OPINION);
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_AUTHOR);
				e.printStackTrace();
			} 
			try {
				searchResults.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	private void deleteTableEntry(int entryID, String table) {
		if(entryID !=0){
			String deleteQuery = 
					  "DELETE FROM " + table
					+ " WHERE id = " + entryID;
			try {
				connection.getStatement().executeUpdate(deleteQuery);
			} catch (SQLException e) {
				System.err.println("Error Deleting Entry " + entryID + " in Table " + table);
				e.printStackTrace();
			}
		}
	}
}
