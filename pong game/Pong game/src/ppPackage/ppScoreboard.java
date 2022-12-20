package ppPackage;

import static ppPackage.ppSimParams.*;


import acm.graphics.GLabel;
import acm.program.GraphicsProgram;

/**
 * This class is responsible of a scoreboard object with methods to set the player's names, increment and clear the scores of the respective players.
 * @author Raphael Verger
 */

public class ppScoreboard {
	
	//Instance variables
	GraphicsProgram GProgram;
	
	
	/**
	 * The constructor for the ppScoreboard class copies parameters to instances variables, creates 
	 * instances of GLabels to displays the agent and player's names and their scores, and adds it to the display.
	 * @param GProgram
	 * @param agentName
	 * @param playerName
	 */
	public ppScoreboard(GraphicsProgram GProgram) {
		
		this.GProgram=GProgram;

		
		//Display agent's name
		GLabel box1 = new GLabel(agentName, GProgram.getWidth()/2-125, GProgram.getHeight()/15);   
		box1.setFont("arial-bold-20");
  		GProgram.add(box1);
  		
  		//Display agent's score
  		GLabel score1 = new GLabel(String.valueOf(APointCounter), GProgram.getWidth()/2-25, GProgram.getHeight()/15);   
		score1.setFont("arial-bold-20");
  		GProgram.add(score1);
  		
  		//Display player's name
  		GLabel box2 = new GLabel(playerName, GProgram.getWidth()/2+25, GProgram.getHeight()/15);   
		box2.setFont("arial-bold-20");
  		GProgram.add(box2);
  		
  		//Display player's score
  		GLabel score2 = new GLabel(String.valueOf(PPointCounter), GProgram.getWidth()/2+135, GProgram.getHeight()/15);   
		score2.setFont("arial-bold-20");
  		GProgram.add(score2);
  		
		
		
	}
	
	/**
	 * Simple method to set the name of the agent
	 * @param name1
	 */
	
	public void setAgentName(String name1) {
		agentName=name1;
		
	/**
	 * Simple method to set the name of the player
	 */
		
	}
	public void setPlayerName(String name2) {
		playerName=name2;
		
	}
	
	/**
	 * Simple method to set increment the agent's score by one
	 */
	
	public static void pointForAgent() {
		APointCounter++;
	}
	
	/**
	 * Simple method to set increment the player's score by one
	 */
	
	public static void pointForPlayer() {
		PPointCounter++;
	}
	
	/**
	 * Simple method to clear the scores of both players
	 */
	
	public void clear() {
		APointCounter=0;
		PPointCounter=0;
	}
		

}
