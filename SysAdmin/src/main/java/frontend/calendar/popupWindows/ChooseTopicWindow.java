package frontend.calendar.popupWindows;

import java.time.LocalDate;

import backend.database.dbClasses.Topic;
import backend.database.dbQueries.SearchQueries;
import frontend.LoginPane;
import frontend.MainPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseTopicWindow extends Stage {

	static StringProperty labelTitle = new SimpleStringProperty();
	static StringProperty labelAuthor = new SimpleStringProperty();
	static StringProperty labelState = new SimpleStringProperty();

	private SearchQueries searchQueries;
	private ListView<Pane> list;
	private LocalDate selectedDate;

	public ChooseTopicWindow(double x, double y, LocalDate selectedDate) {
		this.selectedDate = selectedDate;
		setTitle("Projekt auswählen...");

		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(500);
		setHeight(400);
		setX(x);
		setY(y);

		if (null != MainPane.searchQueries) {
			searchQueries = MainPane.searchQueries;
			list = new ListView<Pane>();
			ObservableList<Pane> items = FXCollections.observableArrayList();
			for (Topic topic : searchQueries.searchAllTopics()) {
				items.add(createListEntry(topic));
			}
			list.setItems(items);
		}
		
		Scene scene = new Scene(list, getWidth(), getHeight());
		scene.getStylesheets().add(LoginPane.cssFile);
		setScene(scene);
		show();
	}

	private Pane createListEntry(Topic topic) {
		Pane pane = new Pane();
		initComponents(topic);
		GridPane gridLayout = new GridPane();
		gridLayout.add(new HBox(new Label(labelTitle.get()), new Label(labelAuthor.get()), new Label(labelState.get())),
				0, 0);
		pane.getChildren().add(gridLayout);
		pane.setOnMouseReleased(e -> openCreateNewAppointmentWindow(topic));
		return pane;
	}

	private void openCreateNewAppointmentWindow(Topic topic) {
		new NewAppointmentWindow(getX(), getY(), topic, selectedDate); 
		close();
	}

	private void initComponents(Topic topic) {
		labelAuthor.set(appendAuthorName(topic));
		labelTitle.set(appendTitle(topic));
		labelState.set(appendState(topic));
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
