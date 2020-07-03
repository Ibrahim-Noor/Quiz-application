package controllers.Student;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.sun.nio.sctp.Notification;

import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.Student;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import listeners.NewScreenListener;

public class QuestionsScreenFXMLController implements Initializable {
	
	private class QuestionObservable {
		Property<String> question = new SimpleStringProperty();
		Property<String> option1 = new SimpleStringProperty();
		Property<String> option2 = new SimpleStringProperty();
		Property<String> option3 = new SimpleStringProperty();
		Property<String> option4 = new SimpleStringProperty();
		Property<String> answer = new SimpleStringProperty();

		public void setQuestion(Question question) {
			this.question.setValue(question.getQuestion());
			this.option1.setValue(question.getOp1());
			this.option2.setValue(question.getOp2());
			this.option3.setValue(question.getOp3());
			this.option4.setValue(question.getOp4());
			this.answer.setValue(question.getCorrectAnswer());

		}
	}
	
	@FXML
	private Label title;
	
	@FXML
	private Label time;
	
	@FXML
	private Label question;
	
	@FXML
	private JFXRadioButton option1;

	@FXML
	private JFXRadioButton option2;
	
	@FXML
	private JFXRadioButton option3;
	
	@FXML
	private JFXRadioButton option4;
	
	@FXML
	private ToggleGroup options;
	
	@FXML
	private JFXButton nextquestionbtn;
	
	@FXML
	private JFXButton submitquizbtn;
	
	@FXML
	private FlowPane quizprogress;
	
	
	
	//NON FXML FIELDS
	private Quiz quiz;
	private ArrayList<Question> questions;
	private Question currentQuestion;
	int currentindex = 0;
	private QuestionObservable questionObservable;
	private Map<Question, String> allAnsweredQuestions = new HashMap<Question, String>();
	private Integer numberOfRightAnswers = 0;
	private Student student;
	private NewScreenListener newScreenListener;


	//TIMER FIELDS
	private long min, sec, hr, totalSec = 0;
	private Timer timer = new Timer();

	
	public void setNewScreenListener(NewScreenListener newScreenListener) {
		this.newScreenListener = newScreenListener;
	}
	
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	private void fetchData() {
		if (this.quiz != null) {
			questions = this.quiz.getQuizQuestions();
			Collections.shuffle(questions);
			renderProgressSequence();
			setNextQuestion();
			setTimer();
		}
	}
	
