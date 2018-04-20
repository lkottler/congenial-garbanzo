package application;

public class Game {
	
	private Team team1, team2;
	private int[] score;
	private boolean isCompleted;
	private Team winner;
	

	Game(){
		team1 = new Team(); team2 = new Team();
		winner = team1;
		boolean isCompleted = true;
		score = new int[]{0, 0};
	}
	Game(Team team1, Team team2){
		this(team1, team2, 0, 0);
	}
	Game(Team team1, Team team2, int[] score){
		this.team1 = team1;
		this.team2 = team2;
		this.score = score;
		this.isCompleted = false;
		this.winner = null;
	}
	Game(Team team1, Team team2, int score1, int score2){
		this(team1, team2, new int[]{score1, score2});
	}
	
	//Setters
	public void setWinner(Team team){winner = team;}
	public void setWinner(boolean team1Won){winner = (team1Won) ? team1 : team2;}
	public void setCompleted(boolean isCompleted){this.isCompleted = isCompleted;}
	
	//Getters
	public Team getWinner(){return winner;}
	public boolean getCompleted(){return isCompleted;}
	public Team getTeam1(){return team1;}
	public Team getTeam2(){return team2;}
	
	
	
}
