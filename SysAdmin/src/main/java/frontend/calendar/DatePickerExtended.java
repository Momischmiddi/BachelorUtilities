package frontend.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import backend.database.dbClasses.Date;
import backend.database.dbClasses.Topic;
import frontend.calendar.popupWindows.CalendarCellPopupMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

public class DatePickerExtended extends DatePicker {

	public final static String pattern = "dd-MM-yyyy";
	public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

	public DatePickerExtended(LocalDate now, Label label) {
		this(now);
		setOnAction(e -> label.setText("Ausgewähltes Datum: " + getValue().toString()));
	}

	public DatePickerExtended(LocalDate now) {
		super(now);
		setEditable(false);
		setShowWeekNumbers(false);
		setCustomConverter();

		setOnMouseEntered(e -> show());
		setOnKeyPressed(e -> show());
	}
	
	public void showPopUpMenu(double x, double y) {
		new CalendarCellPopupMenu(getValue(), x, y);
	}

	private void setCustomConverter() {
		setConverter(new StringConverter<LocalDate>() {
			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}

	public void append(Topic topic) {
		for (Date date : topic.getDate()) {
			java.sql.Date sqlDate = null;
			
			
		}
	}
}
