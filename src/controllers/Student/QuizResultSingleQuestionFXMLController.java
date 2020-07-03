package controllers.Student;

import java.net.URL;
import java.util.ResourceBundle;

import Models.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class QuizResultSingleQuestionFXMLController implements Initializable{
	@FXML
	private Label question;
	@FXML
	private Label option1;
	@FXML
	private Label option2;
	@FXML
	private Label option3;
	@FXML
	private Label option4;
	@FXML
	private ImageView questionrightwrong;
	
	
	private Question questionObject;
	
	private String userAnswer;
	
	
	public void setValues(Question questionObject, String userAnswer) {
		this.questionObject = questionObject;
		this.userAnswer = userAnswer;
		
		setTexts();
	}
	
	private void setTexts() {
		this.question.setText(this.question.getText() + this.questionObject.getQuestion());
		this.option1.setText(this.option1.getText() + this.questionObject.getOp1());
		this.option2.setText(this.option2.getText() + this.questionObject.getOp2());
		this.option3.setText(this.option3.getText() + this.questionObject.getOp3());
		this.option4.setText(this.option4.getText() + this.questionObject.getOp4());
		
		
		if (this.questionObject.getOp1().trim().equalsIgnoreCase(this.questionObject.getCorrectAnswer().trim())) {
			
			this.option1.setTextFill(Color.GREEN);

		} else if (this.questionObject.getOp2().trim().equalsIgnoreCase(this.questionObject.getCorrectAnswer().trim())) {
			
			this.option2.setTextFill(Color.GREEN);

		} else if (this.questionObject.getOp3().trim().equalsIgnoreCase(this.questionObject.getCorrectAnswer().trim())) {
			
			this.option3.setTextFill(Color.GREEN);
	
		} else if (this.questionObject.getOp4().trim().equalsIgnoreCase(this.questionObject.getCorrectAnswer().trim())) {
			this.option4.setTextFill(Color.GREEN);			
		}
		
		if (!(this.userAnswer.trim().equalsIgnoreCase(this.questionObject.getCorrectAnswer().trim()))) {
			if (this.questionObject.getOp1().trim().equalsIgnoreCase(this.userAnswer.trim())) {
				
				this.option1.setTextFill(Color.RED);

			} else if (this.questionObject.getOp2().trim().equalsIgnoreCase(this.userAnswer.trim())) {
				
				this.option2.setTextFill(Color.RED);

			} else if (this.questionObject.getOp3().trim().equalsIgnoreCase(this.userAnswer.trim())) {
				
				this.option3.setTextFill(Color.RED);
		
			} else if (this.questionObject.getOp4().trim().equalsIgnoreCase(this.userAnswer.trim())) {
				this.option4.setTextFill(Color.RED);			
			}
			setWrongImage();
		} else {
			setRightImage();
		}
	}

	private void setRightImage() {
		Image rightImage = new Image("/right.jpg");
		this.questionrightwrong.setImage(rightImage);
	}
	
	private void setWrongImage() {
		Image wrongImage = new Image("/wrong1.jpg");
		this.questionrightwrong.setImage(wrongImage);
	}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
