package frontend.topic;

import backend.database.dbClasses.Topic;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TopicListEntry extends Pane {
	
	private Label labelTitle, labelAuthor, labelState;
	private Button buttonDetails = new Button("Details"), buttonDelete = new Button("Löschen");
	
	public TopicListEntry(Topic topic) {
		initComponents(topic);
		setLayout();
	}

	private void setLayout() {
		GridPane gridLayout = new GridPane();
		gridLayout.add(new HBox(labelTitle, labelAuthor, labelState), 0, 0);
		gridLayout.add(new HBox(buttonDetails, buttonDelete), 0, 1);
		getChildren().add(gridLayout);
	}

	private void initComponents(Topic topic) {
		labelAuthor = new Label(appendAuthorName(topic));
		labelTitle = new Label(appendTitle(topic));
		labelState = new Label(appendState(topic));
		
		// TODO initActionEvents for Buttons
		buttonDelete.setOnAction(e -> deleteTopic(topic));
		buttonDetails.setOnAction(e -> showTopicWindow(topic));
	}
	
	private void showTopicWindow(Topic topic) {
		
	}

	private void deleteTopic(Topic topic) {
		
	}

	private String appendState(Topic topic) {
		return (topic.isFinished()) ? "Fertig gestellt" : "Offen";
	}

	private String appendTitle(Topic topic) {
		return (topic.getTitle() == null) ? "<Noch kein Titel angegeben>" : topic.getTitle();
	}

	private String appendAuthorName(Topic topic) {
		String author = "";
		if (null != topic.getAuthor()) {
			if (null != topic.getAuthor().getForename()) {
				author = author.concat(topic.getAuthor().getForename());
			}
			if (null != topic.getAuthor().getName()) {
				author = author.concat(" " + topic.getAuthor().getName());
			}
			return author;
		} else {
			return "<Noch kein Author zugewiesen>";
		}
	}
}
