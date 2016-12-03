package frontend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import backend.database.dbClasses.Author;
import backend.database.dbClasses.Date;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.Topic;
import backend.database.dbExceptions.NoTitleException;
import backend.database.dbQueries.InsertQueries;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopicWindow extends Stage {
	private GridPane grid = new GridPane();
	
	private TextField title = new TextField("<Titel>");
	
	private DatePicker endDate = new DatePicker();
	
	private TextField authorFirstName = new TextField("<Vorname>");
	private TextField authorLastName = new TextField("<Nachname>");
	private TextField authorMatrNr = new TextField("<Matrikelnummer>");
	
	private TextField proofReaderFirstName = new TextField("<Vorname>");
	private TextField proofReaderLastName = new TextField("<Nachname>");
	private TextField proofReaderOpinion= new TextField("<Beurteilung>");
	
	private TextArea description = new TextArea("Projektbeschreibung....");
	
	private Button buttonOK = new Button("OK");
	private Button buttonCancel = new Button("Abbrechen");
	private Button buttonGenerate = new Button("Beurteilung generieren");

	public TopicWindow(Stage primaryStage, InsertQueries insertQueries) {
		setTitle("Projektarbeit erstellen...");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);
		
		initComponents(insertQueries);
		setLayout();
		
		Scene scene = new Scene(grid, getWidth(), getHeight());
		setScene(scene);
		
		show();
	}

	private void setLayout() {
		grid.add(title, 1, 0);	
		
		grid.add(new Label("Author"), 0, 1);
		grid.add(authorLastName, 1, 1);		
		grid.add(authorFirstName, 1, 2);		
		grid.add(authorMatrNr, 1, 3);
		
		grid.add(new Label("Abgabe am"), 0, 4);		
		grid.add(endDate, 1, 4);
		
		grid.add(new Label("Zweitkorrektur"), 0, 5);
		grid.add(proofReaderLastName,1, 5);
		grid.add(proofReaderFirstName,1, 6);
		grid.add(proofReaderOpinion,1, 7);
		
		grid.add(new Label("Beschreibung"), 0, 8);	
		grid.add(description, 1, 8);
		
		HBox hBox = new HBox(10,buttonGenerate, buttonOK, buttonCancel);
		grid.add(hBox, 1, 9);
	}

	private void initComponents(InsertQueries insertQueries) {
		
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		description.setMaxWidth(getWidth() - 130);
		
		buttonCancel.setOnAction(e -> close());
		buttonOK.setOnAction(e -> saveTopic(insertQueries));
		
	}

	private void saveTopic(InsertQueries insertQueries) {
		try {
			Topic topic = validateTopicInput();
			topic.setAuthor(validateAuthorInput());
			topic.setDate(validateDateInput());
			topic.setExpertOpinion(validateExpertOpinionInput());
			topic.setID(topic.hashCode());
			
			insertQueries.insertNewTopic(topic);
		} catch (NoTitleException e) {
			e.printStackTrace();
		}
	}

	private ExpertOpinion validateExpertOpinionInput() {
		ExpertOpinion opinion = new ExpertOpinion();
		
		String firstName = proofReaderFirstName.getText();
		String lastName = proofReaderLastName.getText();
		String opinionText = proofReaderOpinion.getText();
		
		opinion.setForename((firstName.isEmpty() ? "Kein Vorname angegeben" : firstName));
		opinion.setName((lastName.isEmpty() ? "Kein Nachname angegeben" : lastName));
		opinion.setOpinion((opinionText.isEmpty()) ? "Keine Beurteilung angegeben" : opinionText);
		return opinion;
	}

	private ArrayList<Date> validateDateInput() {
		Date date = new Date();
		LocalDate selectedDate = endDate.getValue();
		String dateAsString = (null != selectedDate) ? 
								selectedDate.format(DatePickerExtended.dateFormatter).toString() 
									: LocalDate.MAX.toString(); 
		date.setDate(dateAsString);
		date.setName("Endabgabe");
		
		ArrayList<Date> arrayList = new ArrayList<>();
		arrayList.add(date);
		return arrayList;
	}

	private Topic validateTopicInput() {
		Topic topic = new Topic();
		String descriptionText = description.getText();
		String titleText = title.getText();
		
		topic.setDescription((descriptionText.isEmpty()) ? "Keine Beschreibung angegeben" : descriptionText);
		topic.setTitle((titleText.isEmpty()) ? "Kein Titel angegeben" : titleText);
		return topic;
	}

	private Author validateAuthorInput() {
		String firstName = authorFirstName.getText();
		String lastName = authorLastName.getText();
		int matrNr = 0;
		try {
			matrNr = Integer.parseInt(authorMatrNr.getText());
		} catch (NumberFormatException e) {
			
		}
		
		Author newAuthor = new Author();
		newAuthor.setForename((firstName.isEmpty()) ? "" : firstName);
		newAuthor.setName((lastName.isEmpty() ? "" : lastName));
		newAuthor.setMatriculationNumber(matrNr);
		return newAuthor;
	}
}
