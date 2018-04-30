package application;
	
import java.io.File;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {

	static Bracket b;
	
	final static int
	frameHeight = 800,
	frameWidth = 1000;
	
	static MediaPlayer musicPlayer;
	static ArrayList<Media> music = new ArrayList<Media>();
	static Text cSongDisplay;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setResizable(false);
		primaryStage.setMinWidth(frameWidth);
		primaryStage.setMinHeight(frameHeight);
		//primaryStage.setMaxWidth(frameWidth);
		//primaryStage.setMaxHeight(frameHeight);
		menuScreen(primaryStage);
	}
	
	private Image loadImage(String path){
		Image img = null;
		try {
			img = new Image(new File("res/img/" + path).toURI().toURL().toString());
		} catch (MalformedURLException e) {
			System.out.println("Failed to load image: res/img/" + path);
			e.printStackTrace();
		}
		return img;
	}
	
	private void menuScreen(Stage primaryStage){
		
		primaryStage.setTitle("Welcome to my Ȟ̸̳͚̝̖̂ͪ̈́́ȩ͙͚͇͎͓̣ͤá̞̖̬͔̟̈́̆͋̌̀͜͟v̸̙͎̇ͬͪͬͤē̛̖͙̻̩̩̻͌̚͠n͍̬͈̝̙̱̱̰̔ͩͣͣ̂ͧ̓̃"); //TODO
		Pane menuPane = new Pane();
		buildDefaults(menuPane);
		Scene menuScene = new Scene(menuPane, frameWidth, frameHeight);
		menuScene.getStylesheets().add("application/application.css");
		System.out.println(menuScene.getHeight() + " " + menuScene.getWidth());
		
		int[] cData = new int[]{frameWidth / 2, frameHeight /2, 300};
		Circle whiteP = new Circle();
		whiteP.setCenterX(cData[0]);
		whiteP.setCenterY(cData[1]);
		whiteP.setFill(javafx.scene.paint.Color.WHITE);
		whiteP.setRadius(cData[2]);
		
		// pay no attention to this code.
		Image secret = loadImage("menu.png");
		ImageView imvS = new ImageView(secret);
		imvS.setOpacity(0.010);
		imvS.setScaleX((cData[2] * 2) / (secret.getWidth()));
		imvS.setScaleY((cData[2] * 2) / (secret.getHeight()));
		HBox secretRegion = new HBox(imvS);
		secretRegion.setLayoutX(cData[0] - cData[2]+ 57);
		secretRegion.setLayoutY(cData[1] - cData[2]+ 57);
		
		menuPane.getChildren().addAll(whiteP, secretRegion);
		
		Image wisconsin = loadImage("wi.png");
		ImageView imvW = new ImageView(wisconsin);
		imvW.setScaleX(1.1);
		imvW.setScaleY(1.1);
		HBox wiscoHBox = new HBox(imvW);
		wiscoHBox.setLayoutX((frameWidth - wisconsin.getWidth()) / 2);
		wiscoHBox.setLayoutY((frameHeight- wisconsin.getHeight())/ 2 + 25);
		menuPane.getChildren().add(wiscoHBox);
		
		imvW.getStyleClass().add("image");
		imvW.setOnMouseClicked(e -> viewBracket(primaryStage));
		
		
		
		Button bracketBtn = new Button("View Bracket");
		bracketBtn.setOnAction(e -> {
			File duckFile = new File("res/snd/duck.mp3");
			Media duckSound = new Media(duckFile.toURI().toString());
			MediaPlayer duckPlayer = new MediaPlayer(duckSound);
			duckPlayer.play();
			viewBracket(primaryStage);
		});
		
		
		primaryStage.setScene(menuScene);
		primaryStage.sizeToScene();
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
		buildDefaults(root);
		Scene scene1 = new Scene(root, frameWidth, frameHeight);	
		scene1.getStylesheets().add("application/application.css");

		
		ArrayList<Game> games = b.getGames();
		
		//Defaults (based around a 16 team bracket)
		HBox[] remove = null;
		int toRemove = -1,
		numGames = games.size(),
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
	
	
	private static void loopMusic(boolean next) { 
		int currInd = music.indexOf(musicPlayer.getMedia());
		loopMusic((next) 
				? (currInd + 1 >= music.size()) ? 0 : currInd + 1
				: (currInd - 1 < 0) ? music.size() - 1 : currInd - 1);
	}
	private static void loopMusic(String path){ loopMusic(music.indexOf(path));} //Overloaded
	private static void loopMusic(int mIndex){
		double currVol = 100;
		String currentSong = "Hmm, something's not quite right...";
		
		Media songMedia = music.get(mIndex);
		try {
			currentSong = java.net.URLDecoder.decode(songMedia.getSource(), "UTF-8"); //decodes
			currentSong = currentSong.substring(currentSong.lastIndexOf("/") + 1).substring(currentSong.lastIndexOf("\\") + 1); //removes file path
			currentSong = currentSong.substring(0, currentSong.lastIndexOf('.')); //gets rid of extension
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (musicPlayer != null) {
			musicPlayer.stop();
			currVol = musicPlayer.getVolume();
			musicPlayer.dispose();
			System.out.println(currentSong);
		}
		
		cSongDisplay.setText("Now Playing: " + currentSong);
		musicPlayer = new MediaPlayer(music.get(mIndex));
		musicPlayer.setVolume(currVol);
		musicPlayer.setOnEndOfMedia(new Runnable() {public void run(){loopMusic(true);}} );
		musicPlayer.play();
	}

	private static void buildDefaults(Pane panel){ //This can add toolbars and such
		HBox musicBar = new HBox(3);
		HBox musicText = new HBox();
		HBox musicButtons = new HBox(3);
		
		musicButtons.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		musicButtons.setAlignment(Pos.BOTTOM_RIGHT);
		musicButtons.setLayoutX(400);
		musicButtons.setMinWidth(370);
		
		musicText.setMinWidth(frameWidth - 370);
		
		musicBar.setLayoutX(0);
		musicBar.setLayoutY(frameHeight - 25);
		musicBar.setPrefWidth(frameWidth);
		musicBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		musicBar.setAlignment(Pos.BOTTOM_LEFT);
		musicBar.setMinHeight(25);
		
		Button pSong = new Button("Previous Song");
		pSong.setOnAction(e -> {
			loopMusic(false);
		});
		Button nSong = new Button("Next Song");
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
		volumeSlider.setValue(150);
		
		musicText.getChildren().add(cSongDisplay);
		musicButtons.getChildren().addAll(volText, volumeSlider, pSong, nSong);
		musicBar.getChildren().addAll(musicText, musicButtons);
		panel.getChildren().add(musicBar);
		
	}
	
	public static void initVars(){
		
		cSongDisplay = new Text("Init");
		cSongDisplay.setFont(Font.font("Verdana", 20));
		
		//Animate the text
		double availWidth = frameWidth-230;
		double msgWidth = 700;
		KeyValue initKeyValue = new KeyValue(cSongDisplay.translateXProperty(), availWidth);
		KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
		
		KeyValue endKeyValue = new KeyValue(cSongDisplay.translateXProperty(), -1.0 * msgWidth);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(7), endKeyValue);
		
		Timeline timeline = new Timeline(initFrame, endFrame);
		
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		
		File musicFolder = new File("res/snd/music");
		File[] musicFiles = musicFolder.listFiles();
		
		for (File f : musicFiles){
			music.add(new Media(f.toURI().toString()));
		}
		loopMusic(2);
		
		int tempTeams = 32;
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
		
	}
	
	public static void main(String[] args) {
		
		initVars();
		launch(args);
	}
}
