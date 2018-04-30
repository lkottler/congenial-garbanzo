package application;

import java.util.ArrayList;
/*
 * Utilizes the game and team classes to create a bracket
 * interface
 */
public class Bracket {

	private ArrayList<Team> teams;
	private ArrayList<Game> games;
	
	Bracket(){
		teams = new ArrayList<Team>();
	}
	
	Bracket(ArrayList<Team> teams){
		this.teams = teams;
	}
	
	
	public void initGames(){
		games = new ArrayList<Game>();
		int numOfTeams = teams.size();
		if ((numOfTeams & (numOfTeams - 1)) == 0) //Checks if the number of teams is a power of 2
			for (int i = 0; i < numOfTeams/2; i++){
				games.add(new Game(teams.get(i), teams.get(numOfTeams-i-1)));
			}
		else System.out.println("Failed to create games. Invalid number of teams: " +numOfTeams);
	}
	
	public boolean newRound(){ //Returns whether it was successful creating a new round of games.
		ArrayList<Game> tempGames = new ArrayList<Game>();
		for (int i = 0; i < games.size(); i+=2){
			Game game1 = games.get(i);
			Game game2 = games.get(i+1);
			if (!game1.isCompleted() || !game2.isCompleted())
				return false;
			tempGames.add(new Game(game1.getWinner(), game2.getWinner()));
		}
		games = tempGames;	
		return true;
	}
	
	//Setters
	public void setTeams(ArrayList<Team> teams){this.teams = teams;}
	
	//Getters
	public ArrayList<Team> getTeams() {return teams;}
	public ArrayList<Game> getGames() {return games;}
	public int getSize() {return teams.size();}
	
	
	
}
