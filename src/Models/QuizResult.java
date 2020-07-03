package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuizResult {
	
	public static class MetaData {
		public static final String tableName = "QUIZ_RESULT";
		public static final String quizResultID = "quiz_result_ID";
		public static final String QuizID = "quiz_id";
		public static final String studentID = "student_id";
		public static final String rightAnswers = "right_answers";
		public static final String dateTime = "date_time";
	}
	
	
	private Integer id;
	private Quiz quiz;
	private Student student;
	private Integer numberOfRightAnswers;
	private Timestamp timeStamp; 
	
	{
		this.timeStamp = new Timestamp(new Date().getTime());
	}
	
	
	public QuizResult() {
		super();
		// TODO Auto-generated constructor stub
	}


	public QuizResult(Integer id, Quiz quiz, Student student, Integer numberOfRightAnswers) {
		super();
		this.id = id;
		this.quiz = quiz;
		this.student = student;
		this.numberOfRightAnswers = numberOfRightAnswers;
	}
	
	
	public QuizResult(Quiz quiz, Student student, Integer numberOfRightAnswers) {
		super();
		this.quiz = quiz;
		this.student = student;
		this.numberOfRightAnswers = numberOfRightAnswers;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getnumberOfRightAnswers() {
		return numberOfRightAnswers;
	}
	public void setnumberOfRightAnswers(Integer numberOfRightAnswers) {
		this.numberOfRightAnswers = numberOfRightAnswers;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	public static void createTable() {
		try {
			String raw = "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER NOT null PRIMARY KEY AUTOINCREMENT, "
					+ "%s INTEGER NOT null, %s INTEGER NOT null, %s INTEGER NOT null, %s TIMESTAMP NOT null,"
					+ " FOREIGN KEY (%s) REFERENCES %s(%s), FOREIGN KEY (%s) REFERENCES %s(%s))";
			String query = String.format(raw, MetaData.tableName, MetaData.quizResultID, MetaData.QuizID, 
					MetaData.studentID, MetaData.rightAnswers, MetaData.dateTime, MetaData.QuizID, 
					Quiz.MetaData.tableName, Quiz.MetaData.quizID, MetaData.studentID, 
					Student.MetaData.tableName, Student.MetaData.studentID);
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
	
	public boolean save(Map<Question, String> userAnswers) {
		try {
			String raw = "INSERT INTO %s (%s, %s, %s, %s) "
					+ "VALUES (?, ?, ?, CURRENT_TIMESTAMP);";
			String query = String.format(raw, MetaData.tableName, MetaData.QuizID, 
					MetaData.studentID, MetaData.rightAnswers, MetaData.dateTime);
			System.out.println(query);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, this.quiz.getQuizID());
			ps.setInt(2, this.student.getId());
			ps.setInt(3, this.numberOfRightAnswers);
			Integer result = ps.executeUpdate();
			if(result > 0) {
				ResultSet keys= ps.getGeneratedKeys();
				if (keys.next()) {
					this.setId(keys.getInt(1));
					//now we will save details
					return saveQuizResultDetails(userAnswers);
				}
			}
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private boolean saveQuizResultDetails(Map<Question, String> userAnswers) {
		return QuizResultDetails.saveQuizResultDetails(this, userAnswers);
	}
	
	public static Map<QuizResult, Quiz> getQuizResult(Student student) {
		
		Map<QuizResult, Quiz> data = new HashMap<QuizResult, Quiz>();
		String raw = "SELECT %s.%s, %s.%s, "
				+ "%s.%s, %s.%s FROM %s JOIN %s on "
				+ "%s.%s = %s.%s WHERE %s = ?";
		String query = String.format(raw, MetaData.tableName, MetaData.quizResultID, MetaData.tableName,
				MetaData.rightAnswers, Quiz.MetaData.tableName, Quiz.MetaData.quizID, Quiz.MetaData.tableName,
				Quiz.MetaData.quizTitle, MetaData.tableName, Quiz.MetaData.tableName, MetaData.tableName, 
				MetaData.QuizID, Quiz.MetaData.tableName, Quiz.MetaData.quizID, MetaData.studentID);
		
		try {
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, student.getId());
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				QuizResult tempQuizResult = new QuizResult();
				tempQuizResult.setId(result.getInt(1));
				tempQuizResult.setnumberOfRightAnswers(result.getInt(2));
				
				Quiz tempQuiz = new Quiz();
				tempQuiz.setQuizID(result.getInt(3));
				tempQuiz.setQuizTitle(result.getString(4));
				
				data.put(tempQuizResult, tempQuiz);
				
			}
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public Integer getNumberOfAttemptedQuestions() {
		String raw  = "SELECT Count(*) FROM %s WHERE %s = ?";
		String query = String.format(raw, QuizResultDetails.MetaData.tableName, 
				QuizResultDetails.MetaData.quizResultID);
		try {
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			System.out.println(query);
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, this.id);
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				return result.getInt(1);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
}
