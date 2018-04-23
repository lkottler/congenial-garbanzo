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
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Scene mainMenu = new Scene(grid, 800, 600);	
		mainMenu.getStylesheets().clear();
		File f = new File("src/application/application.css");		
		mainMenu.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		
		Text welcomeText = new Text("Welcome to Tournament Builder!");
		welcomeText.setFont(Font.font("Courier New", FontWeight.NORMAL, 20));
		grid.add(welcomeText, 0, 0, 2, 1);
		
		Button bracketBtn = new Button("View Bracket");
		bracketBtn.setOnAction(e -> viewBracket(primaryStage));
		HBox hbBracketBtn = new HBox(10);
		
		hbBracketBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBracketBtn.getChildren().add(bracketBtn);
		grid.add(hbBracketBtn, 1, 4);
		
		primaryStage.setScene(mainMenu);
		primaryStage.show();
		
		
	}
	
	public void viewBracket(Stage primaryStage) {
		
Pane root = new Pane();
		Scene scene1 = new Scene(root, 800, 600);	
		scene1.getStylesheets().clear();
		File f = new File("src/application/application.css");		
		scene1.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		
		primaryStage.setTitle("Bracket");
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		/*
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		*/
		
		ArrayList<Team> teams = b.getTeams();
		int spacing = 50;
		int smallSpace = 5;
		for (int i = 0; i < b.getSize(); i++){
			Button butt = new Button(teams.get(i).getTeamName());
			butt.setMinSize(130, 50);
			butt.setLayoutX((i >= b.getSize()/2) ? 5 : 305);
			int y = spacing*i - (i%2)*smallSpace;
			y = (i >= b.getSize()/2) ? y - spacing*b.getSize()/2 : y; //TODO
			butt.setLayoutY(y);
			root.getChildren().add(butt);
		}
			
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
