package frontend.dateListPanel;

import java.time.LocalDate;
import java.util.TreeMap;

import backend.database.dbClasses.Topic;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DetailListPane extends ScrollPane {
	private VBox contentBox = new VBox(10);

	public DetailListPane(double width, double height, TreeMap<LocalDate, Topic> dateTreeMap) {
		setId("scrollPane");
		setPrefSize(width / 5, height);

		initComponents(dateTreeMap);
	}

	private void initComponents(TreeMap<LocalDate, Topic> dateTreeMap) {
		if (null == dateTreeMap) return;
		
		for (LocalDate localDate : dateTreeMap.keySet()) {
			contentBox.getChildren().add(new DateListPanelEntry(localDate, dateTreeMap.get(localDate)));
		}
		setContent(contentBox);
	}
}
