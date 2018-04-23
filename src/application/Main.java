package application;
	
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
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
		File f = new File("src/application/application.css");		
		mainMenu.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		
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
	
	public void viewBracket(Stage primaryStage) {
		
		Pane root = new Pane();
		Scene scene1 = new Scene(root, 800, 600);	
		scene1.getStylesheets().clear();
		File f = new File("src/application/application.css");		
		scene1.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		ArrayList<Team> teams = b.getTeams();
		int spacing = 50;
		int smallSpace = 10;
		
		
		int numTeams = b.getSize();
		int x, y, xDif, yDif;
		int btnWidth = 90, btnHeight = 30;
		int ySpace = btnHeight*2;
		
		for (int i = 0; i < 31 - Integer.numberOfLeadingZeros(numTeams); i++){
			int subTeamNum = (i == 0) ? numTeams : numTeams / (2*i);
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
		String[] teamNames = new String[]{"Bobby's Team", "Green Angles", "Duskwings", "Albertino",
				"Lightforged", "Highmountain", "Nightfallen", "Pesky Plumbers"};
		
		ArrayList<Team> teamList = new ArrayList<Team>();
		for (int i = 0; i < teamNames.length; i++){
			teamList.add(new Team(i+1, teamNames[i]));
		}
		b = new Bracket(teamList);
		launch(args);
	}
}
