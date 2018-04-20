package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
	
	private TableView table = new TableView();
	private final ObservableList<Team> data =
	        FXCollections.observableArrayList(
	        	new Team(1, "chicken"),
	        	new Team(2, "pizzer")
	        );
	
	@Override
	public void start(Stage primaryStage) {
		
		Scene scene1 = new Scene(new Group());
		primaryStage.setTitle("Milestone 1");
		primaryStage.setWidth(350);
		primaryStage.setHeight(500);
		
		final Label label = new Label("Table Test");
		label.setFont(new Font("Arial", 30));
		
		table.setEditable(true);
		
		TableColumn col1 = new TableColumn("Team Name");
		col1.setMinWidth(150);
		col1.setCellValueFactory(new PropertyValueFactory<Team, String>("teamName"));
		
		TableColumn col2 = new TableColumn("Seed");
		col2.setMinWidth(100);
		col2.setCellValueFactory(new PropertyValueFactory<Team, Integer>("seed"));

		/*
		TableColumn col3 = new TableColumn("col3");
		
		TableColumn child1 = new TableColumn("child1");
		TableColumn child2 = new TableColumn("child2");
		col3.getColumns().addAll(child1, child2);

		*/
	    
		table.setItems(data);
		table.getColumns().addAll(col1, col2);
		
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);
		
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
