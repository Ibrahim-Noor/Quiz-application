package application;
	
import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.QuizResultDetails;
import Models.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			createTable();
			Parent root = FXMLLoader.load(getClass().getResource("/application/StartPage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void createTable() {
		Quiz.createTable();
		Question.createTable();
		Student.createTable();
		QuizResult.createTable();
		QuizResultDetails.createTable();
	}
}
