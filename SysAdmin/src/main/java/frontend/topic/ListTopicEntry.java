package frontend.topic;

import backend.database.dbClasses.Topic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListTopicEntry extends Pane {
	
	private Label labelTitle, labelAuthor, labelState;
	private Button buttonDetails = new Button("Details"), buttonDelete = new Button("Löschen");
	
	public ListTopicEntry(Topic topic, EventHandler<ActionEvent> detailViewHandler, EventHandler<ActionEvent> deleteHandler) {
		initComponents(topic, detailViewHandler, deleteHandler);
		setLayout();
	}

	private void setLayout() {
		GridPane gridLayout = new GridPane();
		gridLayout.add(new HBox(labelTitle, labelAuthor, labelState), 0, 0);
		gridLayout.add(new HBox(buttonDetails, buttonDelete), 0, 1);
		getChildren().add(gridLayout);
	}

	private void initComponents(Topic topic, EventHandler<ActionEvent> detailViewHandler,  EventHandler<ActionEvent> deleteHandler) {
		labelAuthor = new Label(appendAuthorName(topic));
		labelTitle = new Label(appendTitle(topic));
		labelState = new Label(appendState(topic));
		
		buttonDelete.setOnAction(deleteHandler);
		buttonDetails.setOnAction(detailViewHandler);
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
