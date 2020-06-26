package controllers.Student;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ProgressSequenceFXMLController implements Initializable{
	
	@FXML
	private Circle circlesequence;
	
	@FXML
	private Label questionnumber;
	
	public void setQuestionNumber(Integer quesno) {
		this.questionnumber.setText(quesno.toString());
	}
	
	public void setCircleDefaultColor() {
		this.circlesequence.setFill(Color.web("#DAE0E2"));
		this.questionnumber.setTextFill(Color.BLACK);
	}
	
	public void setCircleCurrentQColor() {
		this.circlesequence.setFill(Color.web("#0ABDE3"));
		this.questionnumber.setTextFill(Color.WHITE);
	}
	
	public void setCircleWrongColor() {
		this.circlesequence.setFill(Color.web("#EC4849"));
		this.questionnumber.setTextFill(Color.BLACK);
	}
	
	public void setCircleRightColor() {
		this.circlesequence.setFill(Color.web("#75DA8B"));
		this.questionnumber.setTextFill(Color.BLACK);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
