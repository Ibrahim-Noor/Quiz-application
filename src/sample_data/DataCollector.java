package sample_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import Models.Question;
import Models.Quiz;
import Models.Student;

public class DataCollector {
	public static void main(String[] args) {
		Quiz.createTable();
		Question.createTable();
		Student.createTable();
		readAndSaveQuizesData();
		readAndSaveStudentsData();
	}
	
	
	public static void readAndSaveQuizesData() {
		String basePath = "src/sample_data/quizes";
		File folder = new File(basePath);
		String[] files = folder.list();
		for (String filename : files) {
			File f = new File(basePath + "/" + filename.toString());
			FileReader fileReader;
			try {
				fileReader = new FileReader(f);
				BufferedReader br = new BufferedReader(fileReader);
				StringBuilder stringBuilder = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
	//				System.out.println(line);
					stringBuilder.append(line);
				}
				System.out.println(stringBuilder);
				
				JSONObject jsonObject = new JSONObject(stringBuilder.toString());
				JSONArray result = jsonObject.getJSONArray("results");
				Quiz quiz = new Quiz();
				Set<Question> questions = new HashSet<Question>();
				for (int i = 0; i < result.length(); i++) {
					JSONObject obj = (JSONObject)result.get(i);
					System.out.println(obj);
					JSONArray answers = obj.getJSONArray("incorrect_answers");
					String correct_ans = obj.get("correct_answer").toString();
					answers.put(correct_ans);
					System.out.println(answers);
					
					quiz.setQuizTitle(obj.getString("category"));
					
					Question question = new Question();
					question.setQuestion(obj.getString("question"));
					question.setOp1(answers.getString(0));
					question.setOp2(answers.getString(1));
					question.setOp3(answers.getString(2));
					question.setOp4(answers.getString(3));
					question.setCorrectAnswer(correct_ans);
					question.setQuiz(quiz);
					System.out.println(quiz.getQuizID());
					questions.add(question);
				}
				quiz.save(questions);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void readAndSaveStudentsData() {
		File f = new File("src/sample_data/users.json");
		FileReader fileReader;
		try {
			fileReader = new FileReader(f);
			BufferedReader br = new BufferedReader(fileReader);
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				stringBuilder.append(line);
			}
			System.out.println(stringBuilder);
			
			JSONArray result = new JSONArray(stringBuilder.toString());
			for (int i = 0; i < result.length(); i++) {
				JSONObject obj = (JSONObject)result.get(i);
				System.out.println(obj);
				Student student = new Student();
				student.setFirstName(obj.getString("firstName"));
				student.setLastName(obj.getString("lastName"));
				student.setEmail(obj.getString("email"));
				student.setPassword(String.valueOf(obj.getLong("password")));
				student.setMobile(String.valueOf(obj.getLong("phone")));
				student.setGender('F');
				student.save();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
