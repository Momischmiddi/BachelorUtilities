package frontend.topic;

import backend.database.dbClasses.Topic;
import backend.database.dbQueries.DeleteQueries;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListTopicEntry extends Pane {
	
	private Label labelTitle, labelAuthor, labelState;
	private Button buttonDetails = new Button("Details"), buttonDelete = new Button("Löschen");
	
	public ListTopicEntry(Topic topic, EventHandler<ActionEvent> detailViewHandler, DeleteQueries deleteQuery) {
		initComponents(topic, detailViewHandler, deleteQuery);
		setLayout();
	}

	private void setLayout() {
		GridPane gridLayout = new GridPane();
		gridLayout.add(new HBox(labelTitle, labelAuthor, labelState), 0, 0);
		gridLayout.add(new HBox(buttonDetails, buttonDelete), 0, 1);
		getChildren().add(gridLayout);
	}

	private void initComponents(Topic topic, EventHandler<ActionEvent> detailViewHandler, DeleteQueries deleteQuery) {
		labelAuthor = new Label(appendAuthorName(topic));
		labelTitle = new Label(appendTitle(topic));
		labelState = new Label(appendState(topic));
		
		buttonDelete.setOnAction(e -> deleteTopic(topic,deleteQuery));
		buttonDetails.setOnAction(detailViewHandler);
	}
	

	private void deleteTopic(Topic topic, DeleteQueries deleteQuery) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Löschbestätigung");
		alert.setHeaderText("Sind Sie sich sicher, dass die das Projekt löschen möchten?");

		if (alert.showAndWait().get() == ButtonType.OK){
			deleteQuery.deleteTopic(topic.getID());
		} 
	}

	private String appendState(Topic topic) {
		return (topic.isFinished() == 1 ? " Fertig gestellt" : " noch offen");
	}

	private String appendTitle(Topic topic) {
		return (topic.getTitle().isEmpty()) ? "<Noch kein Titel angegeben>" : topic.getTitle();
	}

	private String appendAuthorName(Topic topic) {
		String author = " von ";
		if (null != topic.getAuthor()) {
			if (null != topic.getAuthor().getForename()) {
				author = author.concat(topic.getAuthor().getForename());
			}
			if (null != topic.getAuthor().getName()) {
				author = author.concat(" " + topic.getAuthor().getName());
			}
			return author;
		} else {
			return " <Noch kein Author zugewiesen>";
		}
	}
}
