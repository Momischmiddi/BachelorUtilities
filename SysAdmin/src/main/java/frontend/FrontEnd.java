package frontend;

import javafx.application.Application;
import javafx.stage.Stage;

public class FrontEnd extends Application {

	@Override
	public void start(Stage primaryStage) {
		createScene(primaryStage);
	}

	private void createScene(Stage primaryStage) {
		// gotta build up connection first to check login credentials
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setTitle("BachelorUtilities");
		primaryStage.setMaximized(true);
		
		new LoginPane(primaryStage);
	}


	public static void main(String[] args) {
		launch(args);
	}
}
