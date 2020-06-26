package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Models.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AdminStudentTabFXMLController implements Initializable{

    @FXML
    private VBox formContainer;

    @FXML
    private JFXTextField firstName;

    @FXML
    private JFXTextField lastName;

    @FXML
    private JFXTextField studentEmail;
    
    @FXML
    private PasswordField studentPassword;
    
    @FXML
    private JFXTextField mobileNumber;
    
    @FXML
    private JFXRadioButton male;

    @FXML
    private JFXRadioButton female;
    
    @FXML
    private ToggleGroup gendertoggle;

    @FXML
    private JFXButton saveBtn;
    
    @FXML
    private TableView<Student> studenttable;

    @FXML
    private TableColumn<Student, Integer> studentidclm;

    @FXML
    private TableColumn<Student, String> firstNameclm;

    @FXML
    private TableColumn<Student, String> lastNameclm;
    
    @FXML
    private TableColumn<Student, String> emailclm;
    
    @FXML
    private TableColumn<Student, String> passwordclm;
    
    @FXML
    private TableColumn<Student, String> mobileNumberclm;

    @FXML
    private TableColumn<Student, Character> genderclm;
    
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		renderTable();
	}
    
    private void renderTable() {
		// TODO Auto-generated method stub
		ArrayList<Student> students = Student.getAll();
		
		this.studentidclm.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
		this.firstNameclm.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
		this.lastNameclm.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
		this.emailclm.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
		this.passwordclm.setCellValueFactory(new PropertyValueFactory<Student, String>("password"));
		this.mobileNumberclm.setCellValueFactory(new PropertyValueFactory<Student, String>("mobile"));
		this.genderclm.setCellValueFactory(new PropertyValueFactory<Student, Character>("gender"));
//		
		studenttable.getItems().addAll(students);
	}

	private void formValidateNotification(String message) {
    	Notifications.create()
		.darkStyle()
		.text(message)
		.title("Illegal Action")
		.position(Pos.CENTER)
		.showError();
    }

    @FXML
    void saveStudent(ActionEvent event) {
    	String firstName = this.firstName.getText().trim();
    	String lastName = this.lastName.getText().trim();
    	String mobile = this.mobileNumber.getText().trim();
    	String email = this.studentEmail.getText().trim();
    	String password = this.studentPassword.getText().trim();
    	String gender = "";
    	JFXRadioButton toggle = (JFXRadioButton)gendertoggle.getSelectedToggle();
    	if (toggle != null)
    		gender = toggle.getText().trim();
    	
    	String regexpattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"; 
		Pattern p = Pattern.compile(regexpattern);
    	
    	if(firstName.length()<4) {
    		String message = "First Name must be more than 4 characters long";
    		formValidateNotification(message);
    	} else if (lastName.length() < 2) {
    		String message = "Last Name must be more than 2 characters long";
    		formValidateNotification(message);
    	} else if (!p.matcher(email).matches()) {
			String message = "Enter a Valid Email";
    		formValidateNotification(message);
    	} else if (password.length() < 4){
			String message = "Password must be more than 4 characters long";
    		formValidateNotification(message);
    	} else if (mobile.length() < 10) {
    		String message = "Please Enter a valid mobile Number";
    		formValidateNotification(message);
    	} else if (gender.isEmpty()) {
    		String message = "Please Select your gender";
    		formValidateNotification(message);
    	} else {
    		//code for saving info
    		Student s = new Student(firstName, lastName, email, password,
    				mobile, gender.charAt(0));	
    		if(s.isExists()) {
    	    	Notifications.create()
    			.darkStyle()
    			.text("Student with the given email already exists")
    			.title("Illegal Action")
    			.position(Pos.CENTER)
    			.showError();
    			return;
    		}
    		s = s.save();
    		if(s != null) {
    	    	Notifications.create()
    			.darkStyle()
    			.text("Student Registered")
    			.hideAfter(new Duration(2500))
    			.title("Success")
    			.position(Pos.CENTER)
    			.showInformation();
    	    	
    	    	
    	    	this.firstName.clear();
    	    	this.lastName.clear();
    	    	this.studentEmail.clear();
    	    	this.studentPassword.clear();
    	    	this.mobileNumber.clear();
    	    	toggle.setSelected(false);
    	    	
    	    	studenttable.getItems().add(0, s);
    	    	
    		} else {
    			Notifications.create()
    			.darkStyle()
    			.text("Student registration failed")
    			.title("Failure")
    			.position(Pos.CENTER)
    			.showError();
    		}
    	}
    }

}
