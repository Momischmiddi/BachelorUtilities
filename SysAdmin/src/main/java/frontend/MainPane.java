package frontend;

import java.time.LocalDate;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.scene.Node;
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
	private BorderPane mainContainer = new BorderPane();
	private VBox naviPane = new VBox();
	private VBox listPane = new VBox();
	private HBox toolBarPane = new HBox();
	private GridPane calendarPane = new GridPane();
	private BorderPane detailPane = new BorderPane();
	private double height;
	private double width;
	private Label currentDateInfo = new Label("Hello There!");
	
	public MainPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initComponents();
	}

	private void initComponents() {
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
		toolBarPane.setPrefSize(width, height * 0.05);
		toolBarPane.getStyleClass().add("pane");
		
		return toolBarPane;
	}

	private BorderPane initDetailPane() {
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
		  
		DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePickerExtended(LocalDate.now(), currentDateInfo));
		Node popupContent = datePickerSkin.getPopupContent();
		
        calendarPane.getChildren().add(popupContent);
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