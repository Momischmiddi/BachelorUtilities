package frontend.calendar.popupWindows;

import java.time.LocalDate;

import frontend.LoginPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CalendarCellPopupMenu extends Stage {
	Button btnAdd = new Button("Termin anlegen"),
			btnDelete = new Button("Termin löschen"),
			btnMove = new Button("Termin verschieben");
	private LocalDate selectedDate;

	public CalendarCellPopupMenu(LocalDate value, double x, double y) {
		this.selectedDate = value;
		setTitle("Kalendereintrag analysieren");

		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setWidth(300);
		setHeight(130);
		setX(x);
		setY(y);

		btnAdd.getStyleClass().add("CustomLongButton");
		btnDelete.getStyleClass().add("CustomLongButton");
		btnMove.getStyleClass().add("CustomLongButton");
		
		
		// TODO Load Information about this day
		btnAdd.setOnAction(e -> showChooseTopicWindow(selectedDate));
		
		VBox vBox = new VBox(10, btnAdd, btnDelete, btnMove);
	    vBox.setPadding(new Insets(10, 50, 50, 50));
		vBox.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ESCAPE) close(); });
		Scene scene = new Scene(vBox, getWidth(), getHeight());
		scene.getStylesheets().add(LoginPane.cssFile);
		setScene(scene);
		show();
	}

	private void showChooseTopicWindow(LocalDate selectedDate) {
		close();
		new ChooseTopicWindow(getX(), getY(), selectedDate);
	}
}
