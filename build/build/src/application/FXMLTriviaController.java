/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

public class FXMLTriviaController implements Initializable {

	ArrayList<String> topNames = new ArrayList<>();
	ArrayList<Integer> topScores = new ArrayList<>();
	FileReader rScores;
	FileWriter wScores;
	private String topName;

	private int random, score = 0, multiplier = 0;

	private final List<Integer> visitedQuestions = new ArrayList<Integer>();

	private boolean gameOver = false;

	@FXML
	private Button btnStart, btnA, btnB, btnC, btnD;

	@FXML
	private Label lblQuestion, lblQuestionA, lblQuestionB, lblQuestionC, lblQuestionD, lblScore, lblMultiplier,
			lblName1, lblName2, lblName3, lblName4, lblName5, lblName6, lblName7, lblName8, lblName9, lblName10,
			lblScore1, lblScore2, lblScore3, lblScore4, lblScore5, lblScore6, lblScore7, lblScore8, lblScore9,
			lblScore10;

	public void setScore() {
		
		lblName1.setText(topNames.get(0));
		lblScore1.setText(String.valueOf(topScores.get(0)));
		lblName2.setText(topNames.get(1));
		lblScore2.setText(String.valueOf(topScores.get(1)));
		lblName3.setText(topNames.get(2));
		lblScore3.setText(String.valueOf(topScores.get(2)));
		lblName4.setText(topNames.get(3));
		lblScore4.setText(String.valueOf(topScores.get(3)));
		lblName5.setText(topNames.get(4));
		lblScore5.setText(String.valueOf(topScores.get(4)));
		lblName6.setText(topNames.get(5));
		lblScore6.setText(String.valueOf(topScores.get(5)));
		lblName7.setText(topNames.get(6));
		lblScore7.setText(String.valueOf(topScores.get(6)));
		lblName8.setText(topNames.get(7));
		lblScore8.setText(String.valueOf(topScores.get(7)));
		lblName9.setText(topNames.get(8));
		lblScore9.setText(String.valueOf(topScores.get(8)));
		lblName10.setText(topNames.get(9));
		lblScore10.setText(String.valueOf(topScores.get(9)));
	}

	public void addScore() {

		for (int i = 0; i < topScores.size(); i++) {

			if (score >= topScores.get(i)) {
				topScores.add(i, score);
				topNames.add(i, topName);

				topScores.remove(topScores.size() - 1);
				topNames.remove(topNames.size() - 1);
				score = 0;
			}
		}
		setScore();
	}

	public void writeScores() {

		try {

			wScores = new FileWriter("leaderboard.txt");
			for (int i = 0; i < topScores.size(); i++) {
				wScores.write(topNames.get(i) + "-" + String.valueOf(topScores.get(i)));
				wScores.write(System.lineSeparator());
			}
			wScores.close();

		} catch (IOException e) {

		}
	}

