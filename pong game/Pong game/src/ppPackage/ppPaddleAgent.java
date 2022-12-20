package ppPackage;

import static ppPackage.ppSimParams.*;
import acm.graphics.GPoint;
import java.awt.*;
import acm.program.GraphicsProgram;

/**
 * The ppPaddleAgent class extends ppPaddle by including a provision for adjusting its Y position to match the ball within the run method.
 * It also exports a method that allows the reference to ppBall to be set externally.
 * @author Raphael Verger and Professor Frank Ferry (part of the code structure given)
 */

public class ppPaddleAgent extends ppPaddle {
	
	//Instance variable
	ppBall myBall;
	
	/**
	 * The constructor for the ppPaddleAgent class copies has the same parameters compared to its superclass.
	 * @param X
	 * @param Y
	 * @param myTable
	 * @param GProgram
	 */
	
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
		super(X, Y, myColor, myTable, GProgram);
	}
	
	//Run method to make the agent paddle follow the Y position of the ball, and slows it down so it misses the small sometimes
	
	public void run() {
		double lastX = X;
		double lastY = Y;
		while(true) {
			Vx=(X-lastX)/TICK;
			Vy=(Y-lastY)/TICK;
			lastX=X;
			lastY=Y;
			double Y = myBall.getP().getY();      //get the Y position of the ball
			this.setP(new GPoint(this.getP().getX(),Y));//set the position of the agent paddle
			GProgram.pause(rlag.getValue());  //pause the program to slow down the agent paddle

			
		}
		
	}
	
	/* Alternate version for inside the while loop (that doesn't work as well on my computer since I have to set AgentValue to a very high value to see any difference)
	 * int ballSkip=0;
	 * int AgentLag = rlag.getValue();
	 * if (ballSkip++ >= AgentLag) {
				//get the ball X position
				double Y = myBall.getP().getY();
				//set the paddle position to that Y
				this.setP(new GPoint(this.getP().getX(),Y));
				ballSkip=0;
			}
	 */
	
	/**
	 * Method that allows the reference to ppBall to be set externally
	 * @param myBall
	 */
	
	public void attachBall(ppBall myBall) {
		this.myBall=myBall;
	}

}
 