package backend.database.dbClasses;

import java.util.ArrayList;

import backend.database.dbConnection.DBConnection;

public class DBFactory {
	private Topic topic;
	private DBConnection dbconnection;
	
	public DBFactory(DBConnection dbconnection){
		this.dbconnection = dbconnection;
	}
	
	public Topic getTopic(int id){
		
		return null;
	}
	public ArrayList<Topic> getAllTopics(){
		
		
		
		return null;
	}
}
