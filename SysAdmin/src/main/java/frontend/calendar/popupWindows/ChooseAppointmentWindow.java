package frontend.calendar.popupWindows;

import java.time.LocalDate;

import backend.database.dbClasses.Date;
import backend.database.dbClasses.Topic;
import backend.database.dbExceptions.NoTitleException;
import backend.database.dbQueries.SearchQueries;
import frontend.IExtendedScene;
import frontend.MainPane;
import frontend.calendar.DatePickerExtended;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseAppointmentWindow extends Stage {

	private SearchQueries searchQueries;
	private ListView<Pane> list;

	private Text description, dateAsString, topicTitle;
	private Button btnDelete;
	private DatePickerExtended datePicker;

	public ChooseAppointmentWindow(double x, double y, LocalDate selectedDate, boolean shouldDelete) {
		setTitle("Termin auswählen...");

		initModality(Modality.APPLICATION_MODAL);
		setWidth(500);
		setHeight(600);
		setX(x);
		setY(y);

		datePicker = new DatePickerExtended(selectedDate);
		if (null != MainPane.searchQueries) {
			searchQueries = MainPane.searchQueries;
			list = new ListView<Pane>();
			ObservableList<Pane> items = FXCollections.observableArrayList();
			for (Topic topic : searchQueries.searchAllTopics()) {
				for (Date dateObject : topic.getDate()) {
					LocalDate date = LocalDate.parse(dateObject.getDate(), DatePickerExtended.dateFormatterMurica);
					if (date.equals(selectedDate)) {
						items.add(createListEntry(topic, dateObject, shouldDelete));
					}
				}
			}
			list.setItems(items);
		}
		setScene(new IExtendedScene(this, list, getWidth(), 600));
		show();
	}

	private Pane createListEntry(Topic topic, Date dateObject, boolean shouldDelete) {
		Pane pane = new Pane();
		initComponents(topic, dateObject, shouldDelete);
		
		pane.getChildren().add(new HBox(10, description, dateAsString, topicTitle, shouldDelete ? btnDelete : datePicker));
		return pane;
	}

	private void initComponents(Topic topic, Date dateObject, boolean shouldDelete) {
		description = new Text(dateObject.getName().isEmpty() ? "No Date" : dateObject.getName().substring(0, dateObject.getName().length() > 15 ? 15 : dateObject.getName().length()));
		dateAsString = new Text(dateObject.getDate().isEmpty() ? "No Date" : dateObject.getDate());
		topicTitle = new Text(topic.getTitle().isEmpty() ? "No Topic Title" : topic.getTitle().substring(0, topic.getTitle().length() > 15 ? 15 : topic.getTitle().length()));

		if (shouldDelete) {
			btnDelete = new Button("Löschen");
			btnDelete.setOnAction(e -> deleteAppointment(topic, dateObject));
			return;
		}
		datePicker.setOnAction(e -> moveAppointment(topic, datePicker.getValue(), dateObject));
	}

	private void deleteAppointment(Topic topic, Date dateObject) {
		if (null == MainPane.deleteQueries || null == MainPane.insertQueries) {
			return;
		}
		if (topic.getDate().contains(dateObject)) {
			topic.getDate().remove(dateObject);
	
			Topic newTopic = new Topic(topic);
			newTopic.setID(newTopic.hashCode());
	
			MainPane.deleteQueries.deleteTopic(topic.getID());
			try {
				MainPane.insertQueries.insertNewTopic(newTopic);
			} catch (NoTitleException e) {
			}
		}
		close();
	}

	private void moveAppointment(Topic topic, LocalDate value, Date dateObject) {
		if (null == MainPane.updateQueries) {
			return;
		}
		if (topic.getDate().contains(dateObject)) {
			Date date2 = new Date(dateObject.getName(), dateObject.getDate());
			
			topic.getDate().remove(dateObject);
			
			date2.setDate(value.format(DatePickerExtended.dateFormatterMurica));
			topic.getDate().add(date2);
			MainPane.updateQueries.updateTopic(topic);
		}
		close();
	}
}
