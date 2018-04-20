package application;

import java.util.ArrayList;

public class Bracket {

	private ArrayList<Team> teams;
	private ArrayList<Game> games;
	
	Bracket(){
		teams = new ArrayList<Team>();
	}
	
	Bracket(ArrayList<Team> teams){
		this.teams = teams;
	}
	
	
	public void assignGames(){
		games = new ArrayList<Game>();
		int numOfTeams = teams.size();
		if ((numOfTeams & (numOfTeams - 1)) == 0) //Checks if the number of teams is a power of 2
			for (int i = 0; i < numOfTeams/2; i++){
				games.add(new Game(teams.get(i), teams.get(numOfTeams-i-1)));
			}
		else System.out.println("Failed to create games. Invalid number of teams: " +numOfTeams);
	}
	
	//Getters
	public int getSize() {return teams.size();}
	
	
}
