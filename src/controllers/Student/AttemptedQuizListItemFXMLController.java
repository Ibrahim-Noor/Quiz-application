package controllers.Student;

import java.net.URL;
import java.util.ResourceBundle;


import Models.Quiz;
import Models.QuizResult;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import listeners.AttemptedQuizItemClickListener;

public class AttemptedQuizListItemFXMLController implements Initializable{
	@FXML
	private Label quiztitle;
	@FXML
	private VBox item;
	
	private Quiz quiz;
	
	private QuizResult quizResult;
	
	private AttemptedQuizItemClickListener itemClickListener;
	
	public void setData(Quiz quiz, QuizResult quizResult) {
		this.quiz = quiz;
		this.quiztitle.setText(quiz.getQuizTitle());
		this.quizResult = quizResult;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void setItemClickListener(AttemptedQuizItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}
	
	public void loadData(MouseEvent event) {
		Integer numberOfAttemptedQuestions = quizResult.getNumberOfAttemptedQuestions();
		Integer numberOfQuestions = quiz.getNumberOfQuestions();
		System.out.println(numberOfAttemptedQuestions);
		System.out.println(numberOfQuestions);
		itemClickListener.itemClicked(numberOfQuestions, numberOfAttemptedQuestions);
	}
}
