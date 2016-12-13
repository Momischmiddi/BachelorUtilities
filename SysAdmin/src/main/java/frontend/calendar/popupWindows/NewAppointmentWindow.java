package frontend.calendar.popupWindows;

import java.time.LocalDate;

import backend.database.dbClasses.Date;
import backend.database.dbClasses.Topic;
import backend.database.dbExceptions.NoTitleException;
import backend.database.dbQueries.InsertQueries;
import frontend.IExtendedScene;
import frontend.LoginPane;
import frontend.MainPane;
import frontend.calendar.DatePickerExtended;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewAppointmentWindow extends Stage {

	private DatePicker datePicker = new DatePicker(LocalDate.now());
	private TextField tfInfo = new TextField("Terminbeschreibung");
	
	private Button btnOk = new Button("OK"),
			btnCancel = new Button("Abbrechen");
	
	private Topic topic;
	
	public NewAppointmentWindow(double x, double y, Topic topic, LocalDate selectedDate) {
		this.topic = topic;
		setTitle("Termin für Projekt: " + topic.getTitle());

		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(320);
		setHeight(120);
		setX(x);
		setY(y);
		
		initComponents(selectedDate);
		setLayout();
	}

	private void setLayout() {
		GridPane grid = new GridPane();
		
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		grid.add(datePicker, 0, 0);
		
		grid.add(tfInfo, 0, 1);
		
		HBox hBox = new HBox(20, btnOk, btnCancel);
		hBox.setAlignment(Pos.CENTER_RIGHT);
		grid.add(hBox, 0, 2);
		
		setScene(new IExtendedScene(this, grid, getWidth(), getHeight()));
		show();
	}

	private void initComponents(LocalDate selectedDate) {
		tfInfo.setMaxWidth(300);
		
		datePicker.setPrefWidth(300);
		datePicker.setEditable(false);
		datePicker.setValue(selectedDate);
		
		btnCancel.setOnAction(e -> close());
		btnOk.setOnAction(e -> saveAppointment());
	}

	private void saveAppointment() {
		String info = tfInfo.getText();
		String date = datePicker.getValue().format(DatePickerExtended.dateFormatterMurica);
		
		if (null == MainPane.insertQueries) return;
		InsertQueries insertQueries = MainPane.insertQueries;
		System.out.println("New Appointment saved: " + date + " " + info);
		close();
		try {
			insertQueries.executeInsertStatement(new Date(info, date), topic.getID());
		} catch (NoTitleException e) {
			System.out.println("No Title was saved by the topic: "+ topic.getDescription() + "\nwhile trying to save a new Appointment");
			e.printStackTrace();
		}
	}
}
