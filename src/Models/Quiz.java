package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class Quiz {
	private Integer quizID;
	private String quizTitle;
	
	public static class MetaData {
		public static final String tableName = "QUIZZES";
		public static final String quizID = "quiz_id";
		public static final String quizTitle = "quiz_title";
	}
	public Quiz() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Quiz(String quizTitle) {
		super();
		this.quizTitle = quizTitle;
	}
	public Integer getQuizID() {
		return quizID;
	}
	public void setQuizID(Integer quizID) {
		this.quizID = quizID;
	}
	public String getQuizTitle() {
		return quizTitle;
	}
	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}
	@Override
	public String toString() {
		return this.quizTitle;
	}
	
	
	
	
	public static void createTable() {
		try {
			String raw = "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(50))";
			String query = String.format(raw, MetaData.tableName, MetaData.quizID, MetaData.quizTitle);
			System.out.println(query);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			boolean b = ps.execute();
			System.out.println(b);
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public int save() {
		try {
			String raw = "INSERT INTO %s (%s) VALUES (?)";
			String query = String.format(raw, MetaData.tableName, MetaData.quizTitle);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, this.quizTitle);
			int i = ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			if (keys.next()) {
				return keys.getInt(1);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	
	public boolean save(Set<Question> questions) {
		boolean flag = true;
		this.quizID = this.save();
		for (Question q : questions) {
			flag = flag && q.save();
			System.out.println(flag);
		}
		return flag;
	}
	
	
	public static Map<Quiz, ArrayList<Question>> getAll() {
		Map<Quiz, ArrayList<Question>> quizes = new HashMap<Quiz, ArrayList<Question>>();
		Quiz key = null;
		try{
			String raw = "SELECT %s.%s, %s, %s, %s, %s, %s, %s, "
					+ "%s, %s FROM %s JOIN %s ON %s.%s = %s.%s";
			String query = String.format(raw, MetaData.tableName, MetaData.quizID, MetaData.quizTitle, 
					Question.MetaData.questionID, Question.MetaData.question, Question.MetaData.option1,
					Question.MetaData.option2, Question.MetaData.option3, Question.MetaData.option4, 
					Question.MetaData.correctAnswer ,MetaData.tableName, Question.MetaData.tableName, 
					MetaData.tableName, MetaData.quizID, Question.MetaData.tableName, Question.MetaData.quizID);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			System.out.println(query);
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Quiz tempquiz = new Quiz();
				tempquiz.setQuizID(result.getInt(1));
				tempquiz.setQuizTitle(result.getString(2));
				
				Question tempquestion = new Question();
				tempquestion.setQuestionID(result.getInt(3));
				tempquestion.setQuestion(result.getString(4));
				tempquestion.setOp1(result.getString(5));
				tempquestion.setOp2(result.getString(6));
				tempquestion.setOp3(result.getString(7));
				tempquestion.setOp4(result.getString(8));
				tempquestion.setCorrectAnswer(result.getString(9));
				
				if (key != null && tempquiz.equals(key)) {
					quizes.get(key).add(tempquestion);
				} else {
					ArrayList<Question> templist = new ArrayList<Question>();
					templist.add(tempquestion);
					quizes.put(tempquiz, templist);
				}
				
				key = tempquiz;
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizes;
	}
	
	
	public static Map<Quiz, Integer> getAllWithQuestionCount() {
		Map<Quiz, Integer> quizes = new HashMap<Quiz, Integer>();
		try{
			String raw = "SELECT %s.%s, %s, COUNT(*) AS question_count "
					+ "FROM %s JOIN %s ON %s.%s = %s.%s GROUP BY %s.%s";
			String query = String.format(raw, MetaData.tableName, MetaData.quizID, MetaData.quizTitle, 
					MetaData.tableName, Question.MetaData.tableName, MetaData.tableName, 
					MetaData.quizID, Question.MetaData.tableName, Question.MetaData.quizID,
					MetaData.tableName, MetaData.quizID);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			System.out.println(query);
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Quiz tempquiz = new Quiz();
				tempquiz.setQuizID(result.getInt(1));
				tempquiz.setQuizTitle(result.getString(2));
				quizes.put(tempquiz, result.getInt(3));			
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizes;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(quizID, quizTitle);
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof Quiz) {
			Quiz t = (Quiz)obj;
			if (this.quizID  == t.quizID) {
				return true;
			}
		}
		return false;
	}
	
	
	public ArrayList<Question> getQuizQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>();
		try{
			String raw = "SELECT %s, %s, %s, %s, %s, "
					+ "%s, %s FROM %s WHERE %s = ?";
			String query = String.format(raw, Question.MetaData.questionID, Question.MetaData.question, 
					Question.MetaData.option1, Question.MetaData.option2, Question.MetaData.option3, 
					Question.MetaData.option4, Question.MetaData.correctAnswer, 
					Question.MetaData.tableName, MetaData.quizID);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			System.out.println(query);
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, this.quizID);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Question tempquestion = new Question();
				tempquestion.setQuestionID(result.getInt(1));
				tempquestion.setQuestion(result.getString(2));
				tempquestion.setOp1(result.getString(3));
				tempquestion.setOp2(result.getString(4));
				tempquestion.setOp3(result.getString(5));
				tempquestion.setOp4(result.getString(6));
				tempquestion.setCorrectAnswer(result.getString(7));
				tempquestion.setQuiz(this);
				questions.add(tempquestion);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
	public Integer getNumberOfQuestions() {
		String raw  = "SELECT count(*) FROM %s WHERE %s = ?";
		String query = String.format(raw, Question.MetaData.tableName, Question.MetaData.quizID);
		try {
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			System.out.println(query);
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, this.quizID);
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
