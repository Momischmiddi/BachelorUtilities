package frontend.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import backend.database.dbClasses.Topic;
import frontend.calendar.popupWindows.CalendarCellPopupMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class DatePickerExtended extends DatePicker {

	private final static String germanPattern = "dd-MM-yyyy";
	private final static String muricaPattern = "yyyy-MM-dd";
	public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(germanPattern);
	public final static DateTimeFormatter dateFormatterMurica = DateTimeFormatter.ofPattern(muricaPattern);

	public DatePickerExtended(LocalDate now, TreeMap<LocalDate, Topic> dateTreeMap) {
		this(now);
		getStyleClass().add("test");

		setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker arg0) {
				return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        
                        setText("DEINE MUTTER");
                        setStyle("-fx-min-height: 100px;");
                    }
                };
			}
		});
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
}
