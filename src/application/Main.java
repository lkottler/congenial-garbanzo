package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

	static Bracket b;
	
	final static int
	frameHeight = 800,
	frameWidth = 1000;
		
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
		grid.setPadding(new Insets(5, 5, 5, 5));
		
		Scene mainMenu = new Scene(grid, frameWidth, frameHeight);	
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
	enum st {
		MAX_X (frameWidth - 180),
		MAX_Y (frameHeight - 140),
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
	
	private void buildBtn(Button btn, Game workingGame, Pane root, int paneWidth){
		Team[] teams = new Team[]{workingGame.getTeam1(), workingGame.getTeam2()};
		String t1Name = teams[0].getTeamName(), t2Name = teams[1].getTeamName();
		int[] scores = workingGame.getScores();
		btn.setText(t1Name + ": " + scores[0] + "\n"+ t2Name + ": " + scores[1]);
		btn.setOnAction(e -> {
			Label[] teamLabels = new Label[]{
					new Label(t1Name + "'s Score:"),
					new Label(t2Name + "'s Score:")};;
			TextField[] textFields = new TextField[]{
					new TextField(), //team1 textfield
					new TextField()};//team2 textfield
			HBox[] scoreBoxes = new HBox[]{
					new HBox(), new HBox()
			};
			for (int p = 0; p < 2; p++){
				root.getChildren().remove(root.lookup("#toRemove-" + p));
				textFields[p].setPrefSize(30,10);
				scoreBoxes[p].setId("toRemove-" + p);
				scoreBoxes[p].setSpacing(5);
				scoreBoxes[p].setLayoutX(paneWidth - (teams[p].getTeamName().length()*5) + 85);
				scoreBoxes[p].setLayoutY(75 + p*30);
				scoreBoxes[p].getChildren().addAll(teamLabels[p],textFields[p]);
				root.getChildren().add(scoreBoxes[p]);
			}			
			Button setScores = new Button();
			
			Button completeGame = new Button();
			
		});
	}
	
	public void viewBracket(Stage primaryStage) {
		Pane root = new Pane();
		Scene scene1 = new Scene(root, frameWidth, frameHeight);	
		scene1.getStylesheets().add("application/application.css");

		ArrayList<Game> games = b.getGames();
		
		//Defaults (based around a 16 team bracket)
		HBox[] remove = null;
		int toRemove = -1;
		int numGames = games.size(),
		x, y, xDif, yDif,
		gameCount = 0,
		iterations  = 31 - Integer.numberOfLeadingZeros(numGames);
		
		double
		xAvailSpace = 0.9 * (st.MAX_X.val() - st.X_PADDING.val()) / (iterations*2.0) / st.BTN_WIDTH.val(),
		yAvailSpace = (st.MAX_Y.val() - st.Y_PADDING.val()) / (numGames/2.0) / st.BTN_HEIGHT.val();
		
		final double scalar = (xAvailSpace < yAvailSpace) // see which is restricting
					? xAvailSpace
					: yAvailSpace;
		
		System.out.println(iterations + " " + scalar);
		
		final int maxX     = st.MAX_X.val(),
				  maxY     = st.MAX_Y.val(),
				  xPad     = st.X_PADDING.scale(scalar),
				  yPad     = st.Y_PADDING.scale(scalar),
				  btnWidth = st.BTN_WIDTH.scale(scalar),
				  btnHeight= st.BTN_HEIGHT.scale(scalar),
				  fontSize = st.FONT_SIZE.scale(scalar),
				  ySpace = (int) (maxY/(numGames/2.0)),
				  xSpace = (int) (maxX/(2.0*iterations)) - btnWidth;
		
		for (int i = 0; i < iterations; i++){
			int subNumGames = numGames / (1 << i);
			xDif = i*(btnWidth) + i*xSpace;
			yDif = ySpace*(1 << i)/2;
			
			for (int j = 0; j < subNumGames; j++){
				Button btn = new Button();
				btn.setPrefSize(btnWidth, btnHeight);
				btn.setStyle("-fx-font-size: " + fontSize + "px");
				if (gameCount >= numGames){ //NO FUNCTION BUTTON
					btn.setText("");
				} else{
					if (gameCount < numGames){
						Game workingGame = b.getGames().get(gameCount);
						buildBtn(btn, workingGame, root, maxX);
					}
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
		//put CHAMPIONSHIP in HERE TODO
		Button championBtn = new Button();
		championBtn.setText("ARE YOU READY");
		championBtn.setStyle("-fx-font-size: " + fontSize*1.5 + "px");
		championBtn.setPrefSize(btnWidth*1.5, btnHeight*1.5);
		championBtn.setLayoutX((maxX-btnWidth) / 2);
		championBtn.setLayoutY(maxY / 2 - btnHeight*1.8);
		root.getChildren().add(championBtn);
		
		// SIDE BAR RIGHT SIDE
		Button optionsBtn = new Button("Additional Options");
		optionsBtn.setOnAction(KevinIsHotWhatDoYouGuysThink -> menuScreen(primaryStage));
		optionsBtn.setMinSize(120, 40);
		optionsBtn.setLayoutX(frameWidth-140);
		optionsBtn.setLayoutY(25);
		root.getChildren().add(optionsBtn);
		
		
		

		//root.getChildren().remove(root.getChildren().size() - 1);
		primaryStage.setScene(scene1);
		primaryStage.show(); 
	}
	
	public static void main(String[] args) {
		int tempTeams = 64;
		String[] teamNames = new String[tempTeams];
		for (int i = 0; i < tempTeams; i++){
			teamNames[i] = "Team " + (i+1);
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
