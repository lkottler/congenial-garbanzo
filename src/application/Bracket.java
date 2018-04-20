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
	
	public int getSize() {return teams.size();}
	
}
