package application;

public class Team {
	
	private int seed;
	private String teamName;
	
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
	public String getTeamName(){return teamName;}
	
	//Setters
	public void setSeed(int seed){this.seed = seed;}
	public void setTeamName(String teamName){this.teamName = teamName;}
	
	public int compareTo(Team team){return seed - team.getSeed();}
	
}
