package backend.database.dbQueries;

import java.sql.SQLException;
import java.sql.Statement;

import backend.database.DBStructure;
import backend.database.dbConnection.DBConnection;

public class Init {

	private DBConnection dbconnection;
	
	public Init(DBConnection dbconnection) {
		this.dbconnection = dbconnection;
		createDatabase();
		createTables();
	}
	
	private void createDatabase(){
		String createTable = "CREATE DATABASE IF NOT EXISTS " + DBStructure.MAIN_DATABASE + "";
		try {
			dbconnection.getStatement().execute(createTable);
		} catch (SQLException e) {
			System.out.println("Error creating Database");
			e.printStackTrace();
		}
		System.out.println("Successful created database "+ DBStructure.MAIN_DATABASE +"");
	}
	
	private void createTables(){
		Statement statement = dbconnection.getStatement();
		try {
			createAuthor(statement);
			createExpertOpinion(statement);
			createSecondOpinion(statement);
			createGrade(statement);
			createTopic(statement);
			createDate(statement);
			System.out.println("Successfully created tables");
		} catch (SQLException e) {
			System.out.println("Error creating Tables");
			e.printStackTrace();
		}		
		// Comment this, if something is unknown
//		try {
//			statement.execute("ALTER TABLE "+ DBStructure.TABLE_SECOND_OPINION +" ADD " + DBStructure.TABLE_EXPERT_OPINION_TITLE + " VARCHAR(50)");
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
	}

	private void createTopic(Statement statement) throws SQLException {
		String createTable = 
				  "CREATE TABLE IF NOT EXISTS " + DBStructure.TABLE_TOPIC + ""
				+ "("
					+ "ID INT NOT NULL AUTO_INCREMENT,"
					+ "" + DBStructure.TABLE_TOPIC_TITLE + " VARCHAR(512) NOT NULL,"
					+ "" + DBStructure.TABLE_TOPIC_DESCRIPTION + " VARCHAR(2048),"
					+ "" + DBStructure.TABLE_TOPIC_AUTHOR + " INT,"
					+ "" + DBStructure.TABLE_TOPIC_GRADE + " INT,"
					+ "" + DBStructure.TABLE_TOPIC_EXPERT_OPINION + " INT,"
					+ "" + DBStructure.TABLE_TOPIC_SECOND_OPINION+ " INT,"
					+ "" + DBStructure.TABLE_TOPIC_STATE + " BOOL,"
					+ "PRIMARY KEY(ID),"
					+ "FOREIGN KEY(" + DBStructure.TABLE_TOPIC_AUTHOR + ") REFERENCES " 
							+ DBStructure.TABLE_AUTHOR + "(ID),"
					+ "FOREIGN KEY(" + DBStructure.TABLE_TOPIC_GRADE +") REFERENCES " 
							+ DBStructure.TABLE_GRADE + "(ID),"
					+ "FOREIGN KEY(" + DBStructure.TABLE_TOPIC_EXPERT_OPINION + ") "
							+ "REFERENCES " + DBStructure.TABLE_EXPERT_OPINION +"(ID),"
					+ "FOREIGN KEY(" + DBStructure.TABLE_TOPIC_SECOND_OPINION + ") "
							+ "REFERENCES " + DBStructure.TABLE_SECOND_OPINION + "(ID)"
				+ ");";
		statement.execute(createTable);
	}
	
	private void createAuthor(Statement statement) throws SQLException {
		String createTable = 
				  "CREATE TABLE IF NOT EXISTS " + DBStructure.TABLE_AUTHOR + ""
				+ "("
					+ "ID INT NOT NULL AUTO_INCREMENT,"
					+ "" + DBStructure.TABLE_AUTHOR_NAME + " VARCHAR(50),"
					+ "" + DBStructure.TABLE_AUTHOR_FORENAME + " VARCHAR(25),"
					+ "" + DBStructure.TABLE_AUTHOR_MATRICULATIONNR + " INT,"
					+ "PRIMARY KEY(ID)"
				+ ");";
		statement.execute(createTable);
	}
	
