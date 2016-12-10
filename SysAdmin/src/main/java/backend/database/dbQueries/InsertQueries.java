package backend.database.dbQueries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import backend.database.DBStructure;
import backend.database.dbClasses.Author;
import backend.database.dbClasses.Date;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.Grade;
import backend.database.dbClasses.SecondOpinion;
import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbExceptions.NoTitleException;

public class InsertQueries {

	private DBConnection dbconnection;
	public Exception NoTitleException;
	private UpdateQueries update;

	public InsertQueries(DBConnection dbconnection) {
		this.dbconnection = dbconnection;
		update = new UpdateQueries(dbconnection);
	}

	/*
	 * Inserts for all Class entries in Topic-Data to Database Returns
	 * INSERT_ERROR if there was an error
	 */
	public int insertNewTopic(Topic topic) throws NoTitleException {
		String insertInTopicStatement = createInsertNewTopicStatement(topic);
		try {
			dbconnection.getStatement().executeUpdate(insertInTopicStatement, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			System.err.println("Error creating new Topic");
			e.printStackTrace();
			return DBStructure.DBError.INSERT_ERROR.getValue();
		}
		int topicID = getLastInsertedKey();
		int insertStatus;
		insertStatus = executeInsertStatement(topic.getAuthor(), topicID);
		insertStatus = executeInsertStatement(topic.getDate(), topicID);
		insertStatus = executeInsertStatement(topic.getGrade(), topicID);
		insertStatus = executeInsertStatement(topic.getExpertOpinion(), topicID);
		insertStatus = executeInsertStatement(topic.getSecondOpinion(), topicID);
		return insertStatus;
	}

	/*
	 * Inserts the specific Class to Database
	 */
	public int executeInsertStatement(Object DBTable, int topicID) throws NoTitleException {
		Boolean isSaved;// ToDo: Rollback bei false
		try {
			System.out.println(DBTable.getClass());
			if (DBTable instanceof Grade) {
				insertNewGrade((Grade) DBTable);
				isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_GRADE);
			}
			if (DBTable instanceof Author) {
				insertNewAuthor((Author) DBTable);
				isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_AUTHOR);
			}
			if (DBTable instanceof ArrayList) {
				for (Date dateToInsert : (ArrayList<Date>)DBTable)
				insertNewDate((Date) dateToInsert, topicID);
			}
			if (DBTable instanceof SecondOpinion) {
				insertNewSecondOpinion((SecondOpinion) DBTable);
				isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_SECOND_OPINION);
			}
			if (DBTable instanceof ExpertOpinion) {
				insertNewExpertOpinion((ExpertOpinion) DBTable);
				isSaved = makeReferenceToTopic(topicID, DBStructure.TABLE_EXPERT_OPINION);
			}
		} catch (SQLException e) {
			System.err.println("Error inserting data to Database");
			e.printStackTrace();
			return DBStructure.DBError.INSERT_ERROR.getValue();
		}
		return 1;
	}

	private boolean makeReferenceToTopic(int topicID, String referenceTable) {
		int insertedDataID = getLastInsertedKey();
		if (insertedDataID != DBStructure.DBError.NO_PRIMARY_KEY.getValue()) {
			int updateStatus = update.updateForeignKeyToTopic(topicID, referenceTable, insertedDataID);
			if (updateStatus == DBStructure.DBError.UPDATE_REFERENCE_TO_TOPIC_ERROR.getValue()) {
				return false;
			}
			return true;
		}
		return false;
	}

	private int getLastInsertedKey() {
		ResultSet lastKeyResultSet;
		try {
			lastKeyResultSet = dbconnection.getStatement().getGeneratedKeys();
			try {
				if (lastKeyResultSet.next()) {
					return lastKeyResultSet.getInt(1);
				}
			} catch (SQLException e) {
				System.err.println("Error while opening the ResultSet from getting Primary Key from inserted data");
				e.printStackTrace();
			} finally {
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

	private void insertNewExpertOpinion(ExpertOpinion expertOpinion) throws SQLException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_EXPERT_OPINION + " (";
		String insertValues = "VALUES(";

		if (expertOpinion != null) {
			if (expertOpinion.getForename() != null) {
				insertStatement += DBStructure.TABLE_EXPERT_OPINION_FORENAME + ",";
				insertValues += "\"" + expertOpinion.getForename() + "\",";
			}
			if (expertOpinion.getName() != null) {
				insertStatement += DBStructure.TABLE_EXPERT_OPINION_NAME + ",";
				insertValues += "\"" + expertOpinion.getName() + "\",";
			}
			if (expertOpinion.getOpinion() != null) {
				insertStatement += DBStructure.TABLE_EXPERT_OPINION_OPINION;
				insertValues += "\"" + expertOpinion.getOpinion() + "\"";
			}
		}

		insertStatement = dbOperations.deleteLastCharComma(insertStatement);
		insertValues = dbOperations.deleteLastCharComma(insertValues);

		insertStatement += ")";
		insertValues += ");";

		dbconnection.getStatement().executeUpdate(insertStatement + insertValues, Statement.RETURN_GENERATED_KEYS);
	}

	private void insertNewSecondOpinion(SecondOpinion secondOpinion) throws SQLException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_SECOND_OPINION + " (";
		String insertValues = "VALUES(";

		if (secondOpinion != null) {
			if (secondOpinion.getForename() != null) {
				insertStatement += DBStructure.TABLE_SECOND_OPINION_FORENAME + ",";
				insertValues += "\"" + secondOpinion.getForename() + "\",";
			}
			if (secondOpinion.getName() != null) {
				insertStatement += DBStructure.TABLE_SECOND_OPINION_NAME + ",";
				insertValues += "\"" + secondOpinion.getName() + "\",";
			}
			if (secondOpinion.getOpinion() != null) {
				insertStatement += DBStructure.TABLE_SECOND_OPINION_OPINION;
				insertValues += "\"" + secondOpinion.getOpinion() + "\"";
			}
		}

		insertStatement = dbOperations.deleteLastCharComma(insertStatement);
		insertValues = dbOperations.deleteLastCharComma(insertValues);

		insertStatement += ")";
		insertValues += ");";

		dbconnection.getStatement().executeUpdate(insertStatement + insertValues, Statement.RETURN_GENERATED_KEYS);
	}

	private void insertNewDate(Date date, int topicID) throws SQLException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_DATE + " (";
		String insertValues = "VALUES(";

		if (date != null) {
			if (date.getDate() != null) {
				insertStatement += DBStructure.TABLE_DATE_MEETING_DATE + ",";
				insertValues += "\"" + date.getDate() + "\",";
			}
			if (date.getName() != null) {
				insertStatement += DBStructure.TABLE_DATE_NAME + ",";
				insertValues += "\"" + date.getName() + "\",";
			}
		}

		insertStatement += DBStructure.TABLE_DATE_TOPIC;
		insertValues += topicID;

		insertStatement += ")";
		insertValues += ");";

		dbconnection.getStatement().executeUpdate(insertStatement + insertValues, Statement.RETURN_GENERATED_KEYS);
	}

	private void insertNewAuthor(Author author) throws SQLException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_AUTHOR + " (";
		String insertValues = "VALUES(";

		if (author != null) {
			if (author.getForename() != null) {
				insertStatement += DBStructure.TABLE_AUTHOR_FORENAME + ",";
				insertValues += "\"" + author.getForename() + "\",";
			}
			if (author.getName() != null) {
				insertStatement += DBStructure.TABLE_AUTHOR_NAME + ",";
				insertValues += "\"" + author.getName() + "\",";
			}
			if (author.getMatriculationNumber() > 0) {
				insertStatement += DBStructure.TABLE_AUTHOR_MATRICULATIONNR;
				insertValues += author.getMatriculationNumber();
			}
		}

		insertStatement = dbOperations.deleteLastCharComma(insertStatement);
		insertValues = dbOperations.deleteLastCharComma(insertValues);

		insertStatement += ")";
		insertValues += ");";

		dbconnection.getStatement().executeUpdate(insertStatement + insertValues, Statement.RETURN_GENERATED_KEYS);
	}

	private void insertNewGrade(Grade grade) throws SQLException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_GRADE + " (";
		String insertValues = "VALUES(";

		if (grade != null) {
			insertStatement += DBStructure.TABLE_GRADE_GRADE;
			insertValues += grade.getGrade();
		}

		insertStatement += ")";
		insertValues += ");";

		dbconnection.getStatement().executeUpdate(insertStatement + insertValues, Statement.RETURN_GENERATED_KEYS);
	}

	private String createInsertNewTopicStatement(Topic topic) throws NoTitleException {
		String insertStatement = "INSERT INTO " + DBStructure.TABLE_TOPIC + " (";
		String insertValues = "VALUES(";

		if (topic.getTitle() != null || !topic.getTitle().isEmpty()) {
			insertStatement += DBStructure.TABLE_TOPIC_TITLE;
			insertValues += "\"" + topic.getTitle() + "\"";
		} else {
			throw new NoTitleException();
		}
		if (topic.getDescription() != null) {
			insertStatement += "," + DBStructure.TABLE_TOPIC_DESCRIPTION;
			insertValues += "," + "\"" + topic.getDescription() + "\"";
		}
		if (topic.isFinished() == 1) {
			insertStatement += "," + DBStructure.TABLE_TOPIC_STATE;
			insertValues += "," + "\"" + topic.isFinished() + "\"";
		} else {
			insertStatement += "," + DBStructure.TABLE_TOPIC_STATE;
			insertValues += "," + "\"" + topic.isFinished() + "\"";
		}

		insertStatement += ")";
		insertValues += ");";
		return insertStatement + insertValues;
	}
}
