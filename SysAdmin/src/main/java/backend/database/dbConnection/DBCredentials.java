package backend.database.dbConnection;

public class DBCredentials {

	private String username;
	private String password;
	private String hostAdress;
	private int port;
	private String database;
			
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;		
	}

	public void setPassword(String password) {
		this.password = password;		
	}

	public void setHostAdress(String hostAdress) {
		this.hostAdress = hostAdress;		
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getHostAdress() {
		return hostAdress;
	}
	

}
