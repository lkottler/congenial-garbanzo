package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

	static Bracket b;
		
	@Override
	public void start(Stage primaryStage) {
		
		menuScreen(primaryStage);

	}
	
	private void menuScreen(Stage primaryStage){
		
		primaryStage.setTitle("Tournament Bracket Program: Milestone2"); //TODO
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Scene mainMenu = new Scene(grid, 800, 600);	
		mainMenu.getStylesheets().clear();
		
		try {
		FileInputStream input = new FileInputStream("res/img/wi.png");
		Image imgCheese = new Image(input);
		ImageView imv = new ImageView(imgCheese);
		imv.setFitHeight(400);
		imv.setFitWidth(400);
		HBox pictureBox = new HBox(imv);
		grid.add(pictureBox, 0, 1);
		} catch (FileNotFoundException e1) {e1.printStackTrace();}
		
		mainMenu.getStylesheets().add("application/application.css");
		
		Text welcomeText = new Text("Welcome to Tournament Builder!");
		welcomeText.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 30));
		grid.add(welcomeText, 0, 0, 2, 1);
		
		Button bracketBtn = new Button("View Bracket");
		bracketBtn.setOnAction(e -> viewBracket(primaryStage));
		bracketBtn.setPrefSize(120, 40);
		HBox hbBracketBtn = new HBox(10);
		
		hbBracketBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBracketBtn.getChildren().add(bracketBtn);
		grid.add(hbBracketBtn, 2, 4);
		
		Button loadBtn = new Button("Load File");
		//TODO loadBtn.setOnAction(e -> loadFile(File f));
		loadBtn.setPrefSize(120, 40);
		HBox hbLoadBtn = new HBox(10);
		hbLoadBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbLoadBtn.getChildren().add(loadBtn);
		grid.add(hbLoadBtn, 1, 4);
		
		primaryStage.setScene(mainMenu);
		primaryStage.show();
		
		
	}
	
	//Default styling (feel free to mess with these)

	enum styling {
		MAX_X (540),
		MAX_Y (540),
		X_PADDING (25),
		Y_PADDING (25),
		BTN_WIDTH (100),
		BTN_HEIGHT (40),
		FONT_SIZE (12);
		private int value;
		styling(int value) {this.value = value;}
		public int scale(double scalar) {return (int) (value * scalar);}
	}
	
	public void viewBracket(Stage primaryStage) {
		
		Pane root = new Pane();
		Scene scene1 = new Scene(root, 800, 600);	
		scene1.getStylesheets().add("application/application.css");

		ArrayList<Game> games = b.getGames();
		
		//Defaults (based around a 16 team bracket)
		int numGames = games.size(),
		x, y, xDif, yDif,
		gameCount = 0,
		iterations  = 31 - Integer.numberOfLeadingZeros(numGames);
		
		final double scalar = 3.0/iterations;
		
		System.out.println(iterations + " " + scalar);
		
		final int maxX = styling.MAX_X.scale(1),
				  maxY = styling.MAX_Y.scale(1),
				  xPad = styling.X_PADDING.scale(scalar),
				  yPad = styling.Y_PADDING.scale(scalar),
				  btnWidth = styling.BTN_WIDTH.scale(scalar),
				  btnHeight = styling.BTN_HEIGHT.scale(scalar),
				  fontSize = styling.FONT_SIZE.scale(scalar),
				  ySpace = btnHeight*2;
		
		for (int i = 0; i < iterations; i++){
			int subNumGames = numGames / (1 << i);
			xDif = i*(btnWidth + 2);
			yDif = btnHeight*(1 << i) - btnHeight;
			
			for (int j = 0; j < subNumGames; j++){
				Button btn = new Button();
				btn.setPrefSize(btnWidth, btnHeight);
				btn.setStyle("-fx-font-size: " + fontSize + "px");
				if (gameCount >= numGames){ //NO FUNCTION BUTTON
					btn.setText("");
				} else{
					Game workingGame = b.getGames().get(gameCount);
					Team team1 = workingGame.getTeam1();
					Team team2 = workingGame.getTeam2();
					int[] scores = workingGame.getScores();
					btn.setText((gameCount >= games.size()) ? "" :
						team1.getTeamName() + ": " + scores[0] + "\n"+
						team2.getTeamName() + ": " + scores[1]);
					//TODO btn.setOnAction(e -> do someshit);
				}
				//TODO fix X spacing
				int yMul = (i == 0) ? 1 : 1 << i;
				x = (j < subNumGames/2) ? 0 + xDif : maxX - xDif;
				y = (j < subNumGames/2) 
						? j*ySpace*yMul + yDif
						: (j - subNumGames/2)*ySpace*yMul + yDif;
				x += xPad; //padding
				y += yPad; //padding
				btn.setLayoutX(x);
				btn.setLayoutY(y);
				//TODO btn.setOnAction(e -> setScoreOfTeam());
				root.getChildren().add(btn);
				gameCount++;
			}
		}
		
		
		/*
		ArrayList<Team> teams = b.getTeams();
		
		int numTeams = b.getSize();
		int x, y, xDif, yDif;
		int btnWidth = 90, btnHeight = 30;
		int ySpace = btnHeight*2;
		
		for (int i = 0; i < 31 - Integer.numberOfLeadingZeros(numTeams); i++){
			int subTeamNum = numTeams / (1<<i);
			xDif = i*(btnWidth + 10); 
			yDif = btnHeight*(1 << i) - btnHeight;
			for (int j = 0; j < subTeamNum; j++){
				Button btn = new Button();
				btn.setText(teams.get(j).getTeamName()); //TODO correctly name teams
				btn.setMinSize(btnWidth, btnHeight);
				int yMul = (i == 0) ? 1 : 1 << i;
				x = (j < subTeamNum/2) ? 0 + xDif : 505 - xDif;
				y = (j < subTeamNum/2) 
						? j*ySpace*yMul + yDif
						: (j - subTeamNum/2)*ySpace*yMul + yDif;
				x += 25; //padding
				y += 25; //padding
				btn.setLayoutX(x);
				btn.setLayoutY(y);
				//TODO btn.setOnAction(e -> setScoreOfTeam());
				root.getChildren().add(btn);
				
			}
		}
		*/
		/*
		for (int i = 0; i < b.getSize(); i++){
			Button butt = new Button(teams.get(i).getTeamName());
			butt.setMinSize(90, 30);
			int x = (i >= b.getSize()/2) ? 5 : 505;
			x += 25; //padding
			butt.setLayoutX(x);
			int y = spacing*i - (i%2)*smallSpace;
			y = (i >= b.getSize()/2) ? y - spacing*b.getSize()/2 : y; //TODO
			y += 25; //padding
			butt.setLayoutY(y);
			root.getChildren().add(butt);
		}
		*/
		Button optionsBtn = new Button("Additional Options");
		optionsBtn.setOnAction(e -> menuScreen(primaryStage));
		optionsBtn.setMinSize(120, 40);
		optionsBtn.setLayoutX(655);
		optionsBtn.setLayoutY(25);
		root.getChildren().add(optionsBtn);
		
		primaryStage.setScene(scene1);
		primaryStage.show(); 
	}
	
	
	public static void main(String[] args) {
		String[] teamNames = new String[64];
		for (int i = 0; i < 64; i++){
			teamNames[i] = "Team" + i;
		}
		
		
		ArrayList<Team> teamList = new ArrayList<Team>();
		for (int i = 0; i < teamNames.length; i++){
			teamList.add(new Team(i+1, teamNames[i]));
		}
		b = new Bracket(teamList);
		
		b.assignGames();
		
		launch(args);
	}
}
