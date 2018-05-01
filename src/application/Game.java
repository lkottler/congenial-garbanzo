package application;

public class Game {
	
	private Team team1, team2;
	private int[] score;
	private boolean isCompleted;
	private Team winner;
	

	Game(){
		team1 = null; team2 = null;
		winner = null;
		@SuppressWarnings("unused")
		boolean isCompleted = false;
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
	public void setTeam1(Team team1)     {this.team1    = team1;}
	public void setTeam2(Team team2)     {this.team2    = team2;}
	public void setTeam1Score(int score) {this.score[0] = score;}
	public void setTeam2Score(int score) {this.score[1] = score;}
	
	//Getters
	public int[] getScores()     {return score;}
	public boolean isCompleted(){return isCompleted;}
	public Team getWinner()      {return winner;}
	public Team getTeam1()       {return team1;}
	public Team getTeam2()       {return team2;}
	
	//Methods
	public void completeGame(){
		winner = (score[0] == score[1])
			   ? (team1.compareTo(team2) >= 0) ? team1 : team2
			   : (score[0] > score[1])         ? team1 : team2;
		
		isCompleted = true;
		team1.addScore(score[0]);
		team2.addScore(score[1]);
	}
	
}
