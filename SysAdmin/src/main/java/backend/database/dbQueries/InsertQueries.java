package backend.database.dbQueries;

import backend.database.DBStructure;
import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbExceptions.*;;

public class InsertQueries {

	private DBConnection dbconnection;
	public Exception NoTitleException;
	
	
	public InsertQueries(DBConnection dbconnection){
		this.dbconnection = dbconnection;
	}
	
	public void insertNewTopic(Topic topic) throws Exception, NoTitleException{
		
		
		
		
		
		String insertInTopicStatement = createInsertStatement(topic, DBStructure.TABLE_TOPIC);
		dbconnection.getStatement().execute(insertInTopicStatement);
	}

	private String createInsertStatement(Topic topic, String table) throws Exception, NoTitleException{
		String insertStatement = "INSERT INTO " + table + " (";
		String insertValues = "VALUES(";
		
		
		
		if(topic.getTitle() != null){
			insertStatement += "title";
			insertValues += topic.getTitle();
		}else{
			throw NoTitleException;
		}
		if(topic.getDescription() != null){
			insertStatement += ",topic_description";
			insertValues += topic.getDescription();
		}
		insertStatement += ")";
		return insertStatement+insertValues;
	}
}
