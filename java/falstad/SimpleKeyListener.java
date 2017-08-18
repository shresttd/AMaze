/*package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import java.awt.Container;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class implements a wrapper for the user input handled by the Maze class. 
 * The MazeApplication attaches the listener to the GUI, such that user keyboard input
 * flows from GUI to the listener.keyPressed to the MazeController.keyDown method.
 *
 * This code is refactored code from Maze.java by Paul Falstad, www.falstad.com, Copyright (C) 1998, all rights reserved
 * Paul Falstad granted permission to modify and use code for teaching purposes.
 * Refactored by Peter Kemper
 */
/*public class SimpleKeyListener implements KeyListener {

	private Container parent ;
	private MazeController controller ;
	
	private ManualDriver manualDriver ;
	private RobotDriver autoDriver ;
	
	SimpleKeyListener(Container parent, MazeController controller){
		this.parent = parent ;
		this.controller = controller ;
	}
	SimpleKeyListener(Container parent, MazeController controller, ManualDriver manualDriver ) {
		this.parent = parent ;
		this.controller = controller ;
		this.manualDriver = manualDriver ;
	}
	SimpleKeyListener(Container parent, MazeController controller, ManualDriver manualDriver, RobotDriver autoDriver ) {
		this.parent = parent ;
		this.controller = controller ;
		this.manualDriver = manualDriver ;
		this.autoDriver = autoDriver ;
	}
	
	/**
	 * Translates keyboard input to the corresponding characters for the Maze.keyDown method.
	 * 
	 */
	/*@Override
	public void keyPressed(KeyEvent arg0) {
		//System.out.println("1: Communicating key: " + arg0.getKeyText(arg0.getKeyCode()) + " with key char: " + arg0.getKeyChar() + " with code: " + arg0.getKeyCode());
		int key = arg0.getKeyChar() ;
		int code = arg0.getKeyCode() ;
		
		System.out.println("key in SKL: " + key) ;

		if (KeyEvent.CHAR_UNDEFINED == key)
		{
			if ((KeyEvent.VK_0 <= code && code <= KeyEvent.VK_9)||(KeyEvent.VK_A <= code && code <= KeyEvent.VK_Z))
				key = code ;
			if (KeyEvent.VK_ESCAPE == code)
				key = Constants.ESCAPE ; // use internal encoding for escape signal
			if (KeyEvent.VK_UP == code)
				key = 'k' ; // reduce duplicate encodings
			if (KeyEvent.VK_DOWN == code)
				key = 'j' ; // reduce duplicate encodings
			if (KeyEvent.VK_LEFT == code)
				key = 'h' ; // reduce duplicate encodings
			if (KeyEvent.VK_RIGHT == code)
				key = 'l' ; // reduce duplicate encodings
		}
		//System.out.println("Calling keydown with " + key) ;
		// possible inputs for key: unicode char value, 0-9, A-Z, Escape, 'k','j','h','l' plus CTRL-W
		
		
		if (controller.getDriver() != null) {
			switch(controller.getState()) {
			case STATE_PLAY : {
				switch (key) {
				case Event.ENTER : 
					try {
					controller.getDriver().drive2Exit() ;	
					if (controller.getRobot().hasStopped()) {
						controller.switchToFinishScreen();
					}
					} catch (Exception e) {
					}
					break ;
				default :
					keyDown(key);
				}
			break;
			}
			default: keyDown(key);
			}

		}
		else {
			switch(controller.getState()) {
			case STATE_TITLE :
				controller.getManualDriver().setPathLength(ManualDriver.INITIAL_PATHLENGTH);
				controller.getRobot().setBatteryLevel(BasicRobot.INITIAL_BATTERY_LEVEL);
				keyDown(key);
				break;
			case STATE_PLAY :
				switch(key) {
				case 107 : case 104 : case 108: case 106 : 
					controller.getManualDriver().keyDown(key) ;
					break;
				default: keyDown(key); 
				}
				break;
			default : keyDown(key) ;
			}		
		}	
		
		parent.repaint() ;
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// nothing to do
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// NOTE FOR THIS TYPE OF EVENT IS getKeyCode always 0, so Escape etc is not recognized	
		// this is why we work with keyPressed
	}	
	
	/**
	 * Method incorporates all reactions to keyboard input in original code, 
	 * The simple key listener calls this method to communicate input.
	 */
	/*public boolean keyDown(int key) {
		//String s1 = "before: px,py:" + px + "," + py + " dir:" + dx + "," + dy;
		// possible inputs for key: unicode char value, 0-9, A-Z, Escape, 'k','j','h','l'
		// depending on the current state of the GUI, inputs have different effects
		// implemented as a little automaton that switches state and performs necessary actions
		switch (controller.getState()) {
		// if screen shows title page, keys describe level of expertise
		// create a maze according to the user's selected level
		// user types wrong key, just use 0 as a possible default value
		case STATE_TITLE_BUILDER:
			if (key == Constants.ESCAPE) {
				controller.switchToTitleScreen(true) ;				//takes to builder screen
			}
			controller.switchToTitleDriverScreen(key);
			break;
		case STATE_TITLE_DRIVER:
			if (key == Constants.ESCAPE) {
				controller.switchToTitleScreen(true) ;				//takes to builder screen
			}
			else {
				controller.switchToTitleScreen(key);
			}
		    break; 
		case STATE_TITLE:
			if (key == Constants.ESCAPE) {
				controller.switchToTitleScreen(true);
			}
			else {
				controller.switchToGeneratingScreen(key);
			}
			break;
			// if we are currently generating a maze, recognize interrupt signal (ESCAPE key)
			// to stop generation of current maze
		case STATE_GENERATING:
			if (key == Constants.ESCAPE) {
				controller.switchToTitleScreen(true);
			}
			break;
			// if user explores maze, 
			// react to input for directions and interrupt signal (ESCAPE key)	
			// react to input for displaying a map of the current path or of the overall maze (on/off toggle switch)
			// react to input to display solution (on/off toggle switch)
			// react to input to increase/reduce map scale
		case STATE_PLAY:
			switch (key) {
			case Event.UP: case 'k': case '8':
			//case 107:
			// move forward
				controller.walk(1);
				if (controller.isOutside(controller.getCurrentPosition()[0],
						controller.getCurrentPosition()[1])) {
					controller.switchToFinishScreen();
				}
				break;
			case Event.LEFT: case 'h': case '4':
				// turn left
				controller.rotate(1);
				break;
			case Event.RIGHT: case 'l': case '6':
				// turn right
				controller.rotate(-1);
				break;
			case Event.DOWN: case 'j': case '2':
				// move backward
				controller.walk(-1);
				if (controller.isOutside(controller.getCurrentPosition()[0],
						controller.getCurrentPosition()[1])) {
					controller.switchToFinishScreen();
				}
				break;
			case Constants.ESCAPE: case 65385:
				// escape to title screen
				controller.switchToTitleScreen(false);
				break;
			case ('w' & 0x1f): 
				// Ctrl-w makes a step forward even through a wall
				// go to position if within maze
				if (controller.getMazeConfiguration().isValidPosition(
						controller.getCurrentPosition()[0] + controller.getCurDir()[0],
								controller.getCurrentPosition()[1] + controller.getCurDir()[1])) {
					controller.setCurrentPosition(
							controller.getCurrentPosition()[0] + controller.getCurDir()[0],
							controller.getCurrentPosition()[1] + controller.getCurDir()[1]) ;
					controller.notifyViewerRedraw() ;
				}
				break;
			case '\t': case 'm':
				controller.toggleMapMode();
				controller.notifyViewerRedraw() ; 
				break;
			case 'z':
				controller.toggleShowMaze();
				controller.notifyViewerRedraw() ; 
				break;
			case 's':
				controller.toggleShowSolution();
				controller.notifyViewerRedraw() ;
				break;
			case '+': case '=':
				// zoom into map
				controller.notifyViewerIncrementMapScale() ;
				controller.notifyViewerRedraw() ; // seems useless but it is necessary to make the screen update
				break ;
			case '-':
				// zoom out of map
				controller.notifyViewerDecrementMapScale() ;
				controller.notifyViewerRedraw() ; // seems useless but it is necessary to make the screen update
				break ;
			} 
			break ;
		case STATE_FINISH:
			controller.switchToTitleScreen(false);
			break;
		} 
		return true;
	}

} */
