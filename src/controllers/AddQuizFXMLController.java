package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeView;

import Models.Question;
import Models.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

public class AddQuizFXMLController implements Initializable {
	
	@FXML
	private JFXTreeView<String> allquizes;

    @FXML
    private JFXTextField quizTitle;

    @FXML
    private JFXButton btnSetQuizTitle;

    @FXML
    private JFXTextArea question;

    @FXML
    private JFXTextField option1;

    @FXML
    private JFXTextField option2;

    @FXML
    private JFXTextField option3;

    @FXML
    private JFXTextField option4;

    @FXML
    private ToggleGroup rightanswer;
    
    @FXML
    private JFXRadioButton radioRightAnswer1;

    @FXML
    private JFXRadioButton radioRightAnswer2;

    @FXML
    private JFXRadioButton radioRightAnswer3;

    @FXML
    private JFXRadioButton radioRightAnswer4;

    @FXML
    private JFXButton btnNextQuestion;

    @FXML
    private JFXButton btnSubmit;
    
	private Quiz quiz = null;
	
	private Set<Question> questions = new HashSet<Question>(); 
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		renderTreeView();
		bindFields();
	}
	
	private void bindFields() {
		radioRightAnswer1.textProperty().bind(this.option1.textProperty());
		radioRightAnswer2.textProperty().bind(this.option2.textProperty());
		radioRightAnswer3.textProperty().bind(this.option3.textProperty());
		radioRightAnswer4.textProperty().bind(this.option4.textProperty());
	}
	
	private void renderTreeView(){
		Map<Quiz, ArrayList<Question>> alldata = Quiz.getAll();
		Set<Quiz> quizes = alldata.keySet();
		TreeItem<String> root = new TreeItem<>("Quizes");
		for (Quiz q : quizes) {
			TreeItem<String> quizTreeItem = new TreeItem<>(q.toString());
			
			ArrayList<Question> questions = alldata.get(q);
			
			for (Question ques : questions) {
				TreeItem<String> question = new TreeItem<>(ques.toString());
				TreeItem<String> option1 = new TreeItem<>("a: " + ques.getOp1());
				TreeItem<String> option2 = new TreeItem<>("b: " + ques.getOp2());
				TreeItem<String> option3 = new TreeItem<>("c: " + ques.getOp3());
				TreeItem<String> option4 = new TreeItem<>("d: " + ques.getOp4());
				TreeItem<String> correct_answer = new TreeItem<>("answer: " + ques.getCorrectAnswer());
				
				question.getChildren().addAll(option1, option2, option3, option4, correct_answer);
				
				quizTreeItem.getChildren().add(question);
			}
			
			root.getChildren().add(quizTreeItem);
		}
		root.setExpanded(true);
		this.allquizes.setRoot(root);
	}
	
    @FXML
    void setQuizTitle(ActionEvent event) {
    	String title = quizTitle.getText();
    	
    	
    	if(title.trim().isEmpty()) {
    		Notifications.create()
    		.darkStyle()
    		.text("Please Enter Quiz Title")
    		.title("Illegal Action")
    		.hideAfter(javafx.util.Duration.millis(2000))
    		.position(Pos.CENTER)
    		.showError();
    		
    		
    	} else {
    		quizTitle.setEditable(false);
    		this.quiz = new Quiz(quizTitle.getText().trim()); 
    	}
    }
    
    private boolean AddQuestion() {
    	Boolean flag = false;
    	String Question = this.question.getText();
    	String op1 = this.option1.getText();
       	String op2 = this.option2.getText();
       	String op3 = this.option3.getText();
       	String op4 = this.option4.getText();
       	Toggle correctanswer = this.rightanswer.getSelectedToggle();
       	
       	if (this.quiz == null) {
       		Notifications.create()
    		.darkStyle()
    		.text("Please Enter Quiz Title")
    		.title("Illegal Action")
    		.hideAfter(javafx.util.Duration.millis(2000))
    		.position(Pos.CENTER)
    		.showError();
       	} else if (Question.trim().isEmpty() ||
       			op1.trim().isEmpty() || 
       			op2.trim().isEmpty() || 
       			op3.trim().isEmpty() || 
       			op4.trim().isEmpty()) {
       		Notifications.create()
    		.darkStyle()
    		.text("All Fields are required \n [Question, option 1, option 2, option 3, option 4]")
    		.title("Illegal Action")
    		.hideAfter(javafx.util.Duration.millis(2000))
    		.position(Pos.CENTER)
    		.showError();
       	} else {
       		if (correctanswer == null) {
       			Notifications.create()
        		.darkStyle()
        		.text("Please select final answer")
        		.title("Illegal Action")
        		.hideAfter(javafx.util.Duration.millis(2000))
        		.position(Pos.CENTER)
        		.showError();
       		} else {
       			// save question
       			Question question = new Question();
       			RadioButton selectedtoggle = (RadioButton) rightanswer.getSelectedToggle();
       			String correctAnswerText = selectedtoggle.getText().trim();
       			question.setQuestion(this.question.getText().trim());
       			question.setOp1(this.option1.getText().trim());
       			question.setOp2(this.option2.getText().trim());
       			question.setOp3(this.option3.getText().trim());
       			question.setOp4(this.option4.getText().trim());
       			question.setCorrectAnswer(correctAnswerText);
       			question.setQuiz(this.quiz);
       			questions.add(question);
       			flag = true;
       			//checking purposes
       			System.out.println(this.questions);
       			
       		}
       	}
       	return flag;
    }
    
    private void ClearQuiz() {
		this.question.clear();
		option1.clear();
		option2.clear();
		option3.clear();
		option4.clear();
    }
    
    @FXML
    void NextQuestion(ActionEvent event) {
    	if(AddQuestion()) {
    		ClearQuiz();
    	}
    }

    @FXML
    void SubmitQuiz(ActionEvent event) {
    	if(AddQuestion()) {
    		if(this.quiz.save(this.questions)) {
    			Notifications.create()
        		.darkStyle()
        		.text("The Quiz has been saved")
        		.title("Success")
        		.hideAfter(javafx.util.Duration.millis(2000))
        		.position(Pos.CENTER)
        		.showConfirm();
    			this.quizTitle.clear();
    			this.quizTitle.setEditable(true);
    			ClearQuiz();
    		} else {
    			Notifications.create()
        		.darkStyle()
        		.text("The Quiz could not be saved")
        		.title("Failure")
        		.position(Pos.CENTER)
        		.showError();
    		}    		
    	}
    }
    
    public void logOut() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/StartPage.fxml"));
			Stage stage = (Stage)quizTitle.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}

}