package frontend;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class DatePickerExtended extends DatePicker {

	private String pattern = "yyyy-MM-dd";
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
	private Label label;

	public DatePickerExtended(LocalDate now, Label label) {
		super(now);
		this.label = label;
		setShowWeekNumbers(false);
		initSelectionListener();
	}

	private void initSelectionListener() {
		setOnAction(e -> {
			String currentDateAsString = getValue().format(dateFormatter).toString();
			label.setText("Ausgewähltes Datum: " + currentDateAsString);
		});
	}
}