	private void createDate(Statement statement) throws SQLException {
		String createTable = 
				  "CREATE TABLE IF NOT EXISTS " + DBStructure.TABLE_DATE + ""
				+ "("
					+ "ID INT NOT NULL AUTO_INCREMENT,"
					+ "" + DBStructure.TABLE_DATE_NAME + " VARCHAR(512),"
					+ "" + DBStructure.TABLE_DATE_MEETING_DATE + " DATE,"
					+ "" + DBStructure.TABLE_DATE_TOPIC + " INT ,"
					+ "PRIMARY KEY(ID),"
					+ "FOREIGN KEY(topic) REFERENCES " + DBStructure.TABLE_TOPIC + "(ID)"
				+ ");";
		statement.execute(createTable);
	}

	private void createGrade(Statement statement) throws SQLException {
		String createTable = 
				  "CREATE TABLE IF NOT EXISTS " + DBStructure.TABLE_GRADE + ""
				+ "("
					+ "ID INT NOT NULL AUTO_INCREMENT,"
					+ "" + DBStructure.TABLE_GRADE_GRADE + " DOUBLE PRECISION,"
					+ "PRIMARY KEY(ID)"
				+ ");";
		statement.execute(createTable);
	}

	private void createExpertOpinion(Statement statement) throws SQLException {
		String createTable = 
				  "CREATE TABLE IF NOT EXISTS " + DBStructure.TABLE_EXPERT_OPINION + ""
				+ "("
					+ "ID INT NOT NULL AUTO_INCREMENT,"
					+ "" + DBStructure.TABLE_EXPERT_OPINION_TITLE + " VARCHAR(50),"
					+ "" + DBStructure.TABLE_EXPERT_OPINION_NAME + " VARCHAR(50),"
					+ "" + DBStructure.TABLE_EXPERT_OPINION_FORENAME + " VARCHAR(25),"
					+ "" + DBStructure.TABLE_EXPERT_OPINION_OPINION + "  VARCHAR(2048),"
					+ "PRIMARY KEY(ID)"
				+ ");";
		statement.execute(createTable);
	}

	private void createSecondOpinion(Statement statement) throws SQLException {
		String createTable = 
				  "CREATE TABLE IF NOT EXISTS " + DBStructure.TABLE_SECOND_OPINION + ""
				+ "("
					+ "ID INT NOT NULL AUTO_INCREMENT,"
					+ "" + DBStructure.TABLE_SECOND_OPINION_TITLE + " VARCHAR(50),"
					+ "" + DBStructure.TABLE_SECOND_OPINION_NAME + " VARCHAR(50),"
					+ "" + DBStructure.TABLE_SECOND_OPINION_FORENAME + " VARCHAR(25),"
					+ "" + DBStructure.TABLE_SECOND_OPINION_OPINION + " VARCHAR(2048),"
					+ "PRIMARY KEY(ID)"
				+ ");";
		statement.execute(createTable);
	}
}

/********SQL RAW************
CREATE TABLE IF NOT EXISTS Author(
ID INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50),
forename VARCHAR(25),
matriculationNumber VARCHAR(512),
   PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS Expert_Opinion(
ID INT NOT NULL AUTO_INCREMENT ,
name VARCHAR(50),
forename VARCHAR(25),
opinion VARCHAR(2048),
  PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS Second_Opinion(
ID INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50),
forename VARCHAR(25),
opinion VARCHAR(2048),
  PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS Grade(
ID INT NOT NULL AUTO_INCREMENT,
grade DOUBLE PRECISION,
PRIMARY KEY(ID)
);
              
CREATE TABLE IF NOT EXISTS Topic(
ID INT NOT NULL AUTO_INCREMENT,
title VARCHAR(512) NOT NULL,
topic_description VARCHAR(2048),
author INT,
grade INT,
expert_opinion INT,
second_opinion INT,
PRIMARY KEY(ID),
FOREIGN KEY(author) REFERENCES Author(ID),
FOREIGN KEY(grade) REFERENCES Grade(ID),
FOREIGN KEY(expert_opinion) REFERENCES Expert_Opinion(ID),
FOREIGN KEY(second_opinion) REFERENCES Second_Opinion(ID)
);

CREATE TABLE IF NOT EXISTS Date(
ID INT NOT NULL AUTO_INCREMENT,
name VARCHAR(512),
meeting_date DATE,
topic INT ,
PRIMARY KEY(ID),
FOREIGN KEY(topic) REFERENCES Topic(ID)
);
 */

