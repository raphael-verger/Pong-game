package ppPackage;

import static ppPackage.ppSimParams.*;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.*;

/**
 * The ppPaddle class is responsible for creating a paddle instance and exporting methods for interacting with the paddle instance.
 * @author Raphael Verger and Professor Frank Ferry (part of the code structure given)
 */

public class ppPaddle extends Thread {
	
	//Instance variables
	double X;                  //Position of the paddle X
	double Y;                  //Position of the paddle Y
	double Vx;                 //Velocity of the paddle Vx
	double Vy;                 //Velocity of the paddle Vy
	GRect myPaddle;            //Graphics object representing the paddle
	GraphicsProgram GProgram;  // Instance of ppSim class (this)
	ppTable myTable;           // Instance of ppTable class
	Color myColor;
	
	/**
	 * The constructor for the ppPaddle class copies parameters to instances variables, creates an
	 * instance of a GRect to represent the paddle, and adds it to the display.
	 * 
	 * @author mostly Katrina POULIN
	 * @param X
	 * @param Y
	 * @param myTable
	 * @param GProgram
	 */
	
	public ppPaddle (double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		
		this.X=X;
		this.Y=Y;
		this.myTable=myTable;
		this.GProgram=GProgram;
		this.myColor=myColor;
		
		double upperLeftX = X-ppPaddleW/2; //World coordinates of the upper left corner of the paddle
		double upperLeftY=Y+ppPaddleH/2;
		
		GPoint g= myTable.W2S(new GPoint(upperLeftX,upperLeftY)); //To use W2S method
		
		double ScrX = g.getX(); //Screen coordinates of the upper left corner of the paddle
		double ScrY = g.getY();
		
		this.myPaddle = new GRect(ScrX, ScrY, ppPaddleW*Xs, ppPaddleH*Ys); //create an instance of a GRect to represent the paddle
		myPaddle.setFilled(true);
		myPaddle.setColor(myColor); //set the color
		GProgram.add(myPaddle);         //add the paddle
		
	}
	
	/**
	 *Method to continually update paddle velocity in response to user-induced changes in position.
	 */
	
	public void run() { 
		double lastX=X;
		double lastY=Y;
		while (true) {
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y; 
			GProgram.pause(TICK*TSCALE);
		}
	}
	
	/**
	 * @return paddle location (X,Y)
	 */

	
	public GPoint getP() {
		return new GPoint(X,Y);
		
	}
	
	/**
	 * Sets and moves paddle to (X,Y)
	 * @param P
	 */
	
	public void setP(GPoint P) {
		
		//update instance variables
		this.Y=P.getY();
		
		double upperLeftX = X-ppPaddleW/2; //World coordinates of the upper left corner of the paddle
		double upperLeftY=Y+ppPaddleH/2;
		
		GPoint g= myTable.W2S(new GPoint(upperLeftX,upperLeftY)); //To use W2S method
		
		double ScrX = g.getX(); //Screen coordinates of the upper left corner of the paddle
		double ScrY = g.getY();
		
		this.myPaddle.setLocation(ScrX,ScrY);
	}
	
	/**
	 * @return  paddle velocity (Vx,Vy)
	 */
	
	public GPoint getV() {
		return new GPoint(Vx,Vy);
	}
	
	/**
	 * @return the sign of the Y velocity of the paddle
	 */
	
	public double getSgnVy() {
		 return (Vy<0)? -1 : 1;
	}
	
	/**
	 * Method to determine if a surface at position (Sx,Sy) is deemed to be in contact with the paddle.
	 * @param Sx
	 * @param Sy
	 * @return true if the surface touches the paddle, else false
	 */
	
	public boolean contact(double Sx, double Sy) {
		
		return (Sy>=(Y-ppPaddleH/2) && Sy<=(Y+ppPaddleH/2));	
		
	}

}
