package frontend;

import javafx.scene.control.Label;

public class CustomText extends Label {
	public CustomText(String text) {
		super(text);
		getStyleClass().add("text");
	}
}