	private void renderProgressSequence() {
		for (int i = 0; i < questions.size(); i++) {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/ProgressSequenceFXML.fxml"));
			try {
				Node node = fxmlloader.load();
				ProgressSequenceFXMLController progressSequenceFXMLController = fxmlloader.getController();
				progressSequenceFXMLController.setCircleDefaultColor();
				progressSequenceFXMLController.setQuestionNumber(i + 1);
				node.setUserData(progressSequenceFXMLController);
				this.quizprogress.getChildren().add(node);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		this.title.setText(this.quiz.getQuizTitle());
		this.fetchData();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		hideSubmitQuizbtn();
		showNextQuestionbtn();
		this.questionObservable = new QuestionObservable();
		bindFields();
	}
	
	private void bindFields() {
		this.question.textProperty().bind(this.questionObservable.question);
		this.option1.textProperty().bind(this.questionObservable.option1);
		this.option2.textProperty().bind(this.questionObservable.option2);
		this.option3.textProperty().bind(this.questionObservable.option3);
		this.option4.textProperty().bind(this.questionObservable.option4);
	}
	
	public void nextQuestionbtn(ActionEvent ev) {
		RadioButton selected = (RadioButton)this.options.getSelectedToggle();
		if (selected != null)
		{
			String selectedAnswer = selected.getText().trim();
			this.allAnsweredQuestions.put(this.currentQuestion, selectedAnswer);
			Node node = this.quizprogress.getChildren().get(this.currentindex - 1);
			ProgressSequenceFXMLController progressSequenceFXMLController = (ProgressSequenceFXMLController) node.getUserData();
			if (selectedAnswer.equals(currentQuestion.getCorrectAnswer().trim()))
			{
				progressSequenceFXMLController.setCircleRightColor();
				this.numberOfRightAnswers++;
			} else {
				progressSequenceFXMLController.setCircleWrongColor();
			}
		} else {
			return;
		}
		setNextQuestion();
		options.getSelectedToggle().setSelected(false);
	}
	
	private void hideNextQuestionbtn() {
		this.nextquestionbtn.setVisible(false);
	}
	
	private void hideSubmitQuizbtn() {
		this.submitquizbtn.setVisible(false);
	}
	
	private void showNextQuestionbtn() {
		this.nextquestionbtn.setVisible(true);
	}
	
	private void showSubmitQuizbtn() {
		this.submitquizbtn.setVisible(true);
	}
		
	
	private String timeFormat(long value) {
		if (value < 10) {
			return ("0" + value);
		}
		return "" + value;
	}
	
	private void convertTime() {
		min = TimeUnit.SECONDS.toMinutes(totalSec);
		sec = totalSec - (min * 60);
		hr = TimeUnit.MINUTES.toHours(min);
		min = min - (hr * 60);
		this.time.setText(timeFormat(hr) + ":" + timeFormat(min) + ":" + timeFormat(sec));
		totalSec--;
	}
	
	private void setTimer() {
		this.totalSec = this.questions.size() * 2;
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						convertTime();
						if (totalSec < 0) {
							timer.cancel();
							time.setText("00:00:00");
							submitbtn(null);
							Notifications.create()
							.darkStyle().title("Time UP")
							.text("Time Up!!!!!!")
							.position(Pos.CENTER).showInformation();
						}
					}
				});
				// TODO Auto-generated method stub
				
			}
		};
		timer.schedule(timerTask, 0, 1000);
	}
	
	public void submitbtn(ActionEvent ev) {
		timer.cancel();
		System.out.println(this.allAnsweredQuestions);
		QuizResult quizResult = new QuizResult(this.quiz, this.student, numberOfRightAnswers);
		boolean result = quizResult.save(this.allAnsweredQuestions);
		if (result) {
			Notifications.create()
			.darkStyle().title("Success")
			.text("Quiz Submitted")
			.position(Pos.CENTER).showInformation();
			openResultScreen();
		} else {
			Notifications.create()
			.darkStyle().title("Failure")
			.text("Quiz could not be submitted")
			.position(Pos.CENTER).showError();
		}
	}
		
	private void openResultScreen() {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/QuizResultFXML.fxml"));
		Node node;
		try {
			node = fxmlloader.load();
			QuizResultFXMLController quizResultFXMLController = fxmlloader.getController();
			quizResultFXMLController.setValues(this.allAnsweredQuestions, 
					this.numberOfRightAnswers, this.quiz, this.questions);
			this.newScreenListener.RemoveTopScreen();
			this.newScreenListener.ChangeScreen(node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setNextQuestion() {
		if (!(this.currentindex >= this.questions.size())) {
			
			Node node = this.quizprogress.getChildren().get(this.currentindex);
//			System.out.println(node.getUserData());
			ProgressSequenceFXMLController progressSequenceFXMLController = (ProgressSequenceFXMLController) node.getUserData();
			progressSequenceFXMLController.setCircleCurrentQColor();
			this.currentQuestion = this.questions.get(this.currentindex);
			
			ArrayList<String> options = new ArrayList<String>();
			options.add(this.currentQuestion.getOp1());
			options.add(this.currentQuestion.getOp2());
			options.add(this.currentQuestion.getOp3());
			options.add(this.currentQuestion.getOp4());
			Collections.shuffle(options);
			
			this.currentQuestion.setOp1(options.get(0));
			this.currentQuestion.setOp2(options.get(1));
			this.currentQuestion.setOp3(options.get(2));
			this.currentQuestion.setOp4(options.get(3));
			
			this.questionObservable.setQuestion(this.currentQuestion);
			
			this.currentindex++;
		} else {
			hideNextQuestionbtn();
			showSubmitQuizbtn();
		}
	}

}
