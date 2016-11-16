package frontend;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainPane extends StackPane {

	private Stage primaryStage;
	private GridPane grid = new GridPane();
	
	public MainPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		initComponents();
	}

	private void initComponents() {
		primaryStage.setScene(new IExtendedScene(primaryStage, this));
		
	}
}
