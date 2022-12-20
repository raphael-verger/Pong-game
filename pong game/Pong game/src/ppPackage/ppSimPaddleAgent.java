package ppPackage;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * This class is the main entry point for the program, which simulates a ping-pong ball moving in a 2D plane, between an agent paddle and a paddle whose Y position is controlled by the player.
 * It creates an instances of the ppTable,ppPaddleAgent, ppPaddle, ppScoreboard and ppBall classes.
 * 
 * @author Raphael Verger and Professor Frank Ferry (part of the code structure given)
 */

public class ppSimPaddleAgent extends GraphicsProgram {

	//Instance variables
	ppTable myTable;
	ppPaddleAgent LPaddle;
	ppPaddle RPaddle;
	ppBall myBall;
	RandomGenerator rgen;
	ppScoreboard myScoreboard;

	public static void main(String[] args) {
		new ppSimPaddleAgent().start(args);
	}


	public void init() {

		// initialize window size
		this.resize(ppSimParams.WIDTH,ppSimParams.HEIGHT+OFFSET);
		this.setTitle("Pong game");

		//Create the buttons
		JButton clearButton = new JButton("Clear");  
		JButton newServeButton = new JButton("New Serve");
		JButton quitButton = new JButton("Quit");
		traceButton = new JToggleButton("Trace", false);
		rlag = new JSlider(0, 200, 30);
		JButton rTimeButton = new JButton("rtime");
		rtime = new JSlider(1000, 3000, 2000);
		JButton rLagButton = new JButton("rlag");



		//Add buttons
		add(clearButton, SOUTH);
		add(newServeButton, SOUTH);
		add(quitButton, SOUTH);
		add(traceButton, SOUTH);
		add(new JLabel("-t"), SOUTH);
		add(rtime, SOUTH);
		add(new JLabel("+t"), SOUTH);
		add(rTimeButton, SOUTH);
		add(new JLabel("-lag"), SOUTH);
		add(rlag, SOUTH);
		add(new JLabel("+lag"), SOUTH);
		add(rLagButton, SOUTH);

        // Mouse operates paddle
		addMouseListeners();
		
		//Player can interact using buttons
		addActionListeners();

		//Generate ppTable instance
		myTable = new ppTable(this); 

		//Generate random number generator
		rgen = RandomGenerator.getInstance(); 
		rgen.setSeed(RSEED);

		//Start new game
        newGame();
        

	}

	/**
	 *  Mouse Handler - a moved event moves the paddle up and down in Y
	 */

	public void mouseMoved(MouseEvent e) {
		
		
		if (myTable==null || RPaddle==null) return;
		GPoint Pm = myTable.S2W(new GPoint(e.getX(),e.getY())); 
		double PaddleX = RPaddle.getP().getX();
		double PaddleY = Pm.getY();
		RPaddle.setP(new GPoint(PaddleX,PaddleY));

	}
	
	/**
	 *  Action Handler 
	 */
	
	public void actionPerformed(ActionEvent e) { 
		String command = e.getActionCommand(); 
		if (command.equals("New Serve")) {
			newGame();                               //start a new game
		}

		else if (command.equals("Quit")) { 
			System.exit(0);                          //terminate the program
		}
		
		else if (command.equals("rtime")) { 
			rtime.setValue(2000);                    //set the value of rtime to 2000
		}
		
		else if (command.equals("rlag")) { 
			rlag.setValue(30);                       //set the value of rlag to 30
		} 
		
		else if (command.equals("Clear")) { 
			myScoreboard.clear();                    //clear the scores
			newGame();                               //start new game
		}
	}
	

    /**
     * Create an instance of ppBall with some parameters generated randomly
     */
	
	ppBall newBall() {

		//Generate parameters for ppBall
		Color iColor = Color.RED;                               // Color of the ball: red
		double iYinit = rgen.nextDouble(YinitMIN,YinitMAX);     // Current Yinit
		double iLoss = rgen.nextDouble(EMIN,EMAX);              // Current loss parameter
		double iVel = rgen.nextDouble(VoMIN,VoMAX);             // Current velocity
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);     // Current launch angle

		return myBall = new ppBall(Xinit,iYinit,iVel,iTheta,iLoss,iColor,this,myTable);  //return a new instance of ppBall with the new parameters

	}
	
	/**
	 * Method to start a new game: creates a new screen, new RPaddle, LPaddle and myBall at initial positions and updates myScoreboard.
	 */
	
	public void newGame() {
		if (myBall != null) myBall.kill();  //stop current game in play
		myTable.newScreen();      //create new screen
		myBall = newBall();       //create new ball
		RPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit, Color.GREEN, myTable,this);  //create new ppPaddle
		LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.BLUE, myTable,this); //create new ppPaddleAgent
		myScoreboard = new ppScoreboard(this);  //create new ppScoreboard
		LPaddle.attachBall(myBall); 
		myBall.setRightPaddle(RPaddle); 
		myBall.setLeftPaddle(LPaddle); 
		pause(STARTDELAY); 
		myBall.start();
		LPaddle.start();
		RPaddle.start();
		}


}