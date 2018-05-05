package application;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 *
 * @author Dan Hope
 */
public class ComicBookMovieTrivia extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXMLTrivia.fxml"));
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

		Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());

		stage.setTitle("Comic Book Movie Trivia");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);
		
	}
}
