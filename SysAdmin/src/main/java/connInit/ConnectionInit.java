package connInit;

import backend.database.dbConnection.DBConnection;
import backend.database.dbConnection.DBCredentials;
import backend.database.dbConnection.DBOpenConnection;
import backend.database.dbQueries.DeleteQueries;
import backend.database.dbQueries.InsertQueries;;

public class ConnectionInit {

	private DBConnection connection;

	public boolean init() {
		// TODO Auto-generated method stub
		DBCredentials credentials = new DBCredentials();
		credentials.setHostAdress("localhost");
		credentials.setPort(8080);
		credentials.setDatabase("BachelorUtilities");
		credentials.setUsername("root");
		credentials.setPassword("");
		
		DBOpenConnection openConnection = new DBOpenConnection();
		try {
			connection = openConnection.createConnection(credentials);
			InsertQueries insert = new InsertQueries(connection);
			DeleteQueries delete = new DeleteQueries(connection);
			delete.DeleteTopic(2);
//			for(int i = 0; i<20;i++){
//				Topic topic = new Topic();
//				topic.setTitle("Topic " + i);
//				topic.setDescription("Description for Topic " + i);
//				
//				Author author = new Author();
//				author.setForename("Sebastian " + i);
//				author.setName("Lohr" + i);
//				author.setMatriculationNumber(26044);
//				
//				ExpertOpinion expertOpinion = new ExpertOpinion();
//				expertOpinion.setForename("Tobias" + i);
//				expertOpinion.setName("Eggendorfer" + i);
//				expertOpinion.setOpinion("ganz ok ..." + i);
//				
//				SecondOpinion secondOpinion = new SecondOpinion();
//				secondOpinion.setForename("Thorsten" + i);
//				secondOpinion.setName("Weiss" + i);
//				secondOpinion.setOpinion("find ich gut!" + i);
//				
//				Grade grade = new Grade();
//				grade.setGrade(1+(i)/10);
//				
//				topic.setAuthor(author);
//				topic.setExpertOpinion(expertOpinion);
//				topic.setSecondOpinion(secondOpinion);
//				topic.setGrade(grade);
//				insert.insertNewTopic(topic);
//			}
//			for(int i = 0; i<20;i++){
//				for(int j =0; j<20; j++){
//					Date date = new Date();
//					date.setDate("2016-10-" + j);
//					date.setName("Meeting nr. " + j);
//					insert.executeInsertStatement(date, i+1);
//				}
//			}
			return true;
			
		} catch (Exception e) {
			System.err.println("Error establishing connection");
			e.printStackTrace();
			return false;
		} 
	}

	public DBConnection getConnection() {
		return connection;
	}
}
