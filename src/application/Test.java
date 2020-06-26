package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import Models.Question;
import Models.Quiz;
import Models.Student;
import javafx.fxml.LoadException;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException, LoadException, SQLException {
		System.out.println(Quiz.getAllWithQuestionCount());

	}
}
