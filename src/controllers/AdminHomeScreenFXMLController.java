package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import javafx.scene.Parent;
import javafx.scene.control.Tab;

public class AdminHomeScreenFXMLController implements Initializable{
	@FXML
	private JFXTabPane admintabpane;
	@FXML
	private Tab addquiztab;
	@FXML
	private Tab addstudenttab;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			Parent addQuiz = FXMLLoader.load(getClass().getResource("/fxml/AddQuizFXML.fxml"));
			addquiztab.setContent(addQuiz);
			
			Parent addStudent = FXMLLoader.load(getClass().getResource("/fxml/AdminStudentTabFXML.fxml"));
			addstudenttab.setContent(addStudent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
