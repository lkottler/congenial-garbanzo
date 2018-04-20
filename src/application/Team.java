package application;

public class Team {
	
	private int seed;
	private String teamName;
	private int totalScore = 0;
	
	Team(){
		seed = 0;
		teamName = "default";
	}
	Team(int seed, String teamName){
		this.seed = seed;
		this.teamName = teamName;
	}
	
	//Getters
	public int getSeed(){return seed;}
	public int getTotalScore(){return totalScore;}
	public String getTeamName(){return teamName;}
	
	//Setters
	public void setSeed(int seed){this.seed = seed;}
	public void setTeamName(String teamName){this.teamName = teamName;}
	
	//Methods
	public int compareTo(Team team){return totalScore - team.getTotalScore();}
	public void addScore(int score){totalScore += score;}
	
}
