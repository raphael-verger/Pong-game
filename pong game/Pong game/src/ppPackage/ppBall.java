package ppPackage;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import acm.graphics.*;
import acm.program.GraphicsProgram;

/**
 * The ppBall class simulates an instance of a ping pong ball moving through space 
 * with potential collisions with the ground, as well as one controlled by the user(right paddle) and another agent paddle (left paddle).
 * @author Raphael Verger and Professor Frank Ferry (part of the code structure given)
 */


public class ppBall extends Thread {
	
	
	// Instance variables 
	public double Xinit;                        //initial position of the ball X
	public double Yinit;                        // initial position of the ball Y
	public double Vo;                           // Initial velocity (Magnitude)
	public double theta;                        //Initial direction
	public double loss;                         // Energy loss ion collision
	public Color color;                         // Color of ball
	public GraphicsProgram GProgram;            // Instance of ppSim class (this)
	public double ScrX, ScrY;                   // X and Y screen coordinates of the ball
	public GOval myBall;                               // Graphics object representing ball
	public ppTable myTable;                            //Instance of ppTable
	public ppPaddle RPaddle;                           //Instance of ppPaddle
	public ppPaddle LPaddle;                            //Instance of ppPaddle
	public double X, Xo, Y, Yo;
	public double Vx, Vy;
	public boolean running;
	
	/**
	 * The constructor for the ppBall class copies parameters to instances variables, creates an
	 * instance of a GOval to represent the ping-pong ball, and adds it to the display.
	 * 
	 * @param Xinit - starting position of the ball X (meters)
	 * @param Yinit -  starting position of the ball Y (meters)
	 * @param Vo - initial velocity (meters/second)
	 * @param loss- initial angle to the horizontal (degrees)
	 * @param color - ball color (Color)
	 * @param GProgram - a reference to the ppSim class used to manage the display
	 * @param myTable - a reference to the ppTable class used to convert world coordinates to screen coordinates and vice-versa.
	 */
	
