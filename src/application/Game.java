package application;

public class Game {
	
	private Team team1, team2;
	private int[] score;
	

	Game(){
		team1 = null; team2 = null;
		score = new int[]{0, 0};
	}
	
	Game(Team team1, Team team2, int[] score){
		this.team1 = team1;
		this.team2 = team2;
		this.score = score;
	}
	Game(Team team1, Team team2, int score1, int score2){
		this(team1, team2, new int[]{score1, score2});
	}
	

	
	
}
