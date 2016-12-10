package frontend;

import java.time.LocalDate;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbQueries.DeleteQueries;
import backend.database.dbQueries.InsertQueries;
import backend.database.dbQueries.SearchQueries;
import frontend.calendar.DatePickerExtended;
import frontend.eva.EvaluationWindow;
import frontend.topic.ListTopicsWindow;
import frontend.topic.TopicWindow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends StackPane {
	public static SearchQueries searchQueries;
	public static InsertQueries insertQueries;
	public static DeleteQueries deleteQueries;

	private Stage primaryStage;
	private double height;
	private double width;
	private Label currentDateInfo = new Label("Hello There!");
	private DatePickerExtended datePickerExtended;

	public MainPane(Stage primaryStage, DBConnection dbConnection) {
		this.primaryStage = primaryStage;
		primaryStage.setOnCloseRequest(closeEvent -> dbConnection.closeConnection());

		searchQueries = new SearchQueries(dbConnection);
		insertQueries = new InsertQueries(dbConnection);
		deleteQueries = new DeleteQueries(dbConnection);

		initComponents();
		listQueries();
	}

	private void listQueries() {
		ArrayList<Topic> topics = searchQueries.searchAllTopics();

		for (Topic topic : topics) {
			datePickerExtended.append(topic);
		}
	}

	private void initComponents() {

		BorderPane mainContainer = new BorderPane();

		height = primaryStage.getHeight();
		width = primaryStage.getWidth();

		mainContainer.getStyleClass().add("pane");
		mainContainer.setPrefSize(width, height);
		mainContainer.setLeft(initNaviPane());
		mainContainer.setRight(initListPane());
		mainContainer.setTop(initCalendarHeader());
		mainContainer.setCenter(initCalendarPane());
		mainContainer.setBottom(initDetailPane());

		primaryStage.setScene(new IExtendedScene(primaryStage, mainContainer));
	}

	private HBox initCalendarHeader() {
		HBox toolBarPane = new HBox();
		toolBarPane.setPrefSize(width, height * 0.05);
		toolBarPane.getStyleClass().add("pane");

		return toolBarPane;
	}

	private BorderPane initDetailPane() {
		BorderPane detailPane = new BorderPane();
		detailPane.setPrefSize(width / 2, height * 0.2);
		detailPane.getStyleClass().add("pane");

		VBox vbox = new VBox(10);
		vbox.setPrefSize(width, height * 0.2 / 4);
		currentDateInfo.setMinSize(width, height * 0.2 / 4);
		vbox.getChildren().add(currentDateInfo);

		detailPane.getChildren().add(vbox);
		return detailPane;
	}

	private VBox initNaviPane() {
		VBox naviPane = new VBox();
		naviPane.setPrefSize(width / 5, height);
		naviPane.getStyleClass().add("pane");

		Button buttonNewTopic = new Button("Neues Thema"), 
				buttonShowTopics = new Button("Alle Projekte anzeigen"),
				buttonShowEvaluation = new Button("Evaluierungsbogen erstellen");

		buttonNewTopic.setOnAction(e -> new TopicWindow(primaryStage));
		buttonShowTopics.setOnAction(e -> new ListTopicsWindow(primaryStage));
		buttonShowEvaluation.setOnAction(e -> new EvaluationWindow(primaryStage));
		
		VBox content = new VBox(11, buttonNewTopic, buttonShowTopics, buttonShowEvaluation);
		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setId("scrollPane");
		naviPane.getChildren().add(scrollPane);
		return naviPane;
	}

	private GridPane initCalendarPane() {
		GridPane calendarPane = new GridPane();
		calendarPane.setPrefSize(width * 0.6, height * 0.8);
		calendarPane.getStyleClass().add("pane");

		try {
			datePickerExtended = new DatePickerExtended(LocalDate.now(), currentDateInfo);
			final DatePickerSkin datePickerSkin = new DatePickerSkin(datePickerExtended);
			final DatePickerContent calendar = (DatePickerContent) datePickerSkin.getPopupContent();
			calendar.setMinWidth(width * 0.6);
			calendar.setMinHeight(height * 0.8);
			calendarPane.getChildren().add(calendar);

			calendar.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
				if (e.getButton() == MouseButton.SECONDARY) {
					datePickerExtended.showPopUpMenu(e.getSceneX(), e.getSceneY());
				}
			});

		} catch (final Exception e) {
			System.out.println("Could not initialize Calendar with given Informations\nLoad blank clander....");
			calendarPane = new GridPane();
			calendarPane.setPrefSize(width * 0.6, height * 0.8);
			calendarPane.getStyleClass().add("pane");
		}
		return calendarPane;
	}

	private VBox initListPane() {
		VBox listPane = new VBox();
		listPane.getStyleClass().add("pane");
		listPane.setPrefSize(width / 5, height);

		VBox content = new VBox(10);
		for (int i = 0; i < 10; i++) {
			content.getChildren().add(new Label(Integer.toString(i)));
			content.getChildren().add(new Label("Hier könnte Ihre Werbung stehen!"));
		}

		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setId("scrollPane");
		listPane.getChildren().add(scrollPane);

		return listPane;
	}
}