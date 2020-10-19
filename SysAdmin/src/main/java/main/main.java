package main;

import java.util.ArrayList;

import backend.database.dbClasses.Author;
import backend.database.dbClasses.Date;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.Grade;
import backend.database.dbClasses.SecondOpinion;
import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbConnection.DBCredentials;
import backend.database.dbConnection.DBOpenConnection;
import backend.database.dbQueries.InsertQueries;
import backend.database.dbQueries.SearchQueries;

public class main {

	public static void main(String[] args) {
		DBCredentials credentials = new DBCredentials();
		credentials.setHostAdress("localhost");
		credentials.setPort(3306);
		credentials.setDatabase("BachelorUtilities");
		credentials.setUsername("root");
		credentials.setPassword("");
		
		DBOpenConnection openConnection = new DBOpenConnection();
		try {
			DBConnection connection = openConnection.createConnection(credentials);
			InsertQueries insert = new InsertQueries(connection);
			SearchQueries search = new SearchQueries(connection);
			ArrayList<Topic> topics = search.searchAllTopics();
			Topic topic = search.getSpecificTopic(3);
			connection.closeConnection();
		} catch (Exception e) {
			System.err.println("Error establishing connection");
			e.printStackTrace();
		}
	}

}
