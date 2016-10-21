package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Frontend {
	public static final String TITLE = "BachelorUtilities";
	
	private JFrame mainWindow = new JFrame(TITLE);
	private JPanel contentLoginPanel;
	private JTextField tfLoginName = new JTextField("Login...", 255);
	private JPasswordField pfLoginPW = new JPasswordField(new PasswordDocument(), "Password", 255);
	private JButton btnLogin = new JButton("Login");
	
	public static void main(String[] args) {
		new Frontend();
		System.out.println("Hello World!");
	}

	public Frontend() {
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setMinimumSize(new Dimension(300,300));
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);
		
		initializeComponents();
		initializeLayout();
		initializeListener();
	}
	
	private void initializeComponents() {
		contentLoginPanel = new JPanel();
		contentLoginPanel.setLayout(new GridLayout(3,1));
		contentLoginPanel.add(tfLoginName);
		contentLoginPanel.add(pfLoginPW);
		contentLoginPanel.add(btnLogin);
		
	}
	
	private void initializeLayout() {
		BorderLayout layout = new BorderLayout(10, 10);
		mainWindow.setLayout(layout);
		
		mainWindow.add(new JLabel("Login-Bereich"), BorderLayout.NORTH);
		mainWindow.add(contentLoginPanel, BorderLayout.CENTER);
		
	}
	
	private void initializeListener() {
		// For lazy closing reasons
		mainWindow.addKeyListener(escKeyAdapter);
		btnLogin.addActionListener(e -> login(tfLoginName.getText(), pfLoginPW.getPassword()));
	}
	
	private void login(String userName, char[] password) {
		// TODO
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////Utilities
	
	private KeyAdapter escKeyAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
				mainWindow.dispose();
			}
		}
	};

	
}
