package application;

public class Game {
	
	private Team team1, team2;
	private int[] score;
	private boolean isCompleted;
	private Team winner;
	
	/**
	 * default constructor
	 */
	Game(){
		team1 = null; team2 = null;
		winner = null;
		score = new int[]{0, 0};
	}
	/**
	 * constructor with 2 teams as input, default scores set to 0
	 * @param team1
	 * @param team2
	 */
	Game(Team team1, Team team2){
		this(team1, team2, 0, 0);
	}
	/**
	 * constructor with 2 teams and array of scores for team 1 and team 2 respectively as input
	 * @param team1
	 * @param team2
	 * @param score
	 */
	Game(Team team1, Team team2, int[] score){
		this.team1 = team1;
		this.team2 = team2;
		this.score = score;
		this.isCompleted = false;
		this.winner = null;
	}
	/**
	 * constructor with 2 teams and 2 scores as input
	 * @param team1
	 * @param team2
	 * @param score1
	 * @param score2
	 */
	Game(Team team1, Team team2, int score1, int score2){
		this(team1, team2, new int[]{score1, score2});
	}
	
	//Setters
	public void setTeam1(Team team1)     {this.team1    = team1;}
	public void setTeam2(Team team2)     {this.team2    = team2;}
	public void setTeam1Score(int score) {this.score[0] = score;}
	public void setTeam2Score(int score) {this.score[1] = score;}
	public void setScores(int score[]) {this.score[0] = score[0]; this.score[1] = score[1];}
	
	
	//Getters
	public int[] getScores()     {return score;}
	public boolean isCompleted(){return isCompleted;}
	public Team getWinner()      {return winner;}
	public Team getTeam1()       {return team1;}
	public Team getTeam2()       {return team2;}
	
	//Methods
	public void completeGame(){
		
		if (team1 == null || team2 == null){
			isCompleted = true;
			return;
		}
		winner = (score[0] == score[1])
			   ? (team1.compareTo(team2) >= 0) ? team1 : team2
			   : (score[0] > score[1])         ? team1 : team2;
		
		isCompleted = true;
		team1.addScore(score[0]);
		team2.addScore(score[1]);
	}
	
}
