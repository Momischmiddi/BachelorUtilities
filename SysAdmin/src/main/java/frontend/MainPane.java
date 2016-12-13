package frontend;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import backend.database.dbClasses.Date;
import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbQueries.DeleteQueries;
import backend.database.dbQueries.InsertQueries;
import backend.database.dbQueries.SearchQueries;
import backend.database.dbQueries.UpdateQueries;
import frontend.calendar.DatePickerExtended;
import frontend.dateListPanel.DetailListPane;
import frontend.eva.EvaluationWindow;
import frontend.topic.ListTopicsWindow;
import frontend.topic.TopicWindow;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends StackPane {
	public static SearchQueries searchQueries;
	public static InsertQueries insertQueries;
	public static DeleteQueries deleteQueries;
	public static UpdateQueries updateQueries;

	private Stage primaryStage;
	private final double height;
	private final double width;
	private DatePickerExtended datePickerExtended;
	private TreeMap<LocalDate, Topic> dateTreeMap;

	public MainPane(Stage primaryStage, DBConnection dbConnection) {
		this.primaryStage = primaryStage;
		height = primaryStage.getHeight();
		width = primaryStage.getWidth();
		primaryStage.setOnCloseRequest(closeEvent -> dbConnection.closeConnection());

		searchQueries = new SearchQueries(dbConnection);
		insertQueries = new InsertQueries(dbConnection);
		deleteQueries = new DeleteQueries(dbConnection);
		updateQueries = new UpdateQueries(dbConnection);
		
		listQueries();
		initComponents();
	}

	private void listQueries() {
		if (null == searchQueries.connection)
			return;
		ArrayList<Topic> topics = searchQueries.searchAllTopics();
		Map<LocalDate, Topic> dateMap = new HashMap<>();

		for (Topic topic : topics) {
			for (Date dateToAdd : topic.getDate()) {
				try {
					LocalDate date = LocalDate.parse(dateToAdd.getDate(), DatePickerExtended.dateFormatterMurica);
					dateMap.put(date, topic);
				} catch (DateTimeParseException e) {
					System.out.println("Could not parse: " + dateToAdd.getDate());
					continue;
				}
			}
		}
		dateTreeMap = new TreeMap<LocalDate, Topic>(dateMap);
	}

	private void initComponents() {

		BorderPane mainContainer = new BorderPane();

		mainContainer.getStyleClass().add("pane");
		mainContainer.setPrefSize(width, height);
		mainContainer.setLeft(initNaviPane());
		mainContainer.setRight(initListPane());
		mainContainer.setTop(initCalendarHeader());
		mainContainer.setCenter(initCalendarPane());
		mainContainer.setBottom(null);

		primaryStage.setScene(new IExtendedScene(primaryStage, mainContainer));
	}

	private HBox initCalendarHeader() {
		HBox toolBarPane = new HBox();
		toolBarPane.setPrefSize(width, height * 0.05);
		toolBarPane.getStyleClass().add("pane");
		Button btnRefresh = new Button("Refresh");
		btnRefresh.setOnAction(e -> refresh());

		toolBarPane.getChildren().add(btnRefresh);
		return toolBarPane;
	}

	private void refresh() {
		listQueries();
		initComponents();
	}

	private VBox initNaviPane() {
		VBox naviPane = new VBox();
		naviPane.setPrefSize(width / 5, height);
		naviPane.getStyleClass().add("pane");

		Button buttonNewTopic = new Button("Neues Thema"), buttonShowTopics = new Button("Alle Projekte anzeigen"),
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

	private HBox initCalendarPane() {
		HBox calendarPane = new HBox();
		calendarPane.setPrefSize(width * 0.6, height);
		calendarPane.getStyleClass().add("pane");
		try {
			datePickerExtended = new DatePickerExtended(LocalDate.now(), dateTreeMap);
			calendarPane.getChildren().add(datePickerExtended.getCalendar());
		} catch (final Exception e) {
			System.out.println("Could not initialize Calendar with given Informations\nLoad blank clander....");
			calendarPane = new HBox();
		}
		return calendarPane;
	}

	private ScrollPane initListPane() {
		return new DetailListPane(width, height, dateTreeMap);
	}
}