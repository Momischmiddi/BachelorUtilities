package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TopicWindow extends Stage {
	
	public TopicWindow(Stage primaryStage) {
		setTitle("Second Stage");
		
		Label secondLabel = new Label("Hello");
		
		StackPane secondaryLayout = new StackPane();
		
		secondaryLayout.getChildren().add(secondLabel);
		
		Scene secondScene = new Scene(secondaryLayout, 200, 100);
		
		setScene(secondScene);
		
		//Set position of second window, related to primary window.
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);
		show();
	}
}
