package frontend.topic;

import backend.database.dbClasses.Topic;
import backend.database.dbQueries.SearchQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListTopicsWindow extends Stage {

	private ListView<TopicListEntry> list;

	public ListTopicsWindow(Stage primaryStage, SearchQueries searchQueries) {
		setTitle("Übersicht Projektarbeiten");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);

		list = initComponents(searchQueries);
		setLayout();

	}

	private void setLayout() {
		Scene scene = new Scene(list, getWidth(), getHeight());
		setScene(scene);
		show();
	}

	private ListView<TopicListEntry> initComponents(SearchQueries searchQueries) {
		list = new ListView<TopicListEntry>();
		ObservableList<TopicListEntry> items = FXCollections.observableArrayList();
	      
		for (Topic topic : searchQueries.searchAllTopics()) {
			items.add(new TopicListEntry(topic));
		}
		list.setItems(items);
		return list;
	}
}