	public void highScore() {

		if (score >= topScores.get(9)) {
			do {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("High Score!");
				dialog.setHeaderText("WOWZA! " + score + " That's A New High Score!");
				dialog.setContentText("You must now enter your name: ");

				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					topName = result.get();
				}
			} while (topName.equals(""));
		}
		addScore();
	}

	public void gameOver() {

		if (visitedQuestions.size() == questions.length) {

			lblQuestion.setText("Game Over! Total Score: " + score + " points");
			gameOver = true;
			btnA.setDisable(true);
			btnB.setDisable(true);
			btnC.setDisable(true);
			btnD.setDisable(true);
			btnStart.setText("Play Again");
			multiplier = 0;
			highScore();
		}
	}

	public void resetGame() {

		score = 0;
		visitedQuestions.clear();
		btnStart.setText("Skip Question");
		gameOver = false;
		lblScore.setText(String.valueOf(score));
		lblMultiplier.setText("Score: ");
		loadQuestion();
	}

	public void loadQuestion() {

		if (visitedQuestions.size() < questions.length) {
			while (visitedQuestions.contains(random)) {
				random = (int) (Math.random() * questions.length);
			}

			lblQuestion.setText(questions[random][0]);
			lblQuestionA.setText(questions[random][1]);
			lblQuestionB.setText(questions[random][2]);
			lblQuestionC.setText(questions[random][3]);
			lblQuestionD.setText(questions[random][4]);

			visitedQuestions.add(random);

			btnA.setDisable(false);
			btnB.setDisable(false);
			btnC.setDisable(false);
			btnD.setDisable(false);
		}
	}

	public void checkAnswer(int answer) {

		if (Integer.parseInt(questions[random][5]) == answer) {
			// add to multiplier to check what score to give user
			multiplier++;

			// 2x multiplier if user has answered more than 5 questions in a row
			if (multiplier > 5 && multiplier <= 10) {
				score += 200;
				lblScore.setText(String.valueOf(score));
				lblMultiplier.setText("(2X)  Score:");

				// 5x multiplier if user has answered more than 10 questions in a row
			} else if (multiplier > 10 && multiplier <= 20) {
				score += 500;
				lblScore.setText(String.valueOf(score));
				lblMultiplier.setText("(5X)  Score:");

				// 10x multiplier if user has answered more than 20 questions in a row
			} else if (multiplier > 20) {
				score += 1000;
				lblScore.setText(String.valueOf(score));
				lblMultiplier.setText("(10X)  Score:");

				// add 100 to user score for correct answer without multiplier
			} else {
				score += 100;
				lblScore.setText(String.valueOf(score));
				lblMultiplier.setText("Score:");
			}

			// if user answers incorrectly reset multiplier counter
		} else {
			multiplier = 0;
		}
	}

	@FXML
	private void handleButtonA(ActionEvent event) {

		checkAnswer(1);
		gameOver();
		if (gameOver == false) {
			loadQuestion();
		}
	}

	@FXML
	private void handleButtonB(ActionEvent event) {

		checkAnswer(2);
		gameOver();
		if (gameOver == false) {
			loadQuestion();
		}
	}

	@FXML
	private void handleButtonC(ActionEvent event) {

		checkAnswer(3);
		gameOver();
		if (gameOver == false) {
			loadQuestion();
		}
	}

	@FXML
	private void handleButtonD(ActionEvent event) {

		checkAnswer(4);
		gameOver();
		if (gameOver == false) {
			loadQuestion();
		}
	}

	@FXML
	private void handleButtonExit(ActionEvent event) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit this awesome game?",
				ButtonType.YES, ButtonType.NO);
		alert.setTitle("Exit Program");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.YES) {
			writeScores();
			System.exit(0);
		}
	}

	@FXML
	private void handleButtonStart(ActionEvent event) {

		if (btnStart.getText().equals("Play Again")) {
			resetGame();
		} else {
			loadQuestion();
			btnStart.setText("Skip Question");
			lblMultiplier.setText("Score:");
			multiplier = 0;
			gameOver();
		}
	}

	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		String initTopTen = " -0\n -0\n -0\n -0\n -0\n -0\n -0\n -0\n -0\n -0";
		File leaderBoard = new File("leaderboard.txt");
		
		try {
			if(!leaderBoard.exists()) {
				
				leaderBoard.createNewFile();
			
				Files.write(Paths.get("leaderboard.txt"), initTopTen.getBytes());
			}
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
			
			
		btnA.setDisable(true);
		btnB.setDisable(true);
		btnC.setDisable(true);
		btnD.setDisable(true);
		

		try {

			String line = null;
			
			rScores = new FileReader(leaderBoard);
			BufferedReader br = new BufferedReader(rScores);

			while ((line = br.readLine()) != null) {

				String[] input = line.split("-");
				topNames.add(input[0]);
				topScores.add(Integer.parseInt(input[1]));
			}
			
			rScores.close();
			
		} catch (FileNotFoundException e) {

		} catch (IOException io) {

		}
		setScore();
	}

	private final String[][] questions = {
			{ "What is Thor's weapon?", "Truth telling lasso", "Teseract", "Green ring", "Hammer", "4" },
			{ "What is Captain America's sheild made of?", "Poop", "Cheese", "Vibranium", "Vibratium", "3" },
			{ "Who is Tony Stark better known as?", "Spider-man", "Iron man", "The Joker", "The Superman", "2" },
			{ "According to Thor how many Realms are there?", "Three", "Forty Five", "Nine", "PI", "3" },
			{ "Which actor plays 'Rhodey' in Iron Man?", "Terrance Howard", "Don Cheadle", "Sly Stallone", "Bea Arthur",
					"1" },
			{ "What Academy Award winner played Batman's arch nemisis in 1989?", "Jack Nicholas", "Jack Arsington",
					"Jack N. Jill", "Jack Nicholson", "4" },
			{ "Which actor played Mary Jane Watson in the 2002 movie Spiderman?", "Kristen Dunst", "Queen LaTifa",
					"Estelle Getty", "Joan Rivers", "1" },
			{ "What kind of radiation turned Bruce Banner into the Hulk?", "Alpha", "Beta", "Gamma", "Cellular", "3" },
			{ "Which Batman villian has an affinity for fish?", "The Penguin", "The Riddler", "Two Face", "The Joker",
					"1" },
			{ "Which comedian plays The Riddler in Batman Forever?", "Dave Chappelle", "Jim Carrey",
					"Rodney Dangerfield", "Eddie Murphy", "2" },
			{ "Who is Captain Marvel also known as?", "Marvel Man", "Superman", "George W. Bush", "Shazam", "4" } };

}
