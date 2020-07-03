package controllers.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import Models.Question;
import Models.Quiz;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

public class QuizResultFXMLController implements Initializable{
	
	@FXML
	private PieChart attemptedquestions;
	
	@FXML
	private PieChart scorechart;
	
	@FXML
	private VBox questioncontainer;

	
	private Map<Question, String> userAnswers;
	
	private Integer numberOfRightAnswers = 0;
	
	private Quiz quiz;
	
	private ArrayList<Question> questionList;
	
	private Integer attemptedQuestions = 0;
	private Integer notAttemptedQuestions = 0;
	
	public void setValues(Map<Question, String> userAnswers, Integer numberOfRightAnswers, Quiz quiz,
			ArrayList<Question> questionList) {
		this.userAnswers = userAnswers;
		this.numberOfRightAnswers = numberOfRightAnswers;
		this.quiz = quiz;
		this.questionList = questionList;
		
		this.attemptedQuestions = userAnswers.keySet().size();
		this.notAttemptedQuestions = this.questionList.size() - this.attemptedQuestions;
		
		setChartValues();
		setUserAnswerList();
	}
	
	
	private void setChartValues() {
		ObservableList<PieChart.Data> attemptedQuestionsData = this.attemptedquestions.getData();
		attemptedQuestionsData.add(new PieChart.Data(String.format("Attempted (%d)", 
				this.attemptedQuestions), this.attemptedQuestions));
		attemptedQuestionsData.add(new PieChart.Data(String.format("Not Attempted (%d)", 
				this.notAttemptedQuestions), this.notAttemptedQuestions));
		
		
		ObservableList<PieChart.Data> scoreChartData = this.scorechart.getData();
		scoreChartData.add(new PieChart.Data(String.format("Right Answer (%d)", 
				this.numberOfRightAnswers), this.numberOfRightAnswers));
		scoreChartData.add(new PieChart.Data(String.format("Wrong Answers (%d)", 
				this.attemptedQuestions - this.numberOfRightAnswers),
				this.attemptedQuestions - this.numberOfRightAnswers));
		
		
	}
	
	
	private void setUserAnswerList() {
		for (Question question : userAnswers.keySet()) {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/QuizResultSingleQuestionFXML.fxml"));
			Node node;
			try {
				node = fxmlloader.load();
				QuizResultSingleQuestionFXMLController quizResultSingleQuestionFXMLController = fxmlloader.getController();
				quizResultSingleQuestionFXMLController.setValues(question, userAnswers.get(question));
				this.questioncontainer.getChildren().add(node);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
