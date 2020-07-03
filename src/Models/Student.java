package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Exceptions.LoginException;
import Models.Question.MetaData;
import javafx.fxml.LoadException;

public class Student {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private Character gender;
	
	public static class MetaData {
		public static final String tableName = "STUDENTS";
		public static final String studentID = "student_id";
		public static final String firstName = "first_name";
		public static final String lastName = "last_name";
		public static final String email = "email";
		public static final String password = "password";
		public static final String mobileNumber = "mobile_number";
		public static final String gender = "gender";
	}
	
	public Student() {};
	
	public Student(String firstName, String lastName, String email, String password, String mobile, Character gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.gender = gender;
	}
	
	public Student(Integer id, String firstName, String lastName, String email, String password, String mobile,
			Character gender) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.gender = gender;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	
	public static void createTable() {
		try {
			String raw = "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s VARCHAR(20), "
					+ "%s VARCHAR(20), %s VARCHAR(20), %s VARCHAR(20), %s VARCHAR(20), "
					+ "%s CHAR)";
			String query = String.format(raw, MetaData.tableName, MetaData.studentID, MetaData.firstName,
					MetaData.lastName, MetaData.email, MetaData.password, MetaData.mobileNumber, MetaData.gender);
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
	
	public static ArrayList<Student> getAll() {
		ArrayList<Student> students = new ArrayList<>();
		try {
	    	String raw = "SELECT %s, %s, %s, %s, %s, %s, %s FROM %s";
			String query = String.format(raw, MetaData.studentID, MetaData.firstName, MetaData.lastName, 
					MetaData.email, MetaData.password, MetaData.mobileNumber, MetaData.gender, 
					MetaData.tableName);
			System.out.println(query);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				Student s = new Student();
				s.setId(result.getInt(1));
				s.setFirstName(result.getString(2));
				s.setLastName(result.getString(3));
				s.setEmail(result.getString(4));
				s.setPassword(result.getString(5));
				s.setMobile(result.getString(6));
				s.setGender(result.getString(7).charAt(0));
				students.add(s);
			}
			conn.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return students;
	}
	
    public boolean isExists() {
    	try {
	    	String raw = "SELECT * FROM %s WHERE %s = ?";
			String query = String.format(raw, MetaData.tableName, MetaData.email);
			System.out.println(query);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, this.email);
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				return true;
			}
			conn.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return false;
    }
	
	public Student save() {
		try {
			String raw = "INSERT INTO students (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)";
			String query = String.format(raw, MetaData.firstName, MetaData.lastName, MetaData.email, 
					MetaData.password, MetaData.mobileNumber, MetaData.gender);
			String connectionUrl = "jdbc:sqlite:Quiz.db";
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionUrl);
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, this.firstName);
			ps.setString(2, this.lastName);
			ps.setString(3, this.email);
			ps.setString(4, this.password);
			ps.setString(5, this.mobile);
			ps.setString(6, String.valueOf(this.gender));
			int i = ps.executeUpdate();
			ResultSet keys = ps.getGeneratedKeys();
			if(keys.next()) {
				this.id = keys.getInt(1);
			}
			System.out.println("Updated rows " + String.valueOf(i));
			conn.close();
			return this;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void login() throws ClassNotFoundException, SQLException, LoginException {
		String raw = "SELECT %s, %s, %s, %s, %s FROM %s WHERE %s = ? AND %s = ?";
		String query = String.format(raw, MetaData.studentID, MetaData.firstName, 
				MetaData.lastName, MetaData.mobileNumber, MetaData.gender, MetaData.tableName, 
				MetaData.email, MetaData.password);
		String connectionUrl = "jdbc:sqlite:Quiz.db";
		Class.forName("org.sqlite.JDBC");
		System.out.println(query);
		Connection conn = DriverManager.getConnection(connectionUrl);
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, this.email);
		ps.setString(2, this.password);
		ResultSet result = ps.executeQuery();
		if (result.next()) {
			this.setId(result.getInt(1));
			this.setFirstName(result.getString(2));
			this.setLastName(result.getString(3));
			this.setMobile(result.getString(4));
			this.setGender(result.getString(5).charAt(0));
		} else {
			throw new LoginException("Invalid username or password");
		}
		ps.clearParameters();
		conn.close();
	}
	
}
