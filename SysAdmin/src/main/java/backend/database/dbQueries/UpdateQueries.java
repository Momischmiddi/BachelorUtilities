package backend.database.dbQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backend.database.DBStructure;
import backend.database.dbClasses.Author;
import backend.database.dbClasses.Date;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.Grade;
import backend.database.dbClasses.SecondOpinion;
import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbExceptions.NoTitleException;

public class UpdateQueries {

	private DBConnection connection;

	public UpdateQueries(DBConnection connection){
		this.connection = connection;
	}
	
	public int updateForeignKeyToTopic(int topicID, String referenceTable, int referenceTableID){
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
	
	public int updateTopic(Topic topic){
		String insertInTopicStatement;
		try {
			insertInTopicStatement = createUpdateNewTopicStatement(topic.getID(), topic);
			connection.getStatement().executeUpdate(insertInTopicStatement, Statement.RETURN_GENERATED_KEYS);
			updateDataReferencedToTopic(topic.getID(), topic);
		} catch (SQLException e) {
				System.err.println("Error creating new Topic");
				e.printStackTrace();
				return DBStructure.DBError.INSERT_ERROR.getValue();
		} catch (NoTitleException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 1;
	}
	
	private String createUpdateNewTopicStatement(int entryID, Topic topic) throws NoTitleException {
		String insertStatement = "UPDATE " + DBStructure.TABLE_TOPIC ;
		String insertValues = " SET ";
		String whereClause = " WHERE ID = " + entryID;

		if (topic.getTitle() != null || !topic.getTitle().isEmpty()) {
			insertValues += DBStructure.TABLE_TOPIC_TITLE + "=";
			insertValues += "\"" + topic.getTitle() + "\"";
		} else {
			throw new NoTitleException();
		}
		if (topic.getDescription() != null) {
			insertValues += "," + DBStructure.TABLE_TOPIC_DESCRIPTION + "=";
			insertValues += "\"" + topic.getDescription() + "\"";
		}
		if (topic.isFinished() == 1) {
			insertValues += "," + DBStructure.TABLE_TOPIC_STATE + "=";
			insertValues += "\"" + topic.isFinished() + "\"";
		} else {
			insertValues += "," + DBStructure.TABLE_TOPIC_STATE + "=";
			insertValues += "\"" + topic.isFinished() + "\"";
		}

		return insertStatement + insertValues + whereClause;
	}
	
	private void updateDataReferencedToTopic(int topicID, Topic topic) {
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
					InsertQueries insert = new InsertQueries(connection);
					if(searchResults.getInt(DBStructure.TABLE_TOPIC_AUTHOR) == 0){
						executeUpdateStatement(searchResults.getInt(DBStructure.TABLE_TOPIC_AUTHOR), topic.getAuthor());
					}else{
						insert.executeInsertStatement(topic.getAuthor(), topic.getID());
					}
					if(searchResults.getInt(DBStructure.TABLE_TOPIC_GRADE) == 0){
						executeUpdateStatement(searchResults.getInt(DBStructure.TABLE_TOPIC_GRADE), topic.getGrade());					
					}else{
						insert.executeInsertStatement(topic.getGrade(), topic.getID());
					}
					if(searchResults.getInt(DBStructure.TABLE_TOPIC_EXPERT_OPINION) == 0){
						executeUpdateStatement(searchResults.getInt(DBStructure.TABLE_TOPIC_EXPERT_OPINION), topic.getExpertOpinion());
					}else{
						insert.executeInsertStatement(topic.getExpertOpinion(), topic.getID());
					}
					if(searchResults.getInt(DBStructure.TABLE_TOPIC_SECOND_OPINION) == 0){
						executeUpdateStatement(searchResults.getInt(DBStructure.TABLE_TOPIC_SECOND_OPINION), topic.getSecondOpinion());
					}else{
						insert.executeInsertStatement(topic.getSecondOpinion(), topic.getID());
					}
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_AUTHOR);
				e.printStackTrace();
			} catch (NoTitleException e){
				System.err.println("No Title");
			}
			try {
				searchResults.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}

	public int executeUpdateStatement(int entryID, Object DBTable) throws NoTitleException {
		Boolean isSaved;// ToDo: Rollback bei false
		try {
			if (DBTable instanceof Grade) {
				updateGrade(entryID, (Grade) DBTable);
			}
			if (DBTable instanceof Author) {
				updateAuthor(entryID, (Author) DBTable);
			}
			if (DBTable instanceof Date) {
				updateDate(entryID, (Date) DBTable);
			}
			if (DBTable instanceof SecondOpinion) {
				updateSecondOpinion(entryID, (SecondOpinion) DBTable);
			}
			if (DBTable instanceof ExpertOpinion) {
				updateExpertOpinion(entryID, (ExpertOpinion) DBTable);
			}
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
			return DBStructure.DBError.INSERT_ERROR.getValue();
		}
		return 1;
	}

	private void updateGrade(int entryID, Grade grade) throws SQLException {
		String insertStatement = "UPDATE " + DBStructure.TABLE_GRADE;
		String insertValues = " SET ";
		String whereClause = " WHERE ID = " + entryID;

		if (grade != null) {
			insertValues += DBStructure.TABLE_GRADE_GRADE + "=";
			insertValues += grade.getGrade();
		}
		
		insertValues = dbOperations.deleteLastCharComma(insertValues);
		connection.getStatement().executeUpdate(insertStatement + insertValues + whereClause, Statement.RETURN_GENERATED_KEYS);	
	}

	private void updateAuthor(int entryID, Author author) throws SQLException {
		String insertStatement = "UPDATE " + DBStructure.TABLE_AUTHOR;
		String insertValues = " SET ";
		String whereClause = " WHERE ID = " + entryID;

		if (author != null) {
			if (author.getForename() != null) {
				insertValues += DBStructure.TABLE_AUTHOR_FORENAME + "=";
				insertValues += "\"" + author.getForename() + "\",";
			}
			if (author.getName() != null) {
				insertValues += DBStructure.TABLE_AUTHOR_NAME + "=";
				insertValues += "\"" + author.getName() + "\",";
			}
			if (author.getMatriculationNumber() > 0) {
				insertValues += DBStructure.TABLE_AUTHOR_MATRICULATIONNR + "=";
				insertValues += author.getMatriculationNumber();
			}
		}
		
		
		insertValues = dbOperations.deleteLastCharComma(insertValues);
		connection.getStatement().executeUpdate(insertStatement + insertValues + whereClause, Statement.RETURN_GENERATED_KEYS);
	}

	private void updateDate(int entryID, Date date) throws SQLException {
		String insertStatement = "UPDATE " + DBStructure.TABLE_DATE;
		String insertValues = " SET ";
		String whereClause = " WHERE ID = " + entryID;

		if (date != null) {
			if (date.getDate() != null) {
				insertValues += DBStructure.TABLE_DATE_MEETING_DATE + "=";
				insertValues += "\"" + date.getDate() + "\",";
			}
			if (date.getName() != null) {
				insertValues += DBStructure.TABLE_DATE_NAME + "=";
				insertValues += "\"" + date.getName() + "\"";
			}
		}
		
		insertValues = dbOperations.deleteLastCharComma(insertValues);
		connection.getStatement().executeUpdate(insertStatement + insertValues + whereClause, Statement.RETURN_GENERATED_KEYS);
	}

	private void updateSecondOpinion(int entryID, SecondOpinion secondOpinion) throws SQLException {
		String insertStatement = "UPDATE " + DBStructure.TABLE_SECOND_OPINION ;
		String insertValues = " SET ";
		String whereClause = " WHERE ID = " + entryID;

		if (secondOpinion != null) {
			if (secondOpinion.getTitle() != null) {
				insertValues += DBStructure.TABLE_SECOND_OPINION_TITLE + "=";
				insertValues += "\"" + secondOpinion.getTitle() + "\",";
			}
			if (secondOpinion.getForename() != null) {
				insertValues += DBStructure.TABLE_SECOND_OPINION_FORENAME + "=";
				insertValues += "\"" + secondOpinion.getForename() + "\",";
			}
			if (secondOpinion.getName() != null) {
				insertValues += DBStructure.TABLE_SECOND_OPINION_NAME + "=";
				insertValues += "\"" + secondOpinion.getName() + "\",";
			}
			if (secondOpinion.getOpinion() != null) {
				insertValues += DBStructure.TABLE_SECOND_OPINION_OPINION + "=";
				insertValues += "\"" + secondOpinion.getOpinion() + "\"";
			}
		}		
		
		insertValues = dbOperations.deleteLastCharComma(insertValues);
		String update = insertStatement + insertValues + whereClause;
		connection.getStatement().executeUpdate(insertStatement + insertValues + whereClause, Statement.RETURN_GENERATED_KEYS);
	}

	private void updateExpertOpinion(int entryID, ExpertOpinion expertOpinion) throws SQLException {
		String insertStatement = "UPDATE " + DBStructure.TABLE_EXPERT_OPINION ;
		String insertValues = " SET ";
		String whereClause = " WHERE ID = " + entryID;

		if (expertOpinion != null) {
			if (expertOpinion.getTitle() != null) {
				insertValues += DBStructure.TABLE_EXPERT_OPINION_TITLE + "=";
				insertValues += "\"" + expertOpinion.getTitle() + "\",";
			}
			if (expertOpinion.getForename() != null) {
				insertValues += DBStructure.TABLE_EXPERT_OPINION_FORENAME + "=";
				insertValues += "\"" + expertOpinion.getForename() + "\",";
			}
			if (expertOpinion.getName() != null) {
				insertValues += DBStructure.TABLE_EXPERT_OPINION_NAME + "=";
				insertValues += "\"" + expertOpinion.getName() + "\",";
			}
			if (expertOpinion.getOpinion() != null) {
				insertValues += DBStructure.TABLE_EXPERT_OPINION_OPINION + "=";
				insertValues += "\"" + expertOpinion.getOpinion() + "\" ";
			}
		}

		insertValues = dbOperations.deleteLastCharComma(insertValues);
		connection.getStatement().executeUpdate(insertStatement + insertValues + " " + whereClause, Statement.RETURN_GENERATED_KEYS);
	}
	
}
