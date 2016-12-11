package frontend.dateListPanel;

import java.time.LocalDate;

import backend.database.dbClasses.Topic;
import frontend.calendar.DatePickerExtended;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DateListPanelEntry extends HBox {
	
	public DateListPanelEntry(LocalDate localDate, Topic topic) {
		super(10);

		setMaxWidth(100);
	    setStyle("-fx-background-color: #6495ED;");

	    Text title = new Text(topic.getTitle());
	    title.setFont(Font.font ("Verdana", 20));

		Text date = new Text(localDate.format(DatePickerExtended.dateFormatterMurica) + " ");
		date.setFont(Font.font ("Verdana", 20));
		
		getStyleClass().add("pane");
		getChildren().addAll(date, title);
	}
}
