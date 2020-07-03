package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

import Models.QuizResult.MetaData;


public class QuizResultDetails {
	private Integer id;
	private QuizResult quizResult;
	private Question question;
	private String userAnswer;
	
	
	public static class MetaData {
		public static final String tableName = "QUIZ_RESULT_DETAILS";
		public static final String quizResultDetailsID = "quiz_result_details_ID";	
		public static final String quizResultID = "quiz_result_ID";
		public static final String questionID = "question_ID";
		public static final String userAnswer = "userAnswer";
		
		
	}
	
	
	
	public QuizResultDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuizResultDetails(QuizResult quizResult, Question question, String userAnswer) {
		super();
		this.quizResult = quizResult;
		this.question = question;
		this.userAnswer = userAnswer;
	}
	public QuizResultDetails(Integer id, QuizResult quizResult, Question question, String userAnswer) {
		super();
		this.id = id;
		this.quizResult = quizResult;
		this.question = question;
		this.userAnswer = userAnswer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public QuizResult getQuizResult() {
		return quizResult;
	}
	public void setQuizResult(QuizResult quizResult) {
		this.quizResult = quizResult;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getUserAnswer() {
		return userAnswer;
	}
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	
	public static void createTable() {
		try {
			String raw = "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER NOT null PRIMARY KEY AUTOINCREMENT, "
					+ "%s INTEGER NOT null, %s INTEGER NOT null, %s VARCHAR(300) NOT null, "
					+ "FOREIGN KEY (%s) REFERENCES %s(%s), FOREIGN KEY (%s) REFERENCES %s(%s))";
			String query = String.format(raw, MetaData.tableName, MetaData.quizResultDetailsID, 
					MetaData.quizResultID, MetaData.questionID, MetaData.userAnswer,  
					MetaData.quizResultID, QuizResult.MetaData.tableName, QuizResult.MetaData.quizResultID,
					MetaData.questionID, Question.MetaData.tableName, Question.MetaData.questionID);
			System.out.println(query);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			boolean b = ps.execute();
			System.out.println(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Boolean saveQuizResultDetails(QuizResult quizResult, Map<Question, String> userAnswers) {
		try {
			String raw = "INSERT INTO %s (%s, %s, %s) "
					+ "VALUES (?, ?, ?)";
			String query = String.format(raw, MetaData.tableName, MetaData.quizResultID, 
					MetaData.questionID, MetaData.userAnswer);
			System.out.println(query);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			Set<Question> questions = userAnswers.keySet();
			for (Question question : questions) {
				ps.setInt(1, quizResult.getId());
				ps.setInt(2, question.getQuestionID());
				ps.setString(3, userAnswers.get(question));
				ps.addBatch();
			}
			int[] result = ps.executeBatch();
			if (result.length > 0) {
				System.out.println(result);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
