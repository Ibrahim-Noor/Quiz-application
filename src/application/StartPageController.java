package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.controlsfx.control.Notifications;

import Exceptions.LoginException;
import Models.Student;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class StartPageController {
	@FXML
	private TextField adminemail;
	@FXML
	private PasswordField adminpassword;
	@FXML
	private Button adminloginbutton;
	@FXML
	private TextField studentemail;
	@FXML
	private PasswordField studentpassword;
	@FXML
	private Button studentloginbutton;

	// Event Listener on Button[#adminloginbutton].onAction
	@FXML
	public void loginadmin(ActionEvent event) {
		// TODO Autogenerated
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/AdminHomeScreenFXML.fxml"));
			Stage stage = (Stage)studentemail.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}
	// Event Listener on Button[#studentloginbutton].onAction
	@FXML
	public void loginstudent(ActionEvent event) {
		// TODO Autogenerated
		Student s = new Student();
		s.setEmail(this.studentemail.getText().trim());
		s.setPassword(this.studentpassword.getText().trim());
		try {
//			s.login();
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/Student/StudentMainScreenFXML.fxml"));
				Stage stage = (Stage)studentemail.getScene().getWindow();
				stage.setScene(new Scene(root));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (e instanceof LoginException) {
				Notifications.create()
				.darkStyle()
				.text(e.getMessage())
				.title("Illegal Action")
				.position(Pos.CENTER)
				.showError();
			} else {
				e.printStackTrace();
			}
		}
	}
}
