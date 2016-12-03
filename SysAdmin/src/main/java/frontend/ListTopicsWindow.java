package frontend;

import java.util.ArrayList;

import backend.database.dbClasses.Topic;
import backend.database.dbQueries.SearchQueries;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListTopicsWindow extends Stage {

	private GridPane grid = new GridPane();
	private SearchQueries searchQueries;

	public ListTopicsWindow(Stage primaryStage, SearchQueries searchQueries) {
		this.searchQueries = searchQueries;
		setTitle("Übersicht Projektarbeiten");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);

		initComponents();
		setLayout();

		Scene scene = new Scene(grid, getWidth(), getHeight());
		setScene(scene);

		show();
	}

	private void setLayout() {
		// TODO Auto-generated method stub

	}

	private void initComponents() {
		ArrayList<Topic> topics = searchQueries.searchAllTopics();

		int i = 0;
		for (Topic topic : topics) {
			grid.add(new Label(topic.getTitle()), 0, i++);
		}
	}

}
