package frontend;

import connInit.ConnectionInit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPane extends StackPane {
	private Stage primaryStage;

	private Text sceneTitle = new Text("BachelorUtilities");
	private TextField tfID = new TextField("Username.....");
	private PasswordField pwField = new PasswordField();
	private Button btnLogin = new Button("Login");
	private GridPane grid = new GridPane();

	private Label labelUserName = new Label("Benutzername");
	private Label labelPassword = new Label("Passwort");


	public LoginPane(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initComponents();
		initListener();
		initLayout();
	}

	private void initComponents() {
		sceneTitle.setId("sceneTitle");
		labelPassword.setId("loginLabels");
		labelUserName.setId("loginLabels");

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
	}

	private void initListener() {
		tfID.setOnKeyReleased(e -> checkLoginDetails());
		btnLogin.setOnAction(e -> login());
	}

	private void initLayout() {
		grid.add(sceneTitle, 0, 0, 2, 1);
		grid.add(labelUserName, 0, 3);
		grid.add(tfID, 1, 3);
		grid.add(labelPassword, 0, 4);
		grid.add(pwField, 1, 4);

		HBox hbBLoginButton = new HBox(10);
		hbBLoginButton.setAlignment(Pos.BOTTOM_RIGHT);
		hbBLoginButton.getChildren().add(btnLogin);
		grid.add(hbBLoginButton, 0, 5, 2, 1);
		
		primaryStage.setScene(new IExtendedScene(primaryStage, grid, 500, 300));
	}

	private void login() {
		ConnectionInit connectionInit = new ConnectionInit();
		if (connectionInit.init()) {
			new MainPane(primaryStage, connectionInit.getConnection());
		};
	}

	private void checkLoginDetails() {
		btnLogin.setDisable(tfID.getText().isEmpty());
	}
}
