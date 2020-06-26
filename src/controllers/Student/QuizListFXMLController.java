package controllers.Student;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import Models.Quiz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import listeners.NewScreenListener;

public class QuizListFXMLController implements Initializable {
	@FXML
	private FlowPane quizlistcontainer;
	private NewScreenListener newScreenListener;
	Map<Quiz, Integer> quizesWithQuestionCount;
	Set<Quiz> keys;
	
	
	public void setNewScreenListener(NewScreenListener newScreenListener) {
		this.newScreenListener = newScreenListener;
		renderQuizCard();
	}
	
	public void renderQuizCard() {
		for (Quiz q : keys) {
			try {
				FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/Student/QuizCardFXML.fxml"));
				Node node = fxmlloader.load();
				QuizCardFXMLController quizCardFXMLController = fxmlloader.getController();
				System.out.println(quizCardFXMLController);
				System.out.println(q.getQuizTitle());
				quizCardFXMLController.setQuiz(q);
				System.out.println("quizlist");
				System.out.println(q);
				quizCardFXMLController.setnoofq(String.valueOf(quizesWithQuestionCount.get(q)));
				quizCardFXMLController.setNewScreenListener(this.newScreenListener);
				quizlistcontainer.getChildren().add(node);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		quizesWithQuestionCount = Quiz.getAllWithQuestionCount();
		keys = quizesWithQuestionCount.keySet();
	}

}
