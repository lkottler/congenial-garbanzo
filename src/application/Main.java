package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

//test commit

public class Main extends Application {

	static Bracket b;
	
	final static int
	FRAME_HEIGHT = 800,
	FRAME_WIDTH = 1000;
	
	static MediaPlayer musicPlayer;
	static ArrayList<Media> music = new ArrayList<Media>();
	static ArrayList<Image> logos = new ArrayList<Image>();
	static Text cSongDisplay;
	
	final static String PATH_TO_RES = "src/res/";
	
	private static String thirdPlace = "Nobody";
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setResizable(false);
		primaryStage.setMinWidth(FRAME_WIDTH);
		primaryStage.setMinHeight(FRAME_HEIGHT);
		menuScreen(primaryStage);
	}
	
	
	//This function will correctly load an image given that it is in the img folder.
	private Image loadImage(String path){
		Image img = null;
		try {
			img = new Image(new File(PATH_TO_RES + "img/" + path).toURI().toURL().toString());
		} catch (MalformedURLException e) {
			System.out.println("Failed to load image: " + PATH_TO_RES + path);
			e.printStackTrace();
		} catch (NullPointerException e){
			System.out.println("Found null for Image: " + path);
			e.printStackTrace();
		}
		return img;
	}
	
	/*
	 * This function is called when in the Options menu
	 * It allows the user to reset the bracket with new teams
	 * @param rounds is the power of 2 number of teams
	 */
	private void changeSizeOfBracket(int rounds){
		int totalTeams = (1 << rounds);
		System.out.println(totalTeams);
		ArrayList<Team> newTeams = new ArrayList<Team>();
		for (int i = 0; i < totalTeams; i++){
			newTeams.add(new Team(i, "Team: " + (i + 1)));
		}
		b = new Bracket(newTeams);
	}
	
	/* 
	 * This function is called when the user clicks "Additional options button in viewBracket()
	 * It builds a new scene composed of options of things to change within the bracket itself.
	 * 
	 * You may 
	 * change the total number of teams
	 * Reload the teams from reading a file
	 * Return to the main menu
	 * 
	 */
	private void optionScreen(Stage primaryStage){
		primaryStage.setTitle("Option Menu");
		Pane optionPane = new Pane();
		
		buildDefaults(optionPane);
		
		Scene optionScene = new Scene (optionPane, FRAME_WIDTH, FRAME_HEIGHT);
		optionScene.getStylesheets().add("application/css/options.css");
		
		Label confirmed = new Label("Operation complete.");
		
		VBox vSize = new VBox();											//lists in vertical
		
		//1st row of horizontal buttons that changes rounds of games
		HBox hSize = new HBox();
		Label sizeLab = new Label("Change number of rounds (0-10):");
		TextField sizeField = new TextField(); sizeField.getStyleClass().add("textField");
		Button confirmSize = new Button("Confirm");
		sizeField.setPrefSize(80, 30);
		confirmSize.setOnAction(e -> {
			if (sizeField.getText().matches("-?\\d+")){
				int rounds = Integer.parseInt(sizeField.getText());
				if (rounds >= 0 && rounds < 11)
					changeSizeOfBracket(rounds);
				if (!hSize.getChildren().contains(confirmed))
					hSize.getChildren().add(confirmed);
			}
		});
		
		hSize.getChildren().addAll(sizeLab, sizeField, confirmSize);		
		hSize.setSpacing(10);
		hSize.setAlignment(Pos.CENTER_LEFT);
		
		
		//2nd row of horizontal buttons that reads in from a file
		HBox hFile = new HBox();
		Label fileLab = new Label("Read in teams from File:"); 
		//what does this code do
		//TODO figure out css name for textfield
		TextField fileField = new TextField(); fileField.getStyleClass().add("textField"); 
		Button confirmFile = new Button("Confirm");
		hFile.setAlignment(Pos.CENTER_LEFT);
		hFile.setSpacing(10);
		fileField.setPrefSize(200, 30);
		confirmFile.setOnAction(e -> {
			initTeamsByFile(fileField.getText()); 
			if (!hFile.getChildren().contains(confirmed))
				hFile.getChildren().add(confirmed);
		});
		
		hFile.getChildren().addAll(fileLab, fileField, confirmFile);	
		
		
		HBox primaryBtns = new HBox();
		primaryBtns.setSpacing(25);
		
		//extra buttons (go to main page, view of bracket, clear option menu) and texts
		
		Button homeBtn = new Button("Title Screen");									//goes back to W
		homeBtn.setOnAction(e -> menuScreen(primaryStage));
		
		Button bracketBtn = new Button("Return to Bracket");							//goes back to bracket
		bracketBtn.setOnAction(e -> viewBracket(primaryStage));
		
		Button clearBtn = new Button("Clear Scores");												//resets option page
		clearBtn.setOnAction(e -> changeSizeOfBracket(32-Integer.numberOfLeadingZeros(b.getSize()/2)));

		primaryBtns.getChildren().addAll(homeBtn, bracketBtn, clearBtn);
		
		

		//this part lists all of the horizontal boxes in a vertical manner
		vSize.setAlignment(Pos.TOP_LEFT);
		vSize.getChildren().addAll(hSize, hFile, primaryBtns);
		vSize.setLayoutX(20);
		vSize.setLayoutY(20);
		vSize.setSpacing(25);
		
		Label me = new Label("Logan is really cool");
		me.getStyleClass().remove("label");
		me.setLayoutY(FRAME_HEIGHT - 40);
		optionPane.getChildren().addAll(vSize, me);
		
		Label neesh = new Label("Neeshan has light up shoes");
		neesh.getStyleClass().remove("label");
		neesh.setLayoutY(FRAME_HEIGHT - 60);
		optionPane.getChildren().addAll(neesh);
		
		Image bee = loadImage("logos/bee.png");
		ImageView imvB = new ImageView(bee);
		imvB.setScaleX(1.1);
		imvB.setScaleY(1.1);
		// I took out the bee image
		HBox bBox = new HBox(imvB);
		bBox.setLayoutX((FRAME_WIDTH - bee.getWidth()) / 2);
		bBox.setLayoutY((FRAME_HEIGHT- bee.getHeight())/ 2 + 25);
		optionPane.getChildren().add(bBox);
		
		imvB.getStyleClass().add("image");
		
		//I want to change the theme of the game, how do I make a new css file and change those things?
		imvB.setOnMouseClicked(e -> optionScene.getStylesheets().add("application/css/chicken.css"));
			
		
		primaryStage.setScene(optionScene);
		primaryStage.show();
		
	}
	/*
	 * This function is called after variables have been initialized in initVars()
	 * This will be the first screen the user sees, and must remain fairly simply.
	 * The user may click on the "w" image to call function viewBracket() moving onto another scene.
	 */
	private void menuScreen(Stage primaryStage){
		primaryStage.setTitle("Welcome to my Ȟ̸̳͚̝̖̂ͪ̈́́ȩ͙͚͇͎͓̣ͤá̞̖̬͔̟̈́̆͋̌̀͜͟v̸̙͎̇ͬͪͬͤē̛̖͙̻̩̩̻͌̚͠n͍̬͈̝̙̱̱̰̔ͩͣͣ̂ͧ̓̃"); //maybe we need to change
		Pane menuPane = new Pane();
		buildDefaults(menuPane);													//sound bar
		
		Scene menuScene = new Scene(menuPane, FRAME_WIDTH, FRAME_HEIGHT);
		menuScene.getStylesheets().add("application/css/mainMenu.css");
		
		int[] cData = new int[]{FRAME_WIDTH / 2, FRAME_HEIGHT /2, 300};
		
		//circle for the W logo
		Circle whiteP = new Circle();
		whiteP.setCenterX(cData[0]);
		whiteP.setCenterY(cData[1]);
		whiteP.setFill(javafx.scene.paint.Color.WHITE);
		whiteP.setRadius(cData[2]);
		whiteP.setOnMouseClicked(e -> viewBracket(primaryStage));
		
//		Image secret = loadImage("menu.png");
//		ImageView imvS = new ImageView(secret);
//		imvS.setOpacity(0.015);
//		imvS.setScaleX((cData[2] * 2) / (secret.getWidth()));
//		imvS.setScaleY((cData[2] * 2) / (secret.getHeight()));
//		HBox secretRegion = new HBox(imvS);
//		secretRegion.setLayoutX(cData[0] - cData[2]+ 57);
//		secretRegion.setLayoutY(cData[1] - cData[2]+ 57);
//		
//		FadeTransition fadeIn = new FadeTransition(Duration.minutes(10), imvS);
//		fadeIn.setFromValue(0);
//		fadeIn.setToValue(1);
//		fadeIn.play();
//		
//		menuPane.getChildren().addAll(whiteP, secretRegion);
//		//End of secret code
		
		menuPane.getChildren().addAll(whiteP);
		
		//The Wisconsin logo
		Image wisconsin = loadImage("logos/wi.png");
		ImageView imvW = new ImageView(wisconsin);
		imvW.setScaleX(1.1);
		imvW.setScaleY(1.1);
		HBox wiscoHBox = new HBox(imvW);
		wiscoHBox.setLayoutX((FRAME_WIDTH - wisconsin.getWidth()) / 2);
		wiscoHBox.setLayoutY((FRAME_HEIGHT- wisconsin.getHeight())/ 2 + 25);
		menuPane.getChildren().add(wiscoHBox);
		
		imvW.getStyleClass().add("image");
		imvW.setOnMouseClicked(e -> viewBracket(primaryStage));
			
		primaryStage.setScene(menuScene);
		primaryStage.sizeToScene();
		primaryStage.show();	
		
	}
	/*
	 * Styling of the scaling of all the properties
	 */
	//Default styling (feel free to mess with these)
	enum st {
		MAX_X (FRAME_WIDTH - 180),
		MAX_Y (FRAME_HEIGHT - 60),
		X_PADDING (20),
		Y_PADDING (20),
		BTN_WIDTH (100),
		BTN_HEIGHT (40),
		FONT_SIZE (12);
		private int value;
		st(int value) {this.value = value;}
		public int scale(double scalar) {return (int) (value * scalar);}
		public int val() { return value;}
	}

	/*
	 * This function is called when the player has reached the final game of the tournament.
	 * I found it fitting to have a more epic battle for the last game
	 * When scores are locked in, the top 3 teams are shown.
	 * 
	 * TODO: fix all-way-tie-breakers for 3rd place (4 teams same score)
	 */
	private void champScene(Stage primaryStage){
		Pane championship = new Pane();
		buildDefaults(championship); //music bar
		Scene champScene = new Scene(championship);
		champScene.getStylesheets().add("application/css/championship.css");
		ArrayList<Game> games = b.getGames();
		
		if (games.size() == 0){ //hard code in one winner (no possible game to display)
			String winner = (b.getSize() > 0) ? "" + b.getTeams().get(0).getTeamName() : "Nobody :'(";
			Label noFun = new Label("You're no fun...\nThe winner is: " + winner);
			noFun.setFont(Font.font("Verdana", 32));
			noFun.setLayoutY(FRAME_HEIGHT / 2 - 100);
			noFun.setLayoutX(FRAME_WIDTH  / 2 - 80);
			noFun.setAlignment(Pos.CENTER_LEFT);
			
			championship.getChildren().add(noFun);
			
		} else { //else statement may or may not be necessary.	
			
			final Game championshipGame = (games.get(games.size() - 1).getTeam1() != null) //make sure the game is defined
					? games.get(games.size() - 1)
					: games.get(0);
			int[] scores = championshipGame.getScores();
			Team[] teams = new Team[]{championshipGame.getTeam1(), championshipGame.getTeam2()};
			String t1Name = teams[0].getTeamName(), t2Name = teams[1].getTeamName();
			Label[] teamLabels = new Label[]{
					new Label(t1Name + "'s Score:"),
					new Label(t2Name + "'s Score:")},
					
					finalScores = new Label[]{
					new Label(""),
					new Label("")};
					
			TextField[] textFields = new TextField[]{
					new TextField(), //team1 textfield
					new TextField()};//team2 textfield
			HBox[] scoreBoxes = new HBox[]{
					new HBox(), new HBox()};
			VBox[] teamVBoxes = new VBox[]{
					new VBox(), new VBox()};
			Image[] teamImages = new Image[]{null, null};
			while (teamImages[0] == teamImages[1]){
				Image randomLogo = getRandomLogo();
				if (randomLogo == null) break;
				teamImages[0] = randomLogo;
				teamImages[1] = getRandomLogo();
			}
			ImageView[] teamImv = new ImageView[]{new ImageView(teamImages[0]), new ImageView(teamImages[1])};

			for (int i = 0; i < 2/*scoreBoxes.length*/; i++){
				
				teamImv[i].setFitHeight(180);
				teamImv[i].setFitWidth(220);
				
				teamVBoxes[i].setAlignment(Pos.TOP_CENTER);
				teamVBoxes[i].setLayoutX(i*(FRAME_WIDTH / 2.5) + FRAME_WIDTH / 6);
				teamVBoxes[i].setLayoutY(100);
				
				if (scores[i] != 0) textFields[i].setText("" + scores[i]);
				teamLabels[i].setFont(Font.font("Verdana", 16));
				finalScores[i].setFont(Font.font("Verdana", 48));
				textFields[i].setPrefSize(50, 60);
				textFields[i].setFont(Font.font("Verdana", 16));
				scoreBoxes[i].setAlignment(Pos.CENTER_LEFT);
				scoreBoxes[i].setPadding(new Insets(15, 12, 15, 12));
				scoreBoxes[i].setMaxSize(240, 60);
				scoreBoxes[i].setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
				scoreBoxes[i].setSpacing(10);
				scoreBoxes[i].getChildren().addAll(teamLabels[i],textFields[i]);
				
				teamVBoxes[i].getChildren().addAll(teamImv[i], scoreBoxes[i], finalScores[i]);
				championship.getChildren().add(teamVBoxes[i]);
			}
			
			Image vsImg = loadImage("vs.png");
			ImageView vsImv = new ImageView(vsImg);
			vsImv.setFitHeight(100);
			vsImv.setFitWidth(120);
			vsImv.setLayoutX(FRAME_WIDTH/2 - 80);
			vsImv.setLayoutY(100);
			
			Image crown = loadImage("crown.png");
			ImageView crownImv = new ImageView(crown);
			crownImv.setFitHeight(120); //slight smush
			crownImv.setFitWidth(132);
			crownImv.setLayoutY(0);
			
			Image podium = loadImage("podium.png");
			ImageView podiumImv = new ImageView(podium);
			//podiumImv.setFitHeight(248);
			//podiumImv.setFitWidth(850);
			podiumImv.setLayoutY(FRAME_HEIGHT / 2 + 130);
			podiumImv.setLayoutX(20);
			
			//TODO 
			// Implement cut-scene here to allow time for loading imgs.
			
			Label[] podiumWinners = new Label[]{
					new Label(), new Label(), new Label()
			};
			
			int[][] coordinates = new int[][]
			   {{110,FRAME_HEIGHT / 2 + 180},
				{380,FRAME_HEIGHT / 2 + 100},
				{650,FRAME_HEIGHT / 2 + 220}};
			for (int i = 0; i < podiumWinners.length; i++){
				podiumWinners[i].setLayoutX(coordinates[i][0]);
				podiumWinners[i].setLayoutY(coordinates[i][1]);
				podiumWinners[i].setFont(Font.font("Verdana", 30));
			}

			Button completeBtn = new Button("Lock in Scores");
			completeBtn.setPrefSize(120, 60);
			completeBtn.setLayoutX(FRAME_WIDTH/2 - 80);
			completeBtn.setLayoutY(240);
			completeBtn.setOnAction(e -> {
				
				this.thirdPlace = "Nobody!";
				if (games.size() > 2){
					Game[] prevGames = new Game[]{games.get(games.size() - 3), games.get(games.size() - 2)};
					if (prevGames[0] == null) prevGames[0] = prevGames[1];
					
					Team[] losers = new Team[2];
					int[] oldScores = new int[2];
					
					for (int i = 0; i < 2; i ++){ //TODO something strange is going on here...
						if (prevGames[i].getWinner() == prevGames[i].getTeam1()){
							losers[i] = prevGames[i].getTeam2();
							oldScores[i] = prevGames[i].getScores()[1];
						} else{
							losers[i] = prevGames[i].getTeam1();
							oldScores[i] = prevGames[i].getScores()[0];
						}
					} if (!(losers[0] == null || losers[1] == null)){
					this.thirdPlace = (oldScores[0] > oldScores[1])
							? losers[0].getTeamName()
							: losers[1].getTeamName();
					}
				}
				
				  if (textFields[0].getText().matches("-?\\d+")){ //-? --> negative sign (one or none), \\d+ --> one or more digits
					scores[0] = Integer.parseInt(textFields[0].getText());
					championshipGame.setTeam1Score(scores[0]);
					finalScores[0].setText("" + scores[0]);
				} if (textFields[1].getText().matches("-?\\d+")){
					scores[1] = Integer.parseInt(textFields[1].getText());
					championshipGame.setTeam2Score(scores[1]);
					finalScores[1].setText("" + scores[1]);
				}
				b.completeGame(championshipGame);
				Team winner = championshipGame.getWinner();
				podiumWinners[1].setText(winner.getTeamName());
				podiumWinners[0].setText((winner == championshipGame.getTeam1()) 
								? championshipGame.getTeam2().getTeamName()
								: championshipGame.getTeam1().getTeamName());
				
				podiumWinners[2].setText(thirdPlace);
				
				
				crownImv.setLayoutX((championshipGame.getWinner() == teams[0])
						? FRAME_WIDTH / 6 + 42
						: FRAME_WIDTH / 6 + 42 + FRAME_WIDTH / 2.5);
								
				if (!championship.getChildren().contains(crownImv)){ //NOTE DO NOT COMBINE THESE
					championship.getChildren().addAll(crownImv, podiumImv);
					championship.getChildren().addAll(podiumWinners[0], podiumWinners[1], podiumWinners[2]);
				}
			});
			
			championship.getChildren().addAll(completeBtn, vsImv);
		}
		
		// Default button to go back (feel free to change)
		Button returnBtn = new Button("Back");
		returnBtn.setLayoutX(FRAME_WIDTH-90);
		returnBtn.setLayoutY(FRAME_HEIGHT-100);
		returnBtn.setOnAction(e -> viewBracket(primaryStage));
		championship.getChildren().add(returnBtn);
		
		primaryStage.setScene(champScene);
		primaryStage.show();
	}
	
	/*
	 * This function makes a sudo-tree from a linear list.
	 * It returns what *would* be the parent of a node, given it is in an ArrayList
	 * This function is necessary for recursive changing of scores in the case a root score is changed.
	 */
	private static int getParentIndex(int total, int curr){
		int offset = 0;
		int temp = 0;
		while (true){
			offset += total;
			if (curr < offset) break;
			temp = offset;
			total /= 2;
		}
		return offset + (curr - temp)/2;
	}
	
	/*
	 * This function servers to update the text of a button that is not yet ready for functionality
	 * This is mostly only called when one of the two children of the game has been completed.
	 */
	private void updateGameBtn(Button btn, Game game, Pane root, int paneWidth, Stage primaryStage, double scalar, Scene scene){
		Team t1 = game.getTeam1(), t2 = game.getTeam2();
		int[] scores = game.getScores();
		
		if (t1 != null && t2 != null){
			buildBtn(btn, game, root, paneWidth, primaryStage, scalar, scene); //add functionality
		} 
		else btn.setText((t1 == null && t2 == null)  //simply change text
						? ""
						: (t1 == null)
								? "____________\n" + t2.getTeamName() + ": " + scores[1]
								: t1.getTeamName() + ": " + scores[0] + "\n____________");
	}
	/*
	 * This function adds functionality to the button passed in
	 * essentially populating a button to represent a game fully.
	 * score boxes and function buttons appear when clicked.
	 */
	private void buildBtn(Button btn, Game workingGame, Pane root, int paneWidth, Stage primaryStage, double scalar, Scene scene){
		Team[] teams = new Team[]{workingGame.getTeam1(), workingGame.getTeam2()};
		String t1Name = teams[0].getTeamName(), t2Name = teams[1].getTeamName();
		int[] scores = workingGame.getScores();
		if (workingGame.isCompleted()) btn.getStyleClass().add("completedGame");

		btn.setText(t1Name + ": " + scores[0] + "\n"+ t2Name + ": " + scores[1]);
		btn.setOnAction(e -> {
			root.getChildren().remove(root.lookup("#removeVBox"));
			VBox scoringOps = new VBox();
			scoringOps.setAlignment(Pos.TOP_CENTER);
			scoringOps.setId("removeVBox");
			scoringOps.setLayoutX(FRAME_WIDTH - 140);
			scoringOps.setLayoutY(75);
			scoringOps.setSpacing(10);
			
			Label[] teamLabels = new Label[]{
					new Label(t1Name + "'s Score:"),
					new Label(t2Name + "'s Score:")};
			TextField[] textFields = new TextField[]{
					new TextField(), //team1 textfield
					new TextField()};//team2 textfield
			HBox[] scoreBoxes = new HBox[]{
					new HBox(), new HBox()
			};
			Button setScores = new Button("(S)et Scores");
			setScores.setOnAction(p -> {
				boolean[] changed = new boolean[]{false, false};		
				if (textFields[0].getText().matches("-?\\d+")){ //-? --> negative sign (one or none), \\d+ --> one or more digits
					int newScore = Integer.parseInt(textFields[0].getText());
					changed[0] = newScore != scores[0];
					scores[0] = newScore;
					workingGame.setTeam1Score(scores[0]);
					btn.setText(t1Name + ": " + scores[0] + "\n"+ t2Name + ": " + scores[1]);
				} if (textFields[1].getText().matches("-?\\d+")){
					int newScore = Integer.parseInt(textFields[1].getText());
					changed[1] = newScore != scores[1];
					scores[1] = Integer.parseInt(textFields[1].getText());
					workingGame.setTeam2Score(scores[1]);
					btn.setText(t1Name + ": " + scores[0] + "\n"+ t2Name + ": " + scores[1]);
				}
				if (workingGame.isCompleted() && (changed[0] || changed[1])){
					btn.getStyleClass().removeAll("completedGame");
					btn.getStyleClass().add("modifiedGame");
				}
				
			});
			
			Button completeGame = new Button("(C)omplete Game");
			completeGame.setOnAction(p -> {
				b.completeGame(workingGame);

				ArrayList<Game> games = b.getGames();
				int thisGame = Integer.parseInt(btn.getId().substring(4));
				Game g = workingGame;//games.get(thisGame);
				
				boolean championReady = (
										games.size() > 2  &&
										games.get(games.size()-2).isCompleted() &&
										games.get(games.size()-3).isCompleted() &&
										root.lookup("#champBtn") == null
						);
				
				if (championReady) {
					buildChampBtn(primaryStage, root, scalar);
				}
				
				while (thisGame < games.size() - 1){ //recursively fix parents
					g = games.get(thisGame);
					Button thisGameBtn = (Button) root.lookup("#btn-" + thisGame);
					updateGameBtn(thisGameBtn, g, root, paneWidth, primaryStage, scalar, scene);
					thisGame = getParentIndex(b.getSize() / 2, thisGame);
				} 
				
				workingGame.completeGame();
				btn.getStyleClass().add("completedGame");
				
			});
			
			for (int p = 0; p < 2; p++){
				if (scores[p] != 0) textFields[p].setText("" + scores[p]);
				textFields[p].setPrefSize(40,10);
				textFields[p].setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.C) {
						completeGame.fire();
						textFields[0].clear();
						textFields[1].clear();
					} else if (key.getCode() == KeyCode.S){
						setScores.fire();
						textFields[0].clear();
						textFields[1].clear();
					} 
				});
				scoreBoxes[p].setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
				scoreBoxes[p].setSpacing(44 - (teams[p].getTeamName().length()*6));
				scoreBoxes[p].getChildren().addAll(teamLabels[p],textFields[p]);
				scoringOps.getChildren().add(scoreBoxes[p]);
			}	
			
			btn.setOnKeyPressed(key -> {
				if (key.getCode() == KeyCode.C) {
					completeGame.fire();
				} else if (key.getCode() == KeyCode.S){
					setScores.fire();
				}
			});
			
			scoringOps.getChildren().addAll(setScores, completeGame);
			root.getChildren().addAll(scoringOps);
		});
	}
	/*
	 * This function builds the "Championship" button that moves the scene to the
	 * final destination; the Championship!!
	 * This is only called when all other games have been completed.
	 */
	private void buildChampBtn(Stage primaryStage, Pane root, double scalar){
		Button championBtn = new Button();
		championBtn.setText("Championship");
		championBtn.setStyle("-fx-font-size: "+ 18*scalar + "px");
		championBtn.setId("champBtn");
		championBtn.setPrefSize(200*scalar, 100*scalar);
		championBtn.setLayoutX((FRAME_WIDTH - 180) / 2 - 80*scalar);
		championBtn.setLayoutY((FRAME_HEIGHT - 140)/ 2 - 200);
		championBtn.setOnAction(e -> champScene(primaryStage));
		root.getChildren().add(championBtn);
	}
	/*
	 * This is the "main" area  the user will be spending their time.
	 * Data is entered into games here.
	 * This function builds a GUI for the bracket.
	 */
	public void viewBracket(Stage primaryStage) {
		Pane root = new Pane();
		buildDefaults(root);
		Scene scene1 = new Scene(root, FRAME_WIDTH, FRAME_HEIGHT);	
		scene1.getStylesheets().add("application/css/bracket.css");
		
		//Defaults (designed around a 16 team bracket)
		
		ArrayList<Game> games = b.getGames();
		
		int
		numGames = b.getSize() / 2,
		x, y, xDif, yDif,
		gameCount = 0,
		iterations  = 31 - Integer.numberOfLeadingZeros(numGames);
		
		double
		xAvailSpace = 0.9 * (st.MAX_X.val() - st.X_PADDING.val()) / (iterations*2.0) / st.BTN_WIDTH.val(),
		yAvailSpace = (st.MAX_Y.val() - st.Y_PADDING.val()) / (numGames/2.0) / st.BTN_HEIGHT.val();
		
		final double scalar = (numGames < 3) ? 2
					:(xAvailSpace < yAvailSpace) // see which is restricting
						? xAvailSpace
						: yAvailSpace;

		final int maxX     = st.MAX_X.val(),
				  maxY     = st.MAX_Y.val(),
				  xPad     = st.X_PADDING.scale(scalar),
				  yPad     = st.Y_PADDING.scale(scalar),
				  btnWidth = st.BTN_WIDTH.scale(scalar),
				  btnHeight= st.BTN_HEIGHT.scale(scalar),
				  fontSize = st.FONT_SIZE.scale(scalar),
				  ySpace = (int) (maxY/(numGames/2.0)),
				  xSpace = (int) (maxX/(2.0*iterations)) - btnWidth;
		
		if (numGames < 3 || (games.get(games.size() - 3).isCompleted() && games.get(games.size() - 2).isCompleted())){
			buildChampBtn(primaryStage, root, scalar);
		}
		if (b.getSize() > 2){
			for (int i = 0; i < iterations; i++){
				int subNumGames = numGames / (1 << i);
				xDif = i*(btnWidth) + i*xSpace;
				yDif = ySpace*(1 << i)/2;
				
				for (int j = 0; j < subNumGames; j++){
					Button btn = new Button();
					btn.setPrefSize(btnWidth, btnHeight);
					btn.setStyle("-fx-font-size: " + fontSize + "px");
					btn.setId("btn-" + gameCount);
					Game workingGame = games.get(gameCount);
					if (workingGame.getTeam1() == null || workingGame.getTeam2() == null){ //NO FUNCTION BUTTON
						updateGameBtn(btn, workingGame, root, maxX, primaryStage, scalar, scene1);
					} else{
						buildBtn(btn, workingGame, root, maxX, primaryStage, scalar, scene1);
					}
					//TODO fix X spacing
					x = (j < subNumGames/2) ? 0 + xDif : maxX - xDif - btnWidth;
					y = (j < subNumGames/2)
							? j*ySpace*(1 << i) + yDif
	 						: (j - subNumGames/2)*ySpace*(1 << i) + yDif;
					x += xPad; //padding
					y += yPad; //padding
					btn.setLayoutX(x);
					btn.setLayoutY(y);
					root.getChildren().add(btn);
					gameCount++;
				}
			}
		}
		// Add view Championship results 
		if (games.get(gameCount).isCompleted()) {
			String loser = null;
			String winner = null;
			Game endChamp = games.get(gameCount);
			if (endChamp.getWinner() == endChamp.getTeam1()) {
				loser = "" + endChamp.getTeam2().getTeamName();
				winner = "" + endChamp.getTeam1().getTeamName();
			} else {
				loser = "" + endChamp.getTeam1().getTeamName();
				winner = "" + endChamp.getTeam2().getTeamName();
			}
			VBox finalResults = new VBox();
			int [] champScores = games.get(gameCount).getScores();
			int bigScore = (champScores[0] < champScores[1]) ? champScores[1] : champScores[0];
			int smallScore = (bigScore > champScores[1]) ? champScores[1] : champScores[0];
			String filler = " with a score of ";
			Label[] teams = new Label[] {new Label("Champion: " + winner + filler + bigScore)
					, new Label("Second Place: " + loser + filler + smallScore), new Label("Third place: " + this.thirdPlace )};
			for (int i = 0; i < teams.length; i++) {
				teams[i].setFont(Font.font("Verdana", 18));
				teams[i].setTextFill(Color.WHITE);
			}
			finalResults.getChildren().addAll(teams[0], teams[1], teams[2]);
			finalResults.setMinSize(200, 100);
			finalResults.setLayoutX(FRAME_WIDTH / 2 - 225);
			finalResults.setLayoutY(FRAME_HEIGHT - 125);
			finalResults.setStyle("application/css/bracket.css");;
			root.getChildren().add(finalResults);
		}
		

		// SIDE BAR RIGHT SIDE // TODO: ADD MORE OPTIONS
		Button optionsBtn = new Button("Additional Options");
		optionsBtn.setOnAction(e -> optionScreen(primaryStage));
		optionsBtn.setMinSize(120, 40);
		optionsBtn.setLayoutX(FRAME_WIDTH-140);
		optionsBtn.setLayoutY(25);
		root.getChildren().add(optionsBtn);
		
		primaryStage.setScene(scene1);
		primaryStage.show(); 
	}
	
	/*
	 * This function calls itself with the next song if 
	 * @param next is true: next song, false: previous song.
	 */
	private static void loopMusic(boolean next) { 
		int currInd = music.indexOf(musicPlayer.getMedia());
		loopMusic((next) 
				? (currInd + 1 >= music.size()) ? 0 : currInd + 1
				: (currInd - 1 < 0) ? music.size() - 1 : currInd - 1);
	}
	/*
	 * This plays music from the music instantiated give the
	 * @param mIndex, index of the music in the list stored.
	 */
	private static void loopMusic(int mIndex){
		double currVol = 100;
		String currentSong = "Hmm, something's not quite right...";
		Media songMedia = null;
		if (music.size() > 0) {
			songMedia = music.get(mIndex);
		}
		try {
			currentSong = java.net.URLDecoder.decode(songMedia.getSource(), "UTF-8"); //decodes
			currentSong = currentSong.substring(currentSong.lastIndexOf("/") + 1).substring(currentSong.lastIndexOf("\\") + 1); //removes file path
			currentSong = currentSong.substring(0, currentSong.lastIndexOf('.')); //gets rid of extension
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("Music failed to load.");
		} catch (NullPointerException e) {
			System.out.println("tried to load music and failed.");
		}
		if (musicPlayer != null) {
			musicPlayer.stop();
			currVol = musicPlayer.getVolume();
			musicPlayer.dispose();
		}
		
		cSongDisplay.setText("Now Playing: " + currentSong);
		
		try {
			musicPlayer = new MediaPlayer(music.get(mIndex));
		} catch (MediaException e) {
			System.out.println("failed to create MediaPlayer");
		}
		
		if (musicPlayer != null){
			musicPlayer.setVolume(currVol);
			musicPlayer.setOnEndOfMedia(new Runnable() {public void run(){loopMusic(true);}} );
			musicPlayer.play();
		}
	}
	/*
	 * This function is to have an overlay on all scenes.
	 * Right now its only functionality is to create the music bar at the bottom of the screen.
	 */
	private static void buildDefaults(Pane panel){ //This can add toolbars and such
		if (musicPlayer != null){
			HBox musicBar = new HBox(3);
			HBox musicText = new HBox();
			HBox musicButtons = new HBox(3);
			
			musicButtons.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
			musicButtons.setAlignment(Pos.BOTTOM_RIGHT);
			musicButtons.setLayoutX(400);
			musicButtons.setMinWidth(345);
			
			musicText.setMinWidth(FRAME_WIDTH - 345);
			
			musicBar.setLayoutX(0);
			musicBar.setLayoutY(FRAME_HEIGHT - 25);
			musicBar.setPrefWidth(FRAME_WIDTH);
			musicBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			musicBar.setAlignment(Pos.BOTTOM_LEFT);
			musicBar.setMinHeight(25);
			
			Button pSong = new Button("Previous");
			pSong.getStyleClass().add("soundButton");
			pSong.setOnAction(e -> {
				loopMusic(false);
			});
			Button nSong = new Button("Next");
			nSong.getStyleClass().add("soundButton");
			nSong.setOnAction(e -> {
				loopMusic(true);
			});
			
			Text volText = new Text();
			volText.setFont(Font.font("Verdana", 20));
			volText.setFill(Color.WHITE);
			Slider volumeSlider = new Slider();
			volumeSlider.setOrientation(Orientation.HORIZONTAL);
			volumeSlider.setPrefWidth(150);
			volumeSlider.setShowTickMarks(true);
			volumeSlider.setMajorTickUnit(10);
			volumeSlider.setMinorTickCount(0);
			volumeSlider.setShowTickLabels(false);
			volumeSlider.valueProperty().addListener(
					(observable, oldvalue, newvalue) ->
					{
						int i = newvalue.intValue();
						volText.setText(Integer.toString(i) + "%");
						musicPlayer.setVolume(i / 100.0);
					});
			volumeSlider.setValue(musicPlayer.getVolume() * 100);
			
			musicText.getChildren().add(cSongDisplay);
			musicButtons.getChildren().addAll(volText, volumeSlider, pSong, nSong);
			musicBar.getChildren().addAll(musicText, musicButtons);
			panel.getChildren().add(musicBar);
		}
	}
	
	/*
	 * This function generates a random number, using standard C random vals.
	 */
	static final long
	MUL_A = 1103515245,
	MOD_M = 2147483647,
	INC_C = 12345;
	static long curr_rand;
	private static int getRandomNumber(int min, int max){
		curr_rand = (MUL_A * curr_rand + INC_C) % MOD_M;
		if (max == min) return 0;
		return (int) (curr_rand % (max - min)) + min;
	}
	/*
	 * This function returns an image of a random Logo in the Logos folder.
	 */
	private Image getRandomLogo(){
		if (logos.size() == 0) return null;
		curr_rand = System.currentTimeMillis(); //more random every time
		int random = 0;
		for (int i = 0; i < 100; i++){
			random = getRandomNumber(0, logos.size());
		}
		return logos.get(random);
	}
	
	/*
	 * This function will reset the bracket with teams read in from a file.
	 * It is called at execution from the first argument given,
	 * or from manual input from the Options menu.
	 */
	private static void initTeamsByFile(String filePath){
		int counter = 1;
		String line = null;
		ArrayList<Team> newTeams = new ArrayList<Team>();
		try {
			FileReader fr = new FileReader(filePath);
			
			BufferedReader br = new BufferedReader(fr);
			
			while((line = br.readLine()) != null){
				newTeams.add(new Team(counter, line));
			}
			br.close();
		} catch(FileNotFoundException e){
			System.out.println("Uh oh! Couldn't find the file at:\n" + filePath);
		} catch(IOException e){
			System.out.println("Error reading file:\n" + filePath);
		}
		b = new Bracket(newTeams);		
	}
	
	/*
	 * This function loads in a lot of the resources required during this program.
	 * It is the basis for what values are going to be.
	 */
	public static void initVars(String fileOfTeams){
		
		cSongDisplay = new Text("Init");
		cSongDisplay.setFont(Font.font("Verdana", 20));
		
		//Animate the text
		double availWidth = FRAME_WIDTH-350;
		double msgWidth = 700;
		KeyValue initKeyValue = new KeyValue(cSongDisplay.translateXProperty(), availWidth);
		KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
		
		KeyValue endKeyValue = new KeyValue(cSongDisplay.translateXProperty(), -1.0 * msgWidth);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(10), endKeyValue);
		
		Timeline timeline = new Timeline(initFrame, endFrame);
		
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		File musicFolder = new File(PATH_TO_RES + "snd/music");
		int bennieAndtheJets = 0;
		if (musicFolder.exists()){
			File[] musicFiles = musicFolder.listFiles();
			for (File f : musicFiles){
				if (f.toString().contains("Elton John"))
					bennieAndtheJets = music.size();
				music.add(new Media(f.toURI().toString()));
			}
		}
		loopMusic(bennieAndtheJets);
		if (musicPlayer != null)
			musicPlayer.setVolume(.25); //init sound to 25%
		
		File logosFolder = new File(PATH_TO_RES + "img/logos");
		
		if (logosFolder.exists()){
			File[] logosFiles = logosFolder.listFiles();
			for (File f : logosFiles){
				Image thisLogo = null;
				String path = f.getPath();
					   path = path.substring(path.lastIndexOf("/") + 1).substring(path.lastIndexOf("\\") + 1);
				try {
					thisLogo = new Image(f.toURI().toURL().toString());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				if (thisLogo != null){
					logos.add(thisLogo);
				}
			}
		}
		
		//initTeamsByFile("src/teamsTest.txt");
		if (b == null){
			int tempTeams = 16;
			String[] teamNames = new String[tempTeams];
			for (int i = 0; i < tempTeams; i++){
				teamNames[i] = "Team " + (i+1);
			}
			
			ArrayList<Team> teamList = new ArrayList<Team>();
			for (int i = 0; i < teamNames.length; i++){
				teamList.add(new Team(i+1, teamNames[i]));
			}
			b = new Bracket(teamList);
		}
		
		// TESTING CODE: THIS WILL COMPLETE EVERY GAME
		//for (Game g: b.getGames()){ g.completeGame();}
	}
	/*
	 * main, pretty self explanatory.
	 */
	public static void main(String[] args) {
		initVars((args.length > 0) ? args[0] : "");
		launch();
	}
}
