//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:         		Team.java
// Additional Files:	options.css, bracket.css, championship.css, mainMenu.css, chicken.css
// Due Date:			May 3rd, 2018
// Course:          	CS 400, Spring, 2018
//
// Author:          	Logan Kottler, Neeshan Khanikar, Kevin Kemp, Abby Kisicki, Aanjanaye Kajaria
// Email:           	lkottler@wisc.edu, khanikar@wisc.edu, kkemp3@wisc.edu, kisicki@wisc.edu, kajaria@wisc.edu
// Lecturer's Name: 	Deb Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
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
