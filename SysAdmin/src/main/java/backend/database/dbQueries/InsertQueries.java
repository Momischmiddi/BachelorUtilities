package backend.database.dbQueries;

import java.sql.ResultSet;
import java.sql.SQLException;

import backend.database.DBStructure;
import backend.database.dbClasses.Author;
import backend.database.dbClasses.Date;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.Grade;
import backend.database.dbClasses.SecondOpinion;
import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbExceptions.*;;

public class InsertQueries {
	
	private DBConnection dbconnection;
	private Exception NoTitleException;
	private UpdateQueries update;
	
	
	public InsertQueries(DBConnection dbconnection){
		this.dbconnection = dbconnection;
		update = new UpdateQueries(dbconnection);
	}
	
	/*
	 * Inserts for all Class entries in Topic-Data to Database
	 */
	public void insertNewTopic(Topic topic) throws Exception, NoTitleException{
		String insertInTopicStatement = createInsertNewTopicStatement(topic);
		dbconnection.getStatement().execute(insertInTopicStatement);
		int topicID = getLastInsertedKey();
		executeInsertStatement(topic.getAuthor(),topicID);
		executeInsertStatement(topic.getGrade(),topicID);
		executeInsertStatement(topic.getExpertOpinion(),topicID);
		executeInsertStatement(topic.getSecondOpinion(),topicID);
	}
	/*
	 * Inserts the specific Class to Database
	 */
	public void executeInsertStatement(Object DBTable, int topicID) throws Exception, NoTitleException{
		Boolean isSaved;//ToDo: Rollback bei false
		
		if(DBTable instanceof Grade){
			insertNewGrade((Grade) DBTable);
			isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_GRADE);
		}
		if(DBTable instanceof Author){
			insertNewAuthor((Author) DBTable);
			isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_AUTHOR);
		}
		if(DBTable instanceof Date){
			insertNewDate((Date) DBTable, topicID);
		}
		if(DBTable instanceof SecondOpinion){
			insertNewSecondOpinion((SecondOpinion) DBTable);
			isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_SECOND_OPINION);
		}
		if(DBTable instanceof ExpertOpinion){
			insertNewExpertOpinion((ExpertOpinion) DBTable);
			isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_EXPERT_OPINION);
		}
	}

	private boolean makeReferenceToTopic(int topicID, String referenceTable) {
		int insertedDataID = getLastInsertedKey();
		if(insertedDataID != DBStructure.DBError.NO_PRIMARY_KEY.getValue()){
			update.updateTopic(topicID, referenceTable, insertedDataID);
			return true;
		}
		return false;
	}

	private int getLastInsertedKey() {
		ResultSet lastKeyResultSet;
		try {
			lastKeyResultSet = dbconnection.getStatement().getGeneratedKeys();
			try {
				if(lastKeyResultSet.next())
				{
					return lastKeyResultSet.getInt(1);
				}
			} catch (SQLException e) {
				System.err.println("Error while opening the ResultSet from getting Primary Key from inserted data");
				e.printStackTrace();
			}finally{
				try {
					lastKeyResultSet.close();
				} catch (SQLException e) {
					System.err.println("Error closing lastKeyResultSet");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.err.println("Error while getting Primary Key from inserted data");
			e.printStackTrace();
		}
		return DBStructure.DBError.NO_PRIMARY_KEY.getValue();
	}

	private String deleteLastCharComma(String str) {
		if(str.substring(str.length()-1).equals(",")){
			return str.substring(0, str.length()-1);
		}else{
			return str;
		}
	}
	
	private void insertNewExpertOpinion(ExpertOpinion expertOpinion) {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_AUTHOR + " (";
		String insertValues = "VALUES(";
		
		if(expertOpinion != null){
			if(expertOpinion.getForename() != null){
				insertStatement += DBStructure.TABLE_EXPERT_OPINION_FORENAME + ",";
				insertValues += expertOpinion.getForename() + ",";
			}
			if(expertOpinion.getName() != null){
				insertStatement += DBStructure.TABLE_EXPERT_OPINION_NAME + ",";
				insertValues += expertOpinion.getName() + ",";
			}
			if(expertOpinion.getOpinion() != null){
				insertStatement += DBStructure.TABLE_EXPERT_OPINION_OPINION;
				insertValues += expertOpinion.getOpinion();
			}
		}
		
		insertStatement= deleteLastCharComma(insertStatement);
		insertValues = deleteLastCharComma(insertValues);
		
		insertStatement += ")";
		insertValues += ");";
		
		try {
			dbconnection.getStatement().execute(insertStatement+insertValues);
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
		}
	}

	private void insertNewSecondOpinion(SecondOpinion secondOpinion) {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_AUTHOR + " (";
		String insertValues = "VALUES(";
		
		if(secondOpinion != null){
			if(secondOpinion.getForename() != null){
				insertStatement += DBStructure.TABLE_SECOND_OPINION_FORENAME + ",";
				insertValues += secondOpinion.getForename() + ",";
			}
			if(secondOpinion.getName() != null){
				insertStatement += DBStructure.TABLE_SECOND_OPINION_NAME + ",";
				insertValues += secondOpinion.getName() + ",";
			}
			if(secondOpinion.getOpinion() != null){
				insertStatement += DBStructure.TABLE_SECOND_OPINION_OPINION;
				insertValues += secondOpinion.getOpinion();
			}
		}
		
		insertStatement= deleteLastCharComma(insertStatement);
		insertValues = deleteLastCharComma(insertValues);
		
		insertStatement += ")";
		insertValues += ");";
		
		try {
			dbconnection.getStatement().execute(insertStatement+insertValues);
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
		}
	}

	private void insertNewDate(Date date, int topicID) {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_AUTHOR + " (";
		String insertValues = "VALUES(";
		
		if(date != null){
			if(date.getDate() != null){
				insertStatement += DBStructure.TABLE_DATE_MEETING_DATE + ",";
				insertValues += date.getDate() + ",";
			}
			if(date.getName() != null){
				insertStatement += DBStructure.TABLE_DATE_NAME + ",";
				insertValues += date.getName() + ",";
			}
		}
		
		insertStatement += DBStructure.TABLE_DATE_TOPIC;
		insertValues += topicID;
		
		insertStatement += ")";
		insertValues += ");";
		
		try {
			dbconnection.getStatement().execute(insertStatement+insertValues);
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
		}
	}

	private void insertNewAuthor(Author author) {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_AUTHOR + " (";
		String insertValues = "VALUES(";
		
		if(author != null){
			if(author.getForename() != null){
				insertStatement += DBStructure.TABLE_AUTHOR_FORENAME + ",";
				insertValues += author.getForename() + ",";
			}
			if(author.getName() != null){
				insertStatement += DBStructure.TABLE_AUTHOR_NAME + ",";
				insertValues += author.getName() + ",";
			}
			if(author.getMatriculationNumber()>0){
				insertStatement += DBStructure.TABLE_AUTHOR_MATRICULATIONNR;
				insertValues += author.getMatriculationNumber();
			}
		}
				
		insertStatement= deleteLastCharComma(insertStatement);
		insertValues = deleteLastCharComma(insertValues);
		
		insertStatement += ")";
		insertValues += ");";
		
		try {
			dbconnection.getStatement().execute(insertStatement+insertValues);
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
		}
	}

	private void insertNewGrade(Grade grade) {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_GRADE + " (";
		String insertValues = "VALUES(";
		
		if(grade!= null){
			insertStatement += DBStructure.TABLE_GRADE_GRADE;
			insertValues += grade.getGrade();
		}
				
		insertStatement += ")";
		insertValues += ");";
		
		try {
			dbconnection.getStatement().execute(insertStatement+insertValues);
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
		}
	}

	private String createInsertNewTopicStatement(Topic topic) throws Exception, NoTitleException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_TOPIC + " (";
		String insertValues = "VALUES(";
		
		if(topic.getTitle() != null){
			insertStatement += DBStructure.TABLE_TOPIC_TITLE;
			insertValues += topic.getTitle() ;
		}else{
			throw NoTitleException;
		}
		if(topic.getDescription() != null){
			insertStatement += "," + DBStructure.TABLE_TOPIC_DESCRIPTION;
			insertValues += "," + topic.getDescription();
		}
		
		insertStatement += ")";
		insertValues += ");";
		return insertStatement+insertValues;
	}
}
