package frontend.topic;

import backend.database.dbClasses.Topic;
import backend.database.dbQueries.DeleteQueries;
import backend.database.dbQueries.InsertQueries;
import backend.database.dbQueries.SearchQueries;
import frontend.LoginPane;
import frontend.MainPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListTopicsWindow extends Stage {

	private ListView<ListTopicEntry> list;
	private Stage primaryStage;
	private DeleteQueries deleteQueries;
	private Scene scene;
	private SearchQueries searchQueries;
	private InsertQueries insertQueries;

	public ListTopicsWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		if (null != MainPane.searchQueries)
		this.searchQueries = MainPane.searchQueries;
		if (null != MainPane.insertQueries)
		this.insertQueries = MainPane.insertQueries;
		if (null != MainPane.deleteQueries)
		this.deleteQueries = MainPane.deleteQueries;

		setTitle("Übersicht Projektarbeiten");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);

		list = initComponents();
		setLayout();
	}

	private void setLayout() {
		scene = new Scene(list, getWidth(), getHeight());
		scene.getStylesheets().add(LoginPane.cssFile);
		setScene(scene);
		show();
	}

	private ListView<ListTopicEntry> initComponents() {
		if (null == searchQueries || null == insertQueries || null == deleteQueries) return null;
		list = new ListView<ListTopicEntry>();
		ObservableList<ListTopicEntry> items = FXCollections.observableArrayList();
		for (Topic topic : searchQueries.searchAllTopics()) {
			EventHandler<ActionEvent> detailViewHandler = initEventHandlerForDetailView(topic, insertQueries);
			EventHandler<ActionEvent> deleteHandler = initEventHandlerForDeletion(topic, deleteQueries);
			items.add(new ListTopicEntry(topic, detailViewHandler, deleteHandler));
		}
		list.setItems(items);
		return list;
	}

	private EventHandler<ActionEvent> initEventHandlerForDetailView(Topic topic, InsertQueries insertQueries) {
		EventHandler<ActionEvent> detailHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new TopicWindow(primaryStage, topic);
			}
		};
		return detailHandler;
	}

	private EventHandler<ActionEvent> initEventHandlerForDeletion(Topic topic, DeleteQueries deleteQuery) {
		EventHandler<ActionEvent> detailHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Löschbestätigung");
				alert.setHeaderText("Sind Sie sich sicher, dass die das Projekt löschen möchten?");

				if (alert.showAndWait().get() == ButtonType.OK) {
					deleteQuery.deleteTopic(topic.getID());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							close();
							list = initComponents();
							setLayout();
						}
					});
				}
			}
		};
		return detailHandler;
	}
}