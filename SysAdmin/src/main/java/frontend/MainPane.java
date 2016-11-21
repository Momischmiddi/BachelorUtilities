package frontend;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPane extends StackPane {
	private Stage primaryStage;
	private BorderPane mainContainer = new BorderPane();
	private VBox naviPane = new VBox();
	private VBox listPane = new VBox();
	private HBox toolBarPane = new HBox();
	private GridPane calendarPane = new GridPane();
	private GridPane detailPane = new GridPane();
	private double height;
	private double width;
	
	public MainPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initComponents();
	}

	private void initComponents() {
		height = primaryStage.getHeight();
		width = primaryStage.getWidth();
		
		mainContainer.getStyleClass().add("pane");
		mainContainer.setPrefSize(width, height);
		
		mainContainer.setBottom(initDetailPane());
		mainContainer.setLeft(initNaviPane());
		mainContainer.setRight(initListPane());
		mainContainer.setTop(initCalendarHeader());
		mainContainer.setCenter(initCalendarPane());

		primaryStage.setScene(new IExtendedScene(primaryStage, mainContainer));
	}

	private HBox initCalendarHeader() {
		toolBarPane.setPrefSize(width, height * 0.05);
		toolBarPane.getStyleClass().add("pane");
		
		return toolBarPane;
	}

	private GridPane initDetailPane() {
		detailPane.setPrefSize(width / 2, height * 0.2);
		detailPane.getStyleClass().add("pane");
		
		return detailPane;
	}

	private VBox initNaviPane() {
		naviPane.setPrefSize(width / 5, height);
		naviPane.getStyleClass().add("pane");
		
		VBox content = new VBox(10);
		for (int i = 0; i < 10; i++) {
			content.getChildren().add(new Label(Integer.toString(i)));
			content.getChildren().add(new Label("Hier könnte Ihre Werbung stehen!"));
		}

		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setId("scrollPane");
		naviPane.getChildren().add(scrollPane);
		
		return naviPane;
	}

	private GridPane initCalendarPane() {
		calendarPane.setPrefSize(width * 0.6, height * 0.8);
		calendarPane.getStyleClass().add("pane");
		Calendar calendar = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		int dayCount = 1;
		for (int weeks = 0; weeks < 5; weeks++) {
			for (int days = 0; days < 7; days++) {
				Pane pane = new Pane();
				pane.getStyleClass().add("calendarPaneEntry");
				pane.setPrefSize(calendarPane.getPrefWidth() / 7 - 1.715, height * 0.8 / 5);

				if (dayCount <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
					Label label = new Label(Integer.toString(dayCount++));
					pane.getChildren().add(label);
				}
				calendarPane.add(pane, days, weeks);
			}
		}
		return calendarPane;
	}

	private VBox initListPane() {
		listPane.getStyleClass().add("pane");
		listPane.setPrefSize(width / 5, height);

		VBox content = new VBox(10);
		for (int i = 0; i < 10; i++) {
			content.getChildren().add(new Label(Integer.toString(i)));
			content.getChildren().add(new Label("Hier könnte Ihre Werbung stehen!"));
		}

		ScrollPane scrollPane = new ScrollPane(content);
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setId("scrollPane");
		listPane.getChildren().add(scrollPane);
		
		return listPane;
	}
}