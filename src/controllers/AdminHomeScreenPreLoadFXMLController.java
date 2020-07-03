package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminHomeScreenPreLoadFXMLController implements Initializable{
	@FXML
	private AnchorPane adminmainloader;

	// Event Listener on JFXButton.onAction
	@FXML
	public void logOut() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/StartPage.fxml"));
			Stage stage = (Stage)adminmainloader.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			Node node = FXMLLoader.load(getClass().getResource("/fxml/AdminHomeScreenFXML.fxml"));
			adminmainloader.getChildren().add(node);
			node.prefWidth(adminmainloader.widthProperty().doubleValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
}
