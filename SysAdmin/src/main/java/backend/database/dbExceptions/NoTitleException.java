package backend.database.dbExceptions;

public class NoTitleException extends Exception{

	public NoTitleException(){}
	
	public NoTitleException(String message){
		super(message);
	}
}
