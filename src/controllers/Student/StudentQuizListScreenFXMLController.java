package controllers.Student;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Models.Student;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import listeners.NewScreenListener;

public class StudentQuizListScreenFXMLController implements Initializable{
	
	@FXML
	private JFXButton backbtn;
	
	@FXML
	private StackPane quizliststackpane;
	
	private Student student;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
		addquizlistscreen();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	private void addScreen(Node node) {
		this.quizliststackpane.getChildren().add(node);
	}
	
	private void addquizlistscreen() {
		try {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/QuizListFXML.fxml"));
			Node node = fxmlloader.load();
			QuizListFXMLController quizListFXMLController = fxmlloader.getController();
			quizListFXMLController.setNewScreenListener(new NewScreenListener() {
				
				@Override
				public void handle(Event arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void ChangeScreen(Node node) {
					// TODO Auto-generated method stub
					addScreen(node);
				}

				@Override
				public void RemoveTopScreen() {
					quizliststackpane.getChildren().remove(quizliststackpane.getChildren().size()-1);
					// TODO Auto-generated method stub
					
				}
			});
			quizListFXMLController.setStudent(this.student);
			quizliststackpane.getChildren().add(node);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void backBtn() {
		ObservableList<Node> stack = this.quizliststackpane.getChildren();
		if (stack.size() == 1) {
			return;
		}
		this.quizliststackpane.getChildren().remove(stack.size() - 1);
	}
}
