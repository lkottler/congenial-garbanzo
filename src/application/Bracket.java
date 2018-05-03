package application;

import java.util.ArrayList;
/*
 * Utilizes the game and team classes to create a bracket
 * interface
 */
public class Bracket {

	private ArrayList<Team> teams;
	private ArrayList<Game> games;
	
	/**
	 * default constructor
	 */
	Bracket(){
		teams = new ArrayList<Team>();
	}
	
	/**
	 * constructor that takes list of teams as input
	 * @param teams
	 */
	Bracket(ArrayList<Team> teams){
		this.teams = teams;
		this.initGames(); //this method creates the games ArrayList according to the amount of teams -- see method body below
	}
	
	/**
	 * method that sets amount of games in the tournament to the number of teams - 1
	 */
	private void initGames(){
		games = new ArrayList<Game>();
		int numOfTeams = teams.size();
		if (numOfTeams < 2) return;
		if ((numOfTeams & (numOfTeams - 1)) == 0){ //Checks if the number of teams is a power of 2
			for (int i = 0; i < numOfTeams/2; i++){
				games.add(new Game(teams.get(i), teams.get(numOfTeams-i-1)));
			}
			int temp = numOfTeams / 4;
			while (temp > 1){
				for (int i = 0; i < temp; i++)
					games.add(new Game());
				temp /= 2;
			}
			games.add(new Game()); //championship game
		}
		else {
			System.out.println("Failed to create games. Invalid number of teams: " +numOfTeams + "\nRetrying...");
			ArrayList<String> removedTeams = new ArrayList<String>();
			while ((teams.size() & (teams.size() - 1)) != 0){
				removedTeams.add(teams.get(teams.size() - 1).getTeamName());
				teams.remove(teams.size() - 1);
			}
			System.out.print("Had to remove " + removedTeams.size() + " team(s): ");
			for (String s: removedTeams){
				System.out.print('"' + s + "\" - ");
			}
			System.out.println();
			initGames();
		}
	}
	
	/**
	 * Calculates parent index of a game
	 * @param total total number of games
	 * @param curr index of this game
	 * @return
	 */
	private static int getParentIndex(int total, int curr){
		int offset = 0;
		int temp = 0;
		while (true){
			offset += total;
			if (curr < offset) break;
			temp = offset;
			total /= 2;
		}
		return offset + (curr - temp)/2;
	}
	/**
	 * makes call to overloaded completeGame method with int and Game parameters
	 * @param gameIndex
	 */
	public void completeGame(int gameIndex){ this.completeGame(gameIndex, games.get(gameIndex));};
	
	/**
	 * makes call to overloaded completeGame method with int and Game parameters
	 * @param thisGame
	 */
	public void completeGame(Game thisGame){ this.completeGame(games.indexOf(thisGame), thisGame); };
	
	/**
	 * Sets winner of game and sets game as completed
	 * @param gameIndex
	 * @param thisGame
	 */
	private void completeGame(int gameIndex, Game thisGame){
		thisGame.completeGame();
		if (gameIndex != games.size() - 1){
			Game parentGame = games.get(getParentIndex(this.getSize() / 2, gameIndex));
			if (gameIndex % 2 == 0)
				 parentGame.setTeam1(thisGame.getWinner());
			else parentGame.setTeam2(thisGame.getWinner());
			if (parentGame.isCompleted()) this.completeGame(parentGame);
		}
		
	}
	
	//Setters
	public void setTeams(ArrayList<Team> teams){this.teams = teams;}
	
	//Getters
	public ArrayList<Team> getTeams() {return teams;}
	public ArrayList<Game> getGames() {return games;}
	public int getSize() {return teams.size();}
	
	
	
}
