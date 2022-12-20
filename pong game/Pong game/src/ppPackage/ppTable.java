package ppPackage;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * This class is responsible for setting up the ping-pong table (ground planes) and provides utility methods for converting world coordinates to screen coordinates and vice-versa, and setting a new screen.
 * 
 * @author Raphael Verger and Professor Frank Ferry (part of the code structure given)
 */


public class ppTable {
	
	//instance variable
	GraphicsProgram GProgram;
	
	
	public ppTable(GraphicsProgram GProgram) {
		
		this.GProgram=GProgram;
		
		// Create ground plane
		drawGroundLine();			
		
	}
	
	/**
     * Method to convert from world to screen coordinates.
     * @param p a point object in world coordinates
     * @return P the corresponding point object in screen coordinates
     */
    
    static GPoint W2S (GPoint p) {
    	return new GPoint((p.getX()-Xmin)*Xs,ymax-(p.getY()-Ymin)*Ys);
    }	
    
    /**
     * Method to convert from screen to world coordinates.
     * @param P a point object in world coordinates
     * @return p the corresponding point object in screen coordinates
     */
    
    GPoint S2W (GPoint P) {
    	return new GPoint(P.getX()/Xs+Xmin,(ymax-P.getY())/Ys+Ymin);
    }    
    
    /**
     * This simple method draws a new ground plane and erases all objects on the display (except buttons)
     * given by Katrina
     */
    
    public void newScreen() {
    	
    	GProgram.removeAll();   //Remove all objects (except the buttons)
    	drawGroundLine();       //Create new ground line
    	
    	
    }
    
    /**
     * This method draws a ground line
     * given by Katrina POULIN
     */
    
    public void drawGroundLine() {
    	
    	GRect gPlane = new GRect(0,HEIGHT,WIDTH+OFFSET,3);  // A thick line HEIGHT pixels down from 
		gPlane.setColor(Color.BLACK);                        // Color of the ground line : black
		gPlane.setFilled(true);
		GProgram.add(gPlane);                                
    	
    }
    


}