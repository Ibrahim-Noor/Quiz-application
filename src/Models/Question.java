package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Question {
	private Quiz quiz;
	private Integer questionID;
	private String question;
	private String op1;
	private String op2;
	private String op3;
	private String op4;
	private String correctAnswer;
	
	
	public static class MetaData {
		public static final String tableName = "questions";
		public static final String quizID = "quiz_id";
		public static final String questionID = "id";
		public static final String question = "question";
		public static final String option1 = "option_1";
		public static final String option2 = "option_2";
		public static final String option3 = "option_3";
		public static final String option4 = "option_4";
		public static final String correctAnswer = "correct_answer";
	}
	
	public Question() {
		super();
	}

	
	public Question(Quiz quiz, String question, String op1, String op2, String op3, String op4,
			String correctAnswer) {
		super();
		this.quiz = quiz;
		this.question = question;
		this.op1 = op1;
		this.op2 = op2;
		this.op3 = op3;
		this.op4 = op4;
		this.correctAnswer = correctAnswer;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	public Integer getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Integer questionID) {
		this.questionID = questionID;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOp1() {
		return op1;
	}
	public void setOp1(String op1) {
		this.op1 = op1;
	}
	public String getOp2() {
		return op2;
	}
	public void setOp2(String op2) {
		this.op2 = op2;
	}
	public String getOp3() {
		return op3;
	}
	public void setOp3(String op3) {
		this.op3 = op3;
	}
	public String getOp4() {
		return op4;
	}
	public void setOp4(String op4) {
		this.op4 = op4;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	
	
	public static void createTable() {
		try {
			String raw = "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(500),"
					+ " %s VARCHAR(500), %s VARCHAR(500), %s VARCHAR(500), %s VARCHAR(500), "
					+ "%s VARCHAR(500), %s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s))";
			String query = String.format(raw, MetaData.tableName, MetaData.questionID, MetaData.question ,MetaData.option1, MetaData.option2, 
					MetaData.option3, MetaData.option4, MetaData.correctAnswer, MetaData.quizID, MetaData.quizID,
					Quiz.MetaData.tableName, Quiz.MetaData.quizID);
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
	
	
	@Override
	public String toString() {
		return this.question; 
	}


	public boolean save() {
		try {
			String raw = "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?) ";
			String query = String.format(raw, MetaData.tableName, 
					MetaData.question, MetaData.option1, MetaData.option2, MetaData.option3, MetaData.option4,
					MetaData.correctAnswer, MetaData.quizID);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, this.question);
			ps.setString(2, this.op1);
			ps.setString(3, this.op2);
			ps.setString(4, this.op3);
			ps.setString(5, this.op4);
			ps.setString(6, this.correctAnswer);
			ps.setInt(7, this.quiz.getQuizID());
			int i = ps.executeUpdate();
			System.out.println("Updated rows " + String.valueOf(i));
			conn.close();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
