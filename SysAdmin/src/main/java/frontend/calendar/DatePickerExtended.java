package frontend.calendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;

import backend.database.dbClasses.Date;
import backend.database.dbClasses.Topic;
import frontend.calendar.popupWindows.CalendarCellPopupMenu;
import javafx.geometry.Pos;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class DatePickerExtended extends DatePicker {

	private final static String germanPattern = "dd-MM-yyyy";
	private final static String muricaPattern = "yyyy-MM-dd";
	public final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(germanPattern);
	public final static DateTimeFormatter dateFormatterMurica = DateTimeFormatter.ofPattern(muricaPattern);
	private DatePickerContent calendar;

	public DatePickerExtended(LocalDate now, TreeMap<LocalDate, Topic> dateTreeMap) {
		this(now);
		getStyleClass().add("test");
		
		setDayCellFactory(createCallBack(dateTreeMap));
		
		DatePickerSkin datePickerSkin = new DatePickerSkin(this);
		datePickerSkin.syncWithAutoUpdate();
		
		calendar = (DatePickerContent) datePickerSkin.getPopupContent();
		calendar.setAlignment(Pos.TOP_CENTER);
		calendar.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
			if (e.getButton() == MouseButton.SECONDARY) {
				showPopUpMenu(e.getSceneX(), e.getSceneY());
			}
		});
	}

	private Callback<DatePicker, DateCell> createCallBack(TreeMap<LocalDate, Topic> dateTreeMap) {
		Callback<DatePicker, DateCell> callback = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker arg0) {
				DateCell dateCell = createCell(dateTreeMap);
				return dateCell;
			}

		};
		return callback;
		
	}
	
	private DateCell createCell(TreeMap<LocalDate, Topic> dateTreeMap) {
		return new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				String style = "-fx-min-height: 95px;"
						+ "-fx-min-height: 95px;"; 
				

				if (dateTreeMap.containsKey(item)) {
					Topic topic = dateTreeMap.get(item);
					for (Date dateObject : topic.getDate()) {
						if (LocalDate.parse(dateObject.getDate(), dateFormatterMurica).equals(item)) {
							setAlignment(Pos.CENTER);
							setText(dateObject.getName() + "\n" + topic.getTitle() + "\n      " +item.getDayOfMonth());
							style = style.concat("-fx-border-color: red;"
									+ "-fx-border-radius: 10 10 10 10;" +
									"-fx-border-width: 3;" +
									"-fx-background-radius: 10 10 10 10;");
						}
					}
				}
				setStyle(style);
			}
		};
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

	public DatePickerContent getCalendar() {
		return calendar;
	}

	public void setCalendar(DatePickerContent calendar) {
		this.calendar = calendar;
	}
}
