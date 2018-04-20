package application;
	
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {

	static Bracket b;
	
	@Override
	public void start(Stage primaryStage) {
		
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
		for (int i = 0; i < b.getSize(); i++){
			Button butt = new Button(teams.get(i).getTeamName());
			butt.setPrefWidth(150);
			butt.setLayoutX((i >= b.getSize()/2) ? 5 : 305);
			int y = 30*i - i%2*5;
			int y = (i >= b.getSize()/2) ? y - 30*i); //TODO
			butt.setLayoutY((i >= b.getSize()/2) ? 30*i - i%2*5);
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
