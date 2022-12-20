package ppPackage;

import javax.swing.JSlider;
import javax.swing.JToggleButton;

/**
 * This class defines the constants used in the other classes. To use those constants outside of ppSimParams, we have to import them.
 * 
 * @author Raphael Verger and Professor Frank Ferry (part of the code structure given)
 */


public class ppSimParams {
	
	    //0. Buttons and sliders
	    
	    public static JToggleButton traceButton;                     //Enable tracing of the ball is pressed
	    public static JSlider rlag;                                  //Modify rapidity of the agent paddle to follow the ball
	    public static JSlider rtime;                                 //Modify rapidity of the ball

	     
		// 1. Parameters defined in screen coordinates (pixels, acm coordinates)
		
		public static final int WIDTH = 1280;							// n.b. screen coordinates
		public static final int HEIGHT = 600;
		public static final int OFFSET = 200;	
		
	    // 2. Parameters defined in world coordinates (meters)
	    
		public static final double ppTableXlen = 2.74;                //Length
		public static final double ppTableHgt = 1.52;                 //Ceiling
		public static final double XwallL = 0.05;                     // Position of left wall
		public static final double XwallR = 2.69;                     // Position of right wall

		// 3. Parameters defined simulation coordinates 
		
	    public static final double g = 9.8;	                           // gravitational constant
	    public static final double k = 0.1316;                         // air friction
		public static final double Pi = 3.1416;                        // Pi to 4 places
		public static final double bSize = 0.02;                       // Radius of ball (m)
		public static final double bMass = 0.0027;                     // Mass of ball (kg)
		public static final double Xinit = XwallL;	                   // Initial X position of ball
		public static final double TICK=0.01;                  // Clock increment at each iteration.
		public static final double ETHR = 0.001;                       // Threshold for minimum ball's energy
		public static final double Xmin = 0.0;					  	   // Minimum value of X (pp table)
		public static final double Xmax = ppTableXlen;				   // Maximum value of X
	    public static final double Ymin = 0.0;						   // Minimum value of Y
	    public static final double Ymax = ppTableHgt;				   // Maximum value of Y (height above table)
	    public static final int xmin = 0;								// Minimum value of x
	    public static final int xmax = WIDTH;							// Maximum value of x
	    public static final int ymin = 0;								// Minimum value of y
	    public static final int ymax = HEIGHT;							// Maximum value of y
	    public static final double Xs = (xmax-xmin)/(Xmax-Xmin);		// Scale factor X
	    public static final double Ys = (ymax-ymin)/(Ymax-Ymin);		// Scale factor Y
		public static final double Yinit = Ymax/2;                      // Initial Y position of ball
	    public static final double PD = 1;								// Trace point diameter
	    public static final double TSCALE = 2000;                       // Scaling parameter for pause
	    
	    // 4. Paddle parameters
	    
	    static final double ppPaddleH = 8*2.54/100;                     //Paddle height
	    static final double ppPaddleW = 0.5*2.54/100;                   //Paddle width
	    static final double ppPaddleXinit = XwallR-ppPaddleW/2;         // Initial Paddle X
	    static final double ppPaddleYinit = Yinit;                      // Initial Paddle Y
	    static final double ppPaddleXgain = 2.0;                        // Vx gain on paddle hit
	    static final double ppPaddleYgain = 2.0;                        // Vy gain on paddle hit
	    static final double LPaddleXinit = XwallL - ppPaddleW/2;
	    static final double LPaddleYinit = Yinit;
	    static final double LppPaddleXgain = 2.0;     
	    static final double LppPaddleYgain = 2.0;                        



	    
	    // 5. Parameters used by the ppSim class
	    
	    static final double YinitMAX = 0.75*Ymax; // Max inital height at 75% of range 
	    static final double YinitMIN = 0.25*Ymax;  // Min inital height at 25% of range
	    static final double EMIN = 0.2;// Minimum loss coefficient
	    static final double EMAX = 0.2;// Maximum loss coefficient
	    static final double VoMIN = 5.0; // Minimum velocity
	    static final double VoMAX = 3.5; // Maximum velocity
	    static final double ThetaMIN = 0.0; // Minimum launch angle
	    static final double ThetaMAX = 20.0;  // Maximum launch angle
	    static final long RSEED = 8976232; // Random number gen. seed value
	    
	    // 6. Miscellaneous
	    
	    public static final boolean DEBUG = false;   // Debug msg. and single step if true
	    public static final boolean TEST = false;
	    public static final boolean MESG = true;     // Enable status messages on console
	    public static final int STARTDELAY = 1000;  // Delay between setup and start
	    
	    // 7. Parameters used by the ppScoreboard class
	    
	    public static int APointCounter=0;                           //Count point for the agent
	    public static int PPointCounter=0;                           //Count point for the player
	    public static String agentName="Agent";                      //Name of the agent
	    public static String playerName="Human";                     //Name of the player
	    
	    
	    
}	    
	    
	    
	   
	    
	    
	 