package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		
		Scene scene1 = new Scene(grid);
		scene1.getStylesheets().clear();
		scene1.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
