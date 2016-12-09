package frontend;

import javafx.application.Application;
import javafx.stage.Stage;

public class FrontEnd extends Application {
	@Override
	public void start(Stage primaryStage) {
		createScene(primaryStage);
	}

	private void createScene(Stage primaryStage) {
		// TODO gotta build up connection first to check login credentials
		new LoginPane(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
