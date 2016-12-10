package frontend.topic;

import backend.database.dbClasses.Topic;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class ListTopicEntry extends Pane {
	
	static StringProperty labelTitle = new SimpleStringProperty();
	static StringProperty labelAuthor = new SimpleStringProperty();
	static StringProperty labelState = new SimpleStringProperty();
	
	private Button buttonDetails = new Button("Details"), buttonDelete = new Button("Löschen");
	
	public ListTopicEntry(Topic topic, EventHandler<ActionEvent> detailViewHandler, EventHandler<ActionEvent> deleteHandler) {
		initComponents(topic, detailViewHandler, deleteHandler);
		setLayout();
	}
	
	public static Callback<ListTopicEntry, Observable[]> extract() {
		return new Callback<ListTopicEntry, Observable[]>() {
			@Override
			public Observable[] call(ListTopicEntry arg0) {
				return new Observable[]{labelTitle, labelAuthor, labelState};
			}
		};
	}

	private void setLayout() {
		GridPane gridLayout = new GridPane();
		gridLayout.add(new HBox(new Label(labelTitle.get()), new Label(labelAuthor.get()), new Label(labelState.get())), 0, 0);
		gridLayout.add(new HBox(buttonDetails, buttonDelete), 0, 1);
		getChildren().add(gridLayout);
	}

	private void initComponents(Topic topic, EventHandler<ActionEvent> detailViewHandler,  EventHandler<ActionEvent> deleteHandler) {
		labelAuthor.set(appendAuthorName(topic));
		labelTitle.set(appendTitle(topic));
		labelState.set(appendState(topic));
		
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
