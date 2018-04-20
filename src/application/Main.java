package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		
		Scene scene1 = new Scene(new Group());
		primaryStage.setTitle("Milestone 1");
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
		
		final Label label = new Label("chicken noodle");
		label.setAlignment(Pos.CENTER_RIGHT);
		label.setFont(new Font("Arial", 30));
		
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label);
		
		((Group) scene1.getRoot()).getChildren().addAll(vbox);
		
		primaryStage.setScene(scene1);
		primaryStage.show();
		
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		
		Scene scene2 = new Scene(grid);
		scene1.getStylesheets().clear();
		File f = new File("src/application/application.css");		
		scene1.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