	public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color, GraphicsProgram GProgram, ppTable myTable) {
	
		this.Xinit=Xinit;
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.color=color;
		this.GProgram=GProgram;
		this.myTable=myTable;
		
		
		// Create the ball
		GPoint p = myTable.W2S(new GPoint(Xinit,Yinit+bSize));		
    	ScrX = p.getX();						                // Convert simulation to screen coordinates
    	ScrY = p.getY();			
    	myBall = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys);
    	myBall.setColor(color);                                 // Set the color to color
    	myBall.setFilled(true);
    	GProgram.add(myBall);
    	GProgram.pause(1000);								    // So we can see the starting point of the ball
	}
	
	/*
	 * Launch the ball and simulate its trajectory until it gets out of the ceilling or the player or the agent miss the ball.
	 */
	
	public void run() {
		
  		//Initialize simulation parameters
  		double time = 0;                                // time (reset at each interval)
  		double Vt = bMass*g / (4*Pi*bSize*bSize*k);     // Terminal velocity
  		double KEx=ETHR,KEy=ETHR;                       // Kinetic energy in X and Y directions
  		double PE=ETHR;                                 // Potential energy
  		
  		double Vox=Vo*Math.cos(theta*Pi/180);           // Initial velocity components in X and Y
  		double Voy=Vo*Math.sin(theta*Pi/180);
  		
  		Xo=Xinit;                                       // Initial X offset (ball touching wall)
  	    Yo=Yinit;                                       // Initial Y offset
  	    
  		
  		/*     
  		 * Main Simulation Loop    
  		 */
  	    
  	    boolean running = true;
  	    
  	    while (running) {
  	    	
  	    	X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));                // Update relative position
  	    	Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
  	    	Vx = Vox*Math.exp(-g*time/Vt);                        // and velocity.
  	    	Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;
  	    	
  	    	
  	    	if(Y+Yo>Ymax) {                // Check if the ball goes above the ceilling 
  	    		
  	    		running = false;           //Exit the loop
  	    	    
  	    		if (Vox<0) {
  	    			ppScoreboard.pointForAgent();       // Give a point to the agent if Vox < 0
  	    	    	System.out.println("point for the agent");
  	    	    }
  	    	    else {
  	    	    	ppScoreboard.pointForPlayer();     // Give a point to the player if Vox > 0
  	    	    	System.out.println("point for the player");

  	    	    }
  	    	}
  	    		
  	    	
  	    	// Collision with ground. 
  		    //   Vy <= 0
  		    //   Yo <= bSize
  		    
  		    if (Vy<0 && (Yo+Y)<=bSize) {
  		    	
  		    	KEx = 0.5*bMass*Vx*Vx*(1-loss);// Kinetic energy in X direction after collision
  		    	KEy = 0.5*bMass*Vy*Vy*(1-loss);// Kinetic energy in Y direction after collision
  		    	PE = 0;// Potential energy (at ground)
  		    	Vox = Math.sqrt(2*KEx/bMass);// Resulting horizontal velocity
  		    	Voy = Math.sqrt(2*KEy/bMass);// Resulting vertical velocity
  		    	if (Vx<0) Vox=-Vox;// Preserve sign of Vox
  		    	
  		    	time=0;// Reset current interval time
  		    	Xo+=X;// Update X and Y offsets
  		    	Yo=bSize;
  		    	X=0;// Reset X and Y for next iteration
  		    	Y=0;// The simulation can only end when the ball is on the ground since PE ~ 0.
  		    	
  		    	if ((KEx+KEy+PE)<ETHR) running=false;// Terminate if insufficient energy
  		    	
  		    }
  		    	
  		    	
  		    	// Collision with the paddle controlled by the user.  Ball assumed to have non-zero PE, so no check on energy.
  		    	//   Vx > 0
  		    	//   X >= XMAX
  		    	
  		    if (Vx>0 && (Xo+X) >= (RPaddle.getP().getX()-bSize-ppPaddleW/2)) {
  		    	
  		    	//Update parameters if the ball touches the paddle
  		    	if (RPaddle.contact(X+Xo,Y+Yo)) {
  		    		
  		    		KEx = 0.5*bMass*Vx*Vx*(1-loss);// Kinetic energy in X direction after collision
  			    	KEy = 0.5*bMass*Vy*Vy*(1-loss);// Kinetic energy in Y direction after collision
  			    	PE = bMass*g*(Yo+Y);// Potential energy
  			    	Vox = -Math.sqrt(2*KEx/bMass);// Resulting horizontal velocity
  			    	Voy = Math.sqrt(2*KEy/bMass);// Resulting vertical velocity
  			    	
  			    	Vox = Vox*ppPaddleXgain; // Scale X component of velocity
  			    	if (Vox>VoMAX) Vox=VoMAX;//Limit the X velocity 
  			    	Voy = Voy*ppPaddleYgain*RPaddle.getSgnVy(); // Scale Y + same dir. as paddle
  			    	time=0;// Reset current interval time
  			    	Xo=RPaddle.getP().getX()-bSize-ppPaddleW/2;// Update X and Y offsets
  			    	Yo+=Y;
  			    	X=0;// Reset X to zero for start of next interval
  			    	Y=0;
  		    		
  			    //Otherwise terminate the program
  		    	} else {
  		    		
  		    		
  		    		if (Vox>0) {
  		    			ppScoreboard.pointForAgent();                             // Give a point to the agent if Vox > 0
  	  	    	    	System.out.println("point for the agent");

  	  	    	    }
  	  	    	    else {
  	  	    	        ppScoreboard.pointForPlayer();                            // Give a point to the player if Vox < 0
  	  	    	    	System.out.println("point for the player");

  	  	    	    }
  		    		running=false;  //End the loop
  		    		//increment point
  		    		
  		    	}
  		    	
  		    }
  		    		
  		    		

  		    // Collision with the agent paddle.  Same as above wrt. check for energy.
  		    // Vx < 0
  		    // X <= XinitA

  		    if (Vx<0 && (Xo+X) <= (LPaddle.getP().getX()+bSize+ppPaddleW/2)) {

  		    	//Update parameters if the ball touches the paddle
  		    	if (LPaddle.contact(X+Xo,Y+Yo)) {

  		    		KEx = 0.5*bMass*Vx*Vx*(1-loss);// Kinetic energy in X direction after collision
  		    		KEy = 0.5*bMass*Vy*Vy*(1-loss);// Kinetic energy in Y direction after collision
  		    		PE = bMass*g*(Yo+Y);// Potential energy
  		    		Vox = Math.sqrt(2*KEx/bMass);// Resulting horizontal velocity
  		    		Voy = Math.sqrt(2*KEy/bMass);// Resulting vertical velocity

  		    		Vox = Vox*ppPaddleXgain; // Scale X component of velocity
  		    		if (Vox>VoMAX) Vox=VoMAX;//Limit the X velocity
  		    		Voy = Voy*ppPaddleYgain*LPaddle.getSgnVy(); // Scale Y + same dir. as paddle
  		    		time=0;// Reset current interval time
  		    		Xo=LPaddle.getP().getX()+bSize+ppPaddleW/2;// Update X and Y offsets
  		    		Yo+=Y;
  		    		X=0;// Reset X to zero for start of next interval
  		    		Y=0;

  		    		//Otherwise terminate the program
  		    	} else {


  		    		
  		    		if (Vox>0) {
  		    			ppScoreboard.pointForAgent();                           // Give a point to the agent if Vox > 0
  	  	    	    	System.out.println("point for the agent");

  	  	    	    }
  	  	    	    else {
  	  	    	        ppScoreboard.pointForPlayer();                            // Give a point to the player if Vox < 0
  	  	    	    	System.out.println("point for the player");

  	  	    	    }

  		    		running=false;  //End the loop 

  		    	}
  		    }

  		    	// List the motion parameters for current step if enabled.
  		    	
  		    if (TEST) {
  		    	System.out.printf("t: %.2f  X: %.2f  Y: %.2f  Vx: %.2f  Vy: %.2f\n",time,Xo+X,Yo+Y,Vx,Vy);
  		    }
  		    	
  		    // Update ball position on screen
  		    // (note that the current position in simulation coordinates is
  		    // (Xo+X,Yo+Y).
  		    	
  		    GPoint p = ppTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize));// Get current position in screen coordinates
  		   	ScrX = p.getX();
  		   	ScrY = p.getY();
			myBall.setLocation(ScrX,ScrY);// Change position of ball in display
			
			if(traceButton.isSelected()) {   // Place a marker on the current position if the traceButton is pressed
				trace(Xo+X,Yo+Y);          
			}
  		   	
  		    	
  		   
  		    GProgram.pause(TICK*rtime.getValue());// Step display at fixed rate}
  		    time+=TICK;// Update time
   	
  	    }
  	}
  	    
  	    
  	    

    
    /***
     * A simple method to plot a dot at the current location in screen coordinates
     * @param scrX
     * @param scrY
     */
    

    public void trace(double X, double Y) {
		GPoint p = ppTable.W2S(new GPoint(X,Y));
		double ScrX = p.getX();
		double ScrY = p.getY();
		GOval pt = new GOval(ScrX, ScrY,PD,PD);
		pt.setColor(Color.BLACK);
		pt.setFilled(true);
		GProgram.add(pt);
		
	}
    
    /**
     * A simple method to link a paddle instance to the ball
     * @param myPaddle
     */
    
    public void setRightPaddle(ppPaddle myPaddle) {
    	this.RPaddle=myPaddle;
    	
    }
    
    /**
     * Sets the value of the reference to the Agent paddle.
     * @param LPaddle
     */
    
    public void setLeftPaddle (ppPaddle LPaddle) {
    	this.LPaddle=LPaddle;
    	
    }
    
    /**
     * Getter for ball position
     * @return
     */
    
    public GPoint getP() {
    	return new GPoint(Xo+X,Yo+Y);
    	
    }
    
    /**
     * Getter for ball velocity
     * @return
     */
    
    public GPoint getV() {
    	return new GPoint(Vx,Vy);
    	
    }
    
    /**
     * Terminates program
     */
    
    void kill() {
    	running=false;	
    }

}