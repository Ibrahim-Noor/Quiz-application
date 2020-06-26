package controllers.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;

import Models.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import listeners.NewScreenListener;

public class QuizCardFXMLController implements Initializable {
	
	
	@FXML
	private Label title;
	
	@FXML
	private Label noofq;
	
	@FXML
	private JFXButton startquizbtn;
	
	private NewScreenListener newScreenListener;
	
	private Quiz quiz;
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		System.out.println("quizcard");
		System.out.println(quiz);
		this.title.setText(quiz.getQuizTitle());
	}
	
	public void setNewScreenListener(NewScreenListener newScreenListener) {
		this.newScreenListener = newScreenListener;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setnoofq(String value) {
		this.noofq.setText(value);
	}
	
	public void startQuizBtn(ActionEvent event) {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/QuestionsScreenFXML.fxml"));
		try {
			Node node = fxmlloader.load();
			QuestionsScreenFXMLController questionsScreenFXMLController = fxmlloader.getController();
			questionsScreenFXMLController.setQuiz(this.quiz);
			this.newScreenListener.ChangeScreen(node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
