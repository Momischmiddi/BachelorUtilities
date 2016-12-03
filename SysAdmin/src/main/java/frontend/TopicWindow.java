package frontend;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TopicWindow extends Stage {
	
	private GridPane grid = new GridPane();
	
	private TextField title = new TextField("<Titel>");
	private ComboBox<String> date = new ComboBox<>();
	
	private TextField authorFirstName = new TextField("<Vorname>");
	private TextField authorLastName = new TextField("<Nachname>");
	private TextField authorMatrNr = new TextField("<Matrikelnummer>");
	
	private TextField proofReaderFirstName = new TextField("<Vorname>");
	private TextField proofReaderLastName = new TextField("<Nachname>");
	
	private TextArea description = new TextArea("Projektbeschreibung....");
	
	private Button buttonOK = new Button("OK");
	private Button buttonCancel = new Button("Abbrechen");
	private Button buttonGenerate = new Button("Beurteilung generieren");

	public TopicWindow(Stage primaryStage) {
		initOwner(getOwner());
		setTitle("Projektarbeit erstellen...");
		setResizable(false);
		setWidth(600);
		setHeight(500);
		setX(primaryStage.getX() + 250);
		setY(primaryStage.getY() + 100);
		
		initComponents();
		setLayout();
		
		Scene secondScene = new Scene(grid, getWidth(), getHeight());		
		setScene(secondScene);
		
		show();
	}

	private void setLayout() {
		grid.add(title, 1, 0);	
		
		grid.add(new Label("Author"), 0, 1);
		grid.add(authorLastName, 1, 1);		
		grid.add(authorFirstName, 1, 2);		
		grid.add(authorMatrNr, 1, 3);
		
		grid.add(new Label("Zeitraum"), 0, 4);		
		grid.add(date, 1, 4);
		
		grid.add(new Label("Zweitkorrektur"), 0, 5);
		grid.add(proofReaderLastName,1, 5);
		grid.add(proofReaderFirstName,1, 6);
		
		grid.add(new Label("Beschreibung"), 0, 7);	
		grid.add(description, 1, 7);
		
		HBox hBox = new HBox(10,buttonGenerate, buttonOK, buttonCancel);
		grid.add(hBox, 1, 8);
	}

	private void initComponents() {
		grid.setPadding(new Insets(10));
		grid.setHgap(10);
		grid.setVgap(10);
		
		description.setMaxWidth(getWidth() - 130);
	}
}
