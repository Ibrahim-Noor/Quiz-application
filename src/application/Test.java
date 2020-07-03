package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.QuizResultDetails;
import Models.Student;
import javafx.fxml.LoadException;
import sample_data.DataCollector;

public class Test {
	
	
	public static void main(String[] args) throws ClassNotFoundException, LoadException, SQLException {
//		CREATE TABLE quiz_results(
//		quiz_results_id INT NOT null PRIMARY  KEY,
//		quiz_id INT NOT null,
//		student_id INT Not null,
//		right_answers INT NOT null,
//		date_time TIMESTAMP NOT null,
//		FOREIGN KEY(quiz_id) REFERENCES quizs(quiz_id),
//		FOREIGN KEY(student_id) REFERENCES students(student_id)
//	  )

//		CREATE TABLE quiz_result_detail(
//				  quiz_result_detail INT not null PRIMARY KEY,
//				  quiz_result_id INT NOT null,
//				  question_id INT not null,
//				  right_answer VARCHAR(300) NOT null,
//				  FOREIGN KEY(quiz_result_id) REFERENCES quiz_results(quiz_results_id),
//				  FOREIGN KEY(question_id) REFERENCES questions(id)
//				  )

//		QuizResultDetails.createTable();
//		DataCollector.readAndSaveQuizesData();
//		DataCollector.readAndSaveStudentsData();
		
//		totalSec = 5000;
		
//		Student student = new Student();
//		student.setId(60);
//		Set<QuizResult> keys = QuizResult.getQuizResult(student).keySet();
//		for (QuizResult quizzes : keys ) {
//			System.out.println(quizzes.getId());
//		}
		
		QuizResult quizResult = new QuizResult();
		quizResult.setId(4);
		System.out.println(quizResult.getNumberOfAttemptedQuestions());

	}
}
