package backend.database;

public final class DBStructure {
	
	public static enum DBError {
		//Cannot get the Primary Key from last inserted data
		NO_PRIMARY_KEY(-1);
	    private final int value;

	    private DBError(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	//Database
	public static final String MAIN_DATABASE 					= "BachelorUtilities";
	
	//Topic
	public static final String TABLE_TOPIC 						= "Topic";
	public static final String TABLE_TOPIC_TITLE 				= "title"; 
	public static final String TABLE_TOPIC_DESCRIPTION 			= "topic_description"; 
	public static final String TABLE_TOPIC_AUTHOR 				= "author"; 
	public static final String TABLE_TOPIC_GRADE 				= "grade"; 
	public static final String TABLE_TOPIC_EXPERT_OPINION 		= "expert_opinion";
	public static final String TABLE_TOPIC_SECOND_OPINION 		= "second_opinion";
	
	//Author
	public static final String TABLE_AUTHOR 					= "Author";
	public static final String TABLE_AUTHOR_NAME 				= "name";
	public static final String TABLE_AUTHOR_FORENAME 			= "forename";
	public static final String TABLE_AUTHOR_MATRICULATIONNR 	= "matriculationNumber";
	
	//Grade
	public static final String TABLE_GRADE 						= "Grade";
	public static final String TABLE_GRADE_GRADE 				= "grade";
	
	//Expert_Opinion
	public static final String TABLE_EXPERT_OPINION 			= "Expert_Opinion";
	public static final String TABLE_EXPERT_OPINION_NAME 		= "name";
	public static final String TABLE_EXPERT_OPINION_FORENAME 	= "forename";
	public static final String TABLE_EXPERT_OPINION_OPINION 	= "opinion";
	
	//Second_Opinion
	public static final String TABLE_SECOND_OPINION	 			= "Second_Opinion";
	public static final String TABLE_SECOND_OPINION_NAME 		= "name";
	public static final String TABLE_SECOND_OPINION_FORENAME 	= "forename";
	public static final String TABLE_SECOND_OPINION_OPINION 	= "opinion";
	
	//Date
	public static final String TABLE_DATE 						= "Date";
	public static final String TABLE_DATE_NAME 					= "name";
	public static final String TABLE_DATE_MEETING_DATE 			= "meeting_date";
	public static final String TABLE_DATE_TOPIC 				= "topic";
}
