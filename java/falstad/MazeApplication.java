/**
 * 
 */
/*package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import generation.Order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;


/**
 * This class is a wrapper class to startup the Maze game as a Java application
 * 
 *
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 * 
 * TODO: use logger for output instead of Sys.out
 */
/*public class MazeApplication extends JFrame {

	// not used, just to make the compiler, static code checker happy
	private static final long serialVersionUID = 1L;

	private KeyListener kl ;

	private MazeController controller ;
	
	private final static Logger logger = Logger.getLogger(MazeApplication.class.getName());
	
	private Robot robot ;
	private ManualDriver manualDriver ;
	private RobotDriver autoDriver ;

	private JComboBox<String> builderBox;
	private JComboBox<String> driverBox;
	private JComboBox<String> skillBox;
	private JButton startButton;
	
	private MazePanel panel;
	
	String driverInput;
	String builder;
	int level;
	/**
	 * Constructor
	 */
	/*public MazeApplication() {
		super() ;
		
		System.out.println("MazeApplication: maze will be generated with a randomized algorithm.");		
        logger.info("MazeApplication: maze will be generated with a randomized algorithm.");
        
		controller = new MazeController() ;
		setRobot(controller.getRobot()) ;
		manualDriver = controller.getManualDriver() ;
		autoDriver = controller.getDriver();
		panel = new MazePanel(); 
		init() ;  
	}

	/**
	 * Constructor that loads a maze from a given file or uses a particular method to generate a maze
	 */
	/*public MazeApplication(String parameter) {
		super() ;
		// scan parameters
		// Case 1: Prim
		if ("Prim".equalsIgnoreCase(parameter))
		{
			System.out.println("MazeApplication: generating random maze with Prim's algorithm");
			logger.info("MazeApplication: generating random maze with Prim's algorithm");
			
			controller = new MazeController(Order.Builder.Prim) ;
			setRobot(controller.getRobot()) ;
			manualDriver = controller.getManualDriver() ;
			autoDriver = controller.getDriver();
			panel = new MazePanel(); 
			init() ;
			return ;
		}
		// Case 2: Eller
		// TODO: for P2 assignment, please add code for Eller's algorithm here
		if ("Eller".equalsIgnoreCase(parameter))
		{
			System.out.println("MazeApplication: generating random maze with Eller's algorithm");			
			logger.info("MazeApplication: generating random maze with Eller's algorithm");
			
			controller = new MazeController(Order.Builder.Eller) ;
			setRobot(controller.getRobot()) ;
			manualDriver = controller.getManualDriver() ;
			autoDriver = controller.getDriver();
			panel = new MazePanel(); 
			init() ;
			return ;
		}
		
		// Case 3: a file
		File f = new File(parameter) ;
		if (f.exists() && f.canRead())
		{
			System.out.println("MazeApplication: loading maze from file: " + parameter);
			logger.info("MazeApplication: loading maze from file: " + parameter);
			
			controller = new MazeController(parameter) ;
			setRobot(controller.getRobot()) ;
			manualDriver = controller.getManualDriver() ;
			autoDriver = controller.getDriver();
			panel = new MazePanel(); 
			init();
			return ;
		}
		// Default case: 
		System.out.println("MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.");
		logger.info("MazeApplication: unknown parameter value: " + parameter + " ignored, operating in default mode.");
		
		controller = new MazeController() ;
		setRobot(controller.getRobot()) ;
		manualDriver = controller.getManualDriver() ;
		autoDriver = controller.getDriver();
		panel = new MazePanel(); 
		init() ;
	}

	/**
	 * Initializes some internals and puts the game on display.
	 */
/*	private void init() {
			
		//add(controller.getPanel()) ;
		add(panel) ;
		
		kl = new SimpleKeyListener(this, controller) ;
		//kl = new SimpleKeyListener(this, controller, manualDriver) ;
		//kl = new SimpleKeyListener(this, controller, manualDriver, autoDriver) ;
		addKeyListener(kl) ;
		
		SimpleActionListener al = new SimpleActionListener(this, controller, this);

		setSize(570, 500) ;
		setVisible(true) ;
		
		String[] driverStrings = {"Wizard", "Wall Follower", "Pledge", "Explorer", "Manual"};
		String[] builderStrings = {"DFS", "Prim", "Eller"};
		String[] levelStrings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
		
		startButton = new JButton("Start");
		driverBox = new JComboBox<String>(driverStrings);
		builderBox = new JComboBox<String>(builderStrings);
		skillBox = new JComboBox<String>(levelStrings);
		
		//controller.getPanel().setPreferredSize(new Dimension(400, 400));
		panel.setPreferredSize(new Dimension(400, 400));
		
		//add(controller.getPanel(), BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
		add(driverBox, BorderLayout.WEST);
		add(builderBox, BorderLayout.NORTH);
		add(skillBox, BorderLayout.EAST);
		add(startButton, BorderLayout.SOUTH);
		
		startButton.addActionListener(al);
		pack();
		setVisible(true);
		
		// focus should be on the JFrame of the MazeApplication and not on the maze panel
		// such that the SimpleKeyListener kl is used
		setFocusable(true) ;
		
		panel.initBufferImage();
		controller.setPanel(this.panel) ; 
		controller.init();
	}
	
	public MazeController getController() {
		return this.controller ;
	}
	
	public KeyListener getKeyListerner() {
		return this.kl ;
	}
	
	public JComboBox<String> getBuilderBox() {
		return this.builderBox;
	}
	
	public JComboBox<String> getDriverBox() {
		return this.driverBox;
	}
	public JComboBox<String> getSkillBox() {
		return this.skillBox;
	}
	public JButton getStartButton() {
		return this.startButton ; 
	}
	/**
	 * Main method to launch Maze as a java application.
	 * The application can be operated in two ways. The intended normal operation is to provide no parameters
	 * and the maze will be generated by a particular algorithm. If a filename is given, the maze will be loaded
	 * from that file. The latter option is useful for development to check particular mazes.
	 * @param args is optional, first parameter is a filename with a given maze
	 */
/*	public static void main(String[] args) {
		MazeApplication a ; 
		switch (args.length) {
		case 1 : a = new MazeApplication(args[0]);
		break ;
		case 0 : 
		default : a = new MazeApplication() ;
		break ;
		}
		a.repaint() ;
	}

	Robot getRobot() {
		return robot;
	}

	void setRobot(Robot robot) {
		this.robot = robot;
	}

} */
