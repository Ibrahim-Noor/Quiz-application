package controllers.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Models.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;

public class StudentMainPageTabsFXMLController implements Initializable{
	@FXML
	private Tab quizzestab;
	@FXML
	private Tab attemptedquizzestab;
	
	private Student student;
	
	
	
	public void setStudent(Student student) {
		this.student = student;
		setquizzestab();
		setattemptedquizzestab();
	}

	private void setquizzestab() {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/StudentQuizListScreenFXML.fxml"));
		Node node;
		try {
			node = fxmlloader.load();
			StudentQuizListScreenFXMLController studentQuizListScreenFXMLController = fxmlloader.getController();
			studentQuizListScreenFXMLController.setStudent(this.student);
			this.quizzestab.setContent(node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setattemptedquizzestab() {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/AttemptedQuizzesScreenFXML.fxml"));
		Node node;
		try {
			node = fxmlloader.load();
			AttemptedQuizzesScreenFXMLController attemptedQuizzesScreenFXMLController = fxmlloader.getController();
			attemptedQuizzesScreenFXMLController.setStudent(this.student);
			this.attemptedquizzestab.setContent(node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
}
