package frontend.calendar;

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
	Button btnAdd = new Button("Termin anlegen");
	Button btnDelete = new Button("Termin löschen");
	Button btnMove = new Button("Termin verschieben");

	public CalendarCellPopupMenu(LocalDate value, double x, double y) {
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
		// setGraphic(new
		// ImageView(getClass().getResource("/Images/Calendar-16.png").toString()));

//		grid.setPadding(new Insets(10));
//		grid.setHgap(10);
//		grid.setVgap(10);

		VBox vBox = new VBox(10, btnAdd, btnDelete, btnMove);
	    vBox.setPadding(new Insets(10, 50, 50, 50));
		vBox.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ESCAPE) close(); });
		Scene scene = new Scene(vBox, getWidth(), getHeight());
		scene.getStylesheets().add(LoginPane.cssFile);
		setScene(scene);
		show();
	}
}
