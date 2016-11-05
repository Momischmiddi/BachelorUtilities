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

public class SearchQueries {

	public DBConnection connection;
	
	public SearchQueries(DBConnection connection){
		this.connection = connection;
	}
	
	public Topic getSpecificTopic(int topicID){
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_TOPIC + " "
				+ "WHERE ID=" + topicID;
		try {
			ResultSet searchResults = connection.getStatement().executeQuery(searchString);
			if(searchResults.next()){
				Topic topic = getTopic(searchResults);
				return topic;
			}
			searchResults.close();
		} catch (SQLException e) {
			System.err.println("Error creating resultset for all topics");
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Topic> searchAllTopics(){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_TOPIC;
		try {
			ResultSet searchResults = connection.getStatement().executeQuery(searchString);
			
			while(searchResults.next()){
				Topic tmpTopic = getTopic(searchResults);
				if(tmpTopic != null){
					topics.add(tmpTopic);
				}
			}
			searchResults.close();
			return topics;
		} catch (SQLException e) {
			System.err.println("Error creating resultset for all topics");
			e.printStackTrace();
		}
		return null;
	}
	
	private Topic getTopic(ResultSet searchResults) {
		Topic tmpTopic = new Topic();
		
		try {
			tmpTopic.setID(searchResults.getInt("ID"));
			tmpTopic.setTitle(searchResults.getString(DBStructure.TABLE_TOPIC_TITLE));
			tmpTopic.setDescription(searchResults.getString(DBStructure.TABLE_TOPIC_DESCRIPTION));
			
			int authorID = searchResults.getInt(DBStructure.TABLE_TOPIC_AUTHOR);
			int gradeID = searchResults.getInt(DBStructure.TABLE_TOPIC_GRADE);
			int expertOpinionID = searchResults.getInt(DBStructure.TABLE_TOPIC_EXPERT_OPINION);
			int secondOpinionID = searchResults.getInt(DBStructure.TABLE_TOPIC_SECOND_OPINION);
			
			
			getAuthorAndSetToTopic(tmpTopic, authorID);
			getGradeAndSetToTopic(tmpTopic, gradeID);
			getExpertOpinionAndSetToTopic(tmpTopic, expertOpinionID);
			getSecondOpinionAndSetToTopic(tmpTopic, secondOpinionID);
			getDatesAndSetToTopic(tmpTopic, tmpTopic.getID());
			
			return tmpTopic;
		} catch (SQLException e) {
			System.err.println("Error getting " + DBStructure.TABLE_TOPIC + "from Database");
			e.printStackTrace();
		}
		return null;
	}
	
	private Author getAuthorFromDB(int authorID) {
		Author author = new Author();
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_AUTHOR + " "
				+ "WHERE ID=" + authorID;
		ResultSet searchResults = null;
		Statement tmpStatement = getNewStatement();
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchString);
				if(searchResults.next()){
					author.setForename(searchResults.getString(DBStructure.TABLE_AUTHOR_FORENAME));
					author.setName(searchResults.getString(DBStructure.TABLE_AUTHOR_NAME));
					author.setMatriculationNumber((searchResults.getInt(DBStructure.TABLE_AUTHOR_MATRICULATIONNR)));
					searchResults.close();
					return author;
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_AUTHOR);
				e.printStackTrace();
			} 
			try {
				searchResults.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
	private Statement getNewStatement() {
		try {
			Statement tmpStatement = connection.getConnection().createStatement();
			return tmpStatement;
		} catch (SQLException e1) {
		}
		return null;
	}

	private Grade getGradeFromDB(int gradeID) {
		Grade grade = new Grade();
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_GRADE + " "
				+ "WHERE ID=" + gradeID;
		ResultSet searchResults = null;
		Statement tmpStatement = getNewStatement();
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchString);
				if(searchResults.next()){
					grade.setGrade(searchResults.getDouble(DBStructure.TABLE_GRADE_GRADE));
					
					return grade;
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_GRADE);
				e.printStackTrace();
			}
			try {
				searchResults.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
	private ArrayList<Date> getDatesFromTopic(int topicID) {
		ArrayList<Date> dates = new ArrayList<Date>();
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_DATE + " "
				+ "WHERE " + DBStructure.TABLE_DATE_TOPIC + "=" + topicID;
		ResultSet searchResults = null;
		Statement tmpStatement = getNewStatement();
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchString);
				while(searchResults.next()){
					Date tmpDate = new Date();
					
					tmpDate.setName(searchResults.getString(DBStructure.TABLE_DATE_NAME));
					tmpDate.setDate(searchResults.getString(DBStructure.TABLE_DATE_MEETING_DATE));
					
					dates.add(tmpDate);
				}
				return dates;
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_DATE);
				e.printStackTrace();
			}
			try {
				searchResults.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
	private SecondOpinion getSecondOpinionFromDB(int secondOpinionID) {
		SecondOpinion secondOpinion = new SecondOpinion();
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_SECOND_OPINION + " "
				+ "WHERE ID=" + secondOpinionID;
		ResultSet searchResults = null;
		Statement tmpStatement = getNewStatement();
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchString);
				if(searchResults.next()){
					secondOpinion.setForename((searchResults.getString(DBStructure.TABLE_SECOND_OPINION_FORENAME)));
					secondOpinion.setName((searchResults.getString(DBStructure.TABLE_SECOND_OPINION_NAME)));
					secondOpinion.setOpinion((searchResults.getString(DBStructure.TABLE_SECOND_OPINION_OPINION)));
					
					return secondOpinion;
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_SECOND_OPINION);
				e.printStackTrace();
			}
			try {
				searchResults.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
	private ExpertOpinion getExpertOpinionFromDB(int expertOpinionID) {
		ExpertOpinion expertOpinion = new ExpertOpinion();
		String searchString = 
				  "SELECT * "
				+ "FROM " + DBStructure.TABLE_EXPERT_OPINION + " "
				+ "WHERE ID=" + expertOpinionID;	
		ResultSet searchResults = null;
		Statement tmpStatement = getNewStatement();
		if(tmpStatement != null){
			try {
				searchResults = tmpStatement.executeQuery(searchString);
				if(searchResults.next()){
					expertOpinion.setForename((searchResults.getString(DBStructure.TABLE_EXPERT_OPINION_FORENAME)));
					expertOpinion.setName((searchResults.getString(DBStructure.TABLE_EXPERT_OPINION_NAME)));
					expertOpinion.setOpinion((searchResults.getString(DBStructure.TABLE_EXPERT_OPINION_OPINION)));
					
					return expertOpinion;
				}
			} catch (SQLException e) {
				System.err.println("Error creating resultset for " + DBStructure.TABLE_EXPERT_OPINION);
				e.printStackTrace();
			}
			try {
				searchResults.close();
			} catch (SQLException e) {}
		}
		return null;
	}
	
	private void getAuthorAndSetToTopic(Topic tmpTopic, int authorID) {
		Author tmpAuthor;
		tmpAuthor = getAuthorFromDB(authorID);
		if(tmpAuthor != null){
			tmpTopic.setAuthor(tmpAuthor);
		}
	}
	
	private void getGradeAndSetToTopic(Topic tmpTopic, int gradeID) {
		Grade tmpGrade;
		tmpGrade = getGradeFromDB(gradeID);
		if(tmpGrade != null){
			tmpTopic.setGrade(tmpGrade);
		}
	}
	
	private void getDatesAndSetToTopic(Topic topic, int topicID) {
		ArrayList<Date> tmpDates;
		tmpDates = getDatesFromTopic(topicID);
		if(tmpDates != null){
			topic.setDate(tmpDates);
		}
	}

	private void getSecondOpinionAndSetToTopic(Topic tmpTopic, int secondOpinionID) {
		SecondOpinion tmpSecondOpinion;
		tmpSecondOpinion = getSecondOpinionFromDB(secondOpinionID);
		if(tmpSecondOpinion != null){
			tmpTopic.setSecondOpinion(tmpSecondOpinion);
		}
	}

	private void getExpertOpinionAndSetToTopic(Topic tmpTopic, int expertOpinionID) {
		ExpertOpinion expertOpinion;
		expertOpinion = getExpertOpinionFromDB(expertOpinionID);
		if(expertOpinion != null){
			tmpTopic.setExpertOpinion(expertOpinion);
		}
	}
}
