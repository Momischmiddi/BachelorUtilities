package frontend;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class IExtendedScene extends Scene {

	public IExtendedScene(Stage primaryStage, Parent root, double i, double j) {
		super(root, i, j);
		init(primaryStage);
	}

	public IExtendedScene(Stage primaryStage, Parent root) {
		super(root);
		init(primaryStage);
	}
	
	

	private void init(Stage primaryStage) {
		primaryStage.setMaximized(true);
		
		String cssFile = getClass().getResource("NewFile.css").toExternalForm();
		getStylesheets().add(cssFile);

		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if (arg0.getCode() == KeyCode.ESCAPE) {
					primaryStage.close();
				}
			}
		});
		primaryStage.show();
	}
}
