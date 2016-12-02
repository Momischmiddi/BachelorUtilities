package frontend;

import java.time.LocalDate;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import backend.database.dbClasses.Topic;
import backend.database.dbConnection.DBConnection;
import backend.database.dbQueries.SearchQueries;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends StackPane {
	private Stage primaryStage;
	private double height;
	private double width;
	private Label currentDateInfo = new Label("Hello There!");
	private SearchQueries searchQueries;
	
	public MainPane(Stage primaryStage, DBConnection dbConnection) {
		this.primaryStage = primaryStage;
		primaryStage.setOnCloseRequest(closeEvent -> dbConnection.closeConnection());
		initComponents();
		searchQueries = new SearchQueries(dbConnection);
		listQueries();
		
	}
	private void listQueries() {
		ArrayList<Topic> topics = searchQueries.searchAllTopics();
		
		for (Topic topic : topics) {
			System.out.println(topic.toString());
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
		
		VBox content = new VBox(11);
		Button button = new Button("Neues Thema");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showCreateTopicWindow();
			}

		});
		content.getChildren().add(button);
		for (int i = 0; i < 10; i++) {
			content.getChildren().add(new Label(Integer.toString(i)));
			content.getChildren().add(new Label("Hier könnte Ihre Werbung stehen!"));
		}

		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setId("scrollPane");
		naviPane.getChildren().add(scrollPane);
		
		return naviPane;
	}
	
	private void showCreateTopicWindow() {
		new TopicWindow(primaryStage);
	}

	private GridPane initCalendarPane() {
		GridPane calendarPane = new GridPane();
		calendarPane.setPrefSize(width * 0.6, height * 0.8);
		calendarPane.getStyleClass().add("pane");
		
		try {
			final DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePickerExtended(LocalDate.now(), currentDateInfo));
			final DatePickerContent calendar = (DatePickerContent) datePickerSkin.getPopupContent();
			calendar.setMinWidth(width * 0.6);
			calendar.setMinHeight(height * 0.8);
			
			calendarPane.getChildren().add(calendar);
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