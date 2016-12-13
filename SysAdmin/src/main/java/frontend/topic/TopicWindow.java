package frontend.topic;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import backend.database.dbClasses.Author;
import backend.database.dbClasses.Date;
import backend.database.dbClasses.ExpertOpinion;
import backend.database.dbClasses.SecondOpinion;
import backend.database.dbClasses.Topic;
import backend.database.dbExceptions.NoTitleException;
import backend.database.dbQueries.DeleteQueries;
import backend.database.dbQueries.InsertQueries;
import frontend.IExtendedScene;
import frontend.MainPane;
import frontend.calendar.DatePickerExtended;
import frontend.eva.EvaluationWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopicWindow extends Stage {
	private GridPane grid = new GridPane();
	private DatePickerExtended dpEndDate = new DatePickerExtended(LocalDate.now());
	
	private TextField tfTitle = new TextField("<Titel>"),
			tfAuthorFirstName = new TextField("<Vorname>"),
			tfAuthorLastName = new TextField("<Nachname>"),
			tfAuthorMatrNr = new TextField("<Matrikelnummer>"),
			tfProofReaderFirstName = new TextField("<Vorname>"),
			tfProofReaderLastName = new TextField("<Nachname>"),
			tfFirstName = new TextField("<Vorname>"),
			tfLastName = new TextField("<Nachname>");
	
	private Button buttonOK = new Button("OK"),
			buttonCancel = new Button("Abbrechen"),
			buttonGenerate = new Button("Beurteilung generieren"),
			buttonDeleteDate = new Button("Zurücksetzen");

	private TextArea taDescription = new TextArea("Projektbeschreibung....");
	private InsertQueries insertQueries;
	private DeleteQueries deleteQueries;

	private Stage primaryStage;

	public TopicWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
		if (MainPane.insertQueries != null)
		this.insertQueries = MainPane.insertQueries;
		
		setTitle("Projektarbeit erstellen...");
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);
		
		initComponents();
		setLayout();
	}

	public TopicWindow(Stage primaryStage, Topic topic) {
		this(primaryStage);
		setTitle("Projektarbeit bearbeiten...");
		if (null != MainPane.deleteQueries)
		this.deleteQueries = MainPane.deleteQueries;
		insertInformation(topic);
	}

	private void insertInformation(Topic topic) {
		String title = topic.getTitle();
		String description = topic.getDescription();
		
		tfTitle.setText((title.isEmpty()) ? "Kein Titel angegeben" : title);
		dpEndDate.setValue(extractDate(topic.getDate()));
		extractAuthorInfos(topic.getAuthor());
		extractExpertInfos(topic.getExpertOpinion());
		extractProofReaderInfos(topic.getSecondOpinion());
		taDescription.setText(description.isEmpty() ? "Keine Beschreibung angegeben" : description);
		
		buttonOK.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (null == deleteQueries) return;
				deleteQueries.deleteTopic(topic.getID());
				saveTopic();
			}
		});
	}

	private void setLayout() {
		grid.add(tfTitle, 0, 0, 2, 1);	
		
		grid.add(new Label("Author"), 0, 1);
		grid.add(tfAuthorLastName, 1, 1);		
		grid.add(tfAuthorFirstName, 1, 2);		
		grid.add(tfAuthorMatrNr, 1, 3);
		
		grid.add(new Label("Abgabe am"), 0, 4);		
		grid.add(new HBox(10, dpEndDate, buttonDeleteDate), 1, 4);
		
		grid.add(new Label("Betreuer"), 0, 5);
		grid.add(tfLastName,1, 5);
		grid.add(tfFirstName,1, 6);
		
		grid.add(new Label("Zweitkorrektur"), 0, 7);
		grid.add(tfProofReaderLastName,1, 7);
		grid.add(tfProofReaderFirstName,1, 8);
		
		grid.add(new Label("Beschreibung"), 0, 9);	
		grid.add(taDescription, 1, 9);
		
		grid.add(new HBox(10,buttonGenerate, buttonOK, buttonCancel), 1, 10);
		
		setScene(new IExtendedScene(this, grid, getWidth(), getHeight()));
		show();
	}

	private void initComponents() {
		
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		taDescription.setMaxWidth(getWidth() - 130);
		
		buttonCancel.setOnAction(e -> close());
		buttonOK.setOnAction(e -> saveTopic());
		buttonGenerate.setOnAction(e -> new EvaluationWindow(saveTopic(), primaryStage));
		buttonDeleteDate.setOnAction(e -> dpEndDate.setValue(null));
	}

	private Topic saveTopic() {
		Topic topic = null;
		try {
			topic = validateTopicInput();
			topic.setAuthor(validateAuthorInput());
			topic.setDate(validateDateInput());
			topic.setExpertOpinion(validateExpertOpinionInput());
			topic.setSecondOpinion(validateProofReaderInput());
			topic.setID(topic.hashCode());
			topic.setFinished(0);
			
			if(null != insertQueries)
			insertQueries.insertNewTopic(topic);
			
		} catch (NoTitleException e) {
			e.printStackTrace();
		}
		close();
		return topic;
	}

	private SecondOpinion validateProofReaderInput() {
		SecondOpinion opinion = new SecondOpinion();
		
		String firstName = tfProofReaderFirstName.getText();
		String lastName = tfProofReaderLastName.getText();
		
		opinion.setForename((firstName.isEmpty() ? "Kein Vorname angegeben" : firstName));
		opinion.setName((lastName.isEmpty() ? "Kein Nachname angegeben" : lastName));
		return opinion;
	}

	private ExpertOpinion validateExpertOpinionInput() {
		ExpertOpinion opinion = new ExpertOpinion();
		
		String firstName = tfFirstName.getText();
		String lastName = tfLastName.getText();
		
		opinion.setForename((firstName.isEmpty() ? "Kein Vorname angegeben" : firstName));
		opinion.setName((lastName.isEmpty() ? "Kein Nachname angegeben" : lastName));
		return opinion;
	}

	private ArrayList<Date> validateDateInput() {
		Date date = new Date();
		LocalDate selectedDate = dpEndDate.getValue();
		String dateAsString = (null != selectedDate) ? 
								selectedDate.format(DatePickerExtended.dateFormatterMurica).toString() 
									: LocalDate.now().toString(); 
		date.setDate(dateAsString);
		date.setName("Endabgabe");
		
		ArrayList<Date> arrayList = new ArrayList<>();
		arrayList.add(date);
		return arrayList;
	}

	private Topic validateTopicInput() {
		Topic topic = new Topic();
		String descriptionText = taDescription.getText();
		String titleText = tfTitle.getText();
		
		topic.setDescription((descriptionText.isEmpty()) ? "Keine Beschreibung angegeben" : descriptionText);
		topic.setTitle((titleText.isEmpty()) ? "Kein Titel angegeben" : titleText);
		return topic;
	}

	private Author validateAuthorInput() {
		String firstName = tfAuthorFirstName.getText();
		String lastName = tfAuthorLastName.getText();
		int matrNr = 0;
		try {
			matrNr = Integer.parseInt(tfAuthorMatrNr.getText());
		} catch (NumberFormatException e) {
			
		}
		
		Author newAuthor = new Author();
		newAuthor.setForename((firstName.isEmpty()) ? "" : firstName);
		newAuthor.setName((lastName.isEmpty() ? "" : lastName));
		newAuthor.setMatriculationNumber(matrNr);
		return newAuthor;
	}
	
	private LocalDate extractDate(ArrayList<Date> dateList) {
		if (null == dateList) return LocalDate.now();
		if (1 > dateList.size()) return LocalDate.now();
		try { 
			return LocalDate.parse(dateList.get(0).getDate());
		}
		catch (DateTimeParseException e) { 
			return LocalDate.now(); 
		}
	}
	
	private void extractAuthorInfos(Author author) {
		if (null == author) return;
		
		String foreName = author.getForename();
		tfAuthorFirstName.setText((foreName.isEmpty()) ? "Kein Vorname angegeben" : foreName);
		
		String lastName= author.getName();
		tfAuthorLastName.setText((lastName.isEmpty()) ? "Kein Nachname angegeben" : lastName);

		Integer matrNr; 
		try {
			matrNr = Integer.valueOf(author.getMatriculationNumber());
		}
		catch (NumberFormatException e) {
			matrNr = Integer.valueOf(0);
		}
		tfAuthorMatrNr.setText(matrNr.toString());
	}
	
	private void extractProofReaderInfos(SecondOpinion secondOpinion) {
		if (null == secondOpinion) return;
		
		String foreName = secondOpinion.getForename();
		tfProofReaderFirstName.setText((foreName.isEmpty()) ? "Kein Vorname angegeben" : foreName);
		
		String lastName = secondOpinion.getName();
		tfProofReaderLastName.setText(lastName.isEmpty() ? "Kein Nachname angegeben" : lastName);
	}
	
	private void extractExpertInfos(ExpertOpinion expertOpinion) {
		if (null == expertOpinion) return;
		
		String foreName = expertOpinion.getForename();
		tfFirstName.setText((foreName.isEmpty()) ? "Kein Vorname angegeben" : foreName);
		
		String lastName = expertOpinion.getName();
		tfLastName.setText(lastName.isEmpty() ? "Kein Nachname angegeben" : lastName);
	}
}
