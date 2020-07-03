package controllers.Student;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import Models.Quiz;
import Models.QuizResult;
import Models.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;

import javafx.scene.control.Label;

import javafx.scene.layout.VBox;
import listeners.AttemptedQuizItemClickListener;

public class AttemptedQuizzesScreenFXMLController {
	@FXML
	private VBox list;
	@FXML
	private Label totalquestions;
	@FXML
	private Label attemptedquestions;
	@FXML
	private Label rightanswers;
	@FXML
	private Label wronganswers;
	@FXML
	private PieChart attemptedchart;
	@FXML
	private PieChart rightwrongchart;
	
	
	private Student student;


	public void setStudent(Student student) {
		this.student = student;
		setList();
	}


	private void setList() {
		Map<QuizResult, Quiz> data = QuizResult.getQuizResult(this.student);
		Set<QuizResult> keys = data.keySet();
		for (QuizResult quizResult : keys) {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/AttemptedQuizListItemFXML.fxml"));
			try {
				Node node = fxmlloader.load();
				AttemptedQuizListItemFXMLController attemptedQuizListItemFXMLController = fxmlloader.getController();
				attemptedQuizListItemFXMLController.setData(data.get(quizResult), quizResult);
				attemptedQuizListItemFXMLController.setItemClickListener(new AttemptedQuizItemClickListener() {
					
					@Override
					public void itemClicked(Integer numberOfTotalQuestions, Integer numberOfAttemptedQuestions) {
						// TODO Auto-generated method stub
						
						
						int attemptedQuestions = numberOfAttemptedQuestions;
						int nonAttemptedQuestions = numberOfTotalQuestions - numberOfAttemptedQuestions;
						int nofrightAnswers = quizResult.getnumberOfRightAnswers();
						int nofwronganswers = numberOfAttemptedQuestions-quizResult.getnumberOfRightAnswers();
						
						
						totalquestions.setText(attemptedQuestions + "");
						attemptedquestions.setText(numberOfAttemptedQuestions.toString());
						rightanswers.setText(nofrightAnswers + "");
						wronganswers.setText(String.valueOf(nofwronganswers));
						
						
						ObservableList<PieChart.Data> attemptedData = attemptedchart.getData();
						attemptedData.clear();
						attemptedData.add(new PieChart.Data(String.format("Attempted Questions (%d)", 
								attemptedQuestions), attemptedQuestions));
						attemptedData.add(new PieChart.Data(String.format("Non Attempted Questions (%d)", 
								nonAttemptedQuestions), nonAttemptedQuestions));
					
						ObservableList<PieChart.Data> rightWrongData = rightwrongchart.getData();
						attemptedData.clear();
						rightWrongData.add(new PieChart.Data(String.format("Right Answers (%d)", 
								nofrightAnswers), nofrightAnswers));
						rightWrongData.add(new PieChart.Data(String.format("Wrong Answers (%d)", 
								nofwronganswers), nofwronganswers));
					
					}
				});
				this.list.getChildren().add(node);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
