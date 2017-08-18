package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import java.util.Random;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Robot.Turn;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.CardinalDirection;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Distance;

/**
 * This is a refined wall follower that is able to run around and leave an
 * obstacle. It picks a random direction as its main direction and applies wall following when it
 * hits an obstacle. It counts left (-1) and right (+1) turns and when the total becomes zero, it is
 * able to leave an obstacle following its main direction again. Understanding the wall follower algorithm is a
 * prerequisite.
 * @author TJ
 *
 */
public class Pledge implements RobotDriver{
	
	private Distance distance ;	
	private int width ;
	private int height ;
	private int pathLength ;
    
	private Robot robot ;
	private MazeController mazeController ;
	
	public Pledge() {
		setPathLength(0) ;		
	}

	/**
	 * Assigns a robot platform to the driver. 
	 * The driver uses a robot to perform, this method provides it with this necessary information.
	 * @param r robot to operate
	 */
	@Override
	public void setRobot(Robot r) {
		this.robot = r;
	}

	/**
	 * Provides the robot driver with information on the dimensions of the 2D maze
	 * measured in the number of cells in each direction.
	 * @param width of the maze
	 * @param height of the maze
	 * @precondition 0 <= width, 0 <= height of the maze.
	 */
	@Override
	public void setDimensions(int width, int height) {
		
		assert (width >= 0);
		assert (height >= 0);
		
		this.setWidth(width);
		this.setHeight(height);
	}

	/**
	 * Provides the robot driver with information on the distance to the exit.
	 * Only some drivers such as the wizard rely on this information to find the exit.
	 * @param distance gives the length of path from current position to the exit.
	 * @precondition null != distance, a full functional distance object for the current maze.
	 */
	@Override
	public void setDistance(Distance distance) {
		
		assert (null != distance) ;
		this.distance = distance;
	}

	/**
	 * Drives the robot towards the exit given it exists and given the robot's energy supply lasts long enough. 
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		if (getRobot().hasStopped()) {
			return false ;
		}
    	
    	while (!getRobot().isAtExit()) {
    		if (getRobot().distanceToObstacle(Robot.Direction.FORWARD) != 0) {
    			this.setPathLength(this.getPathLength() + 1) ;
    			getRobot().move(1, false);
    		}
    		else {				
    			int counter = 0 ;
    			if (counter == 0 && getRobot().distanceToObstacle(Robot.Direction.LEFT) != 0 ) {
    				getRobot().rotate(Robot.Turn.LEFT);
    				counter --;
    				this.setPathLength(this.getPathLength() + 1) ;
    				getRobot().move(1,  false);
    				
    			}
    			if (counter == 0 && getRobot().distanceToObstacle(Robot.Direction.LEFT) == 0 ) {
    				getRobot().rotate(Robot.Turn.RIGHT);
    				counter ++;
    				this.setPathLength(this.getPathLength() + 1) ;
    				getRobot().move(1, false);
    				
    			}
    	    	while (counter != 0 && !getRobot().isAtExit() /*&& robot.distanceToObstacle(Robot.Direction.FORWARD) != 0*/) {
    	    		if (getRobot().distanceToObstacle(Robot.Direction.LEFT) != 0 ){
						getRobot().rotate(Robot.Turn.LEFT);
						counter -- ;
	    			}
	    			if (getRobot().distanceToObstacle(Robot.Direction.LEFT) == 0 
	    					&& getRobot().distanceToObstacle(Robot.Direction.FORWARD) == 0 ) {
	    				getRobot().rotate(Robot.Turn.RIGHT);
	    				counter ++ ; 
	    			}
    				this.setPathLength(this.getPathLength() + 1) ;
	    			getRobot().move(1, false);
    	    	}
    	    }
    	}
	    assert(getRobot().isAtExit());
	    if (getRobot().isAtExit()) {
		    if (getRobot().distanceToObstacle(Robot.Direction.RIGHT) == Integer.MAX_VALUE) {
				getRobot().rotate(Robot.Turn.RIGHT);
				this.setPathLength(this.getPathLength() + 1);
				getRobot().move(1, false);
				return true;
			}
			if (getRobot().distanceToObstacle(Robot.Direction.LEFT) == Integer.MAX_VALUE) {
				getRobot().rotate(Robot.Turn.LEFT);
				this.setPathLength(this.getPathLength() + 1);
				getRobot().move(1, false);
				return true;
			}
			if (getRobot().distanceToObstacle(Robot.Direction.FORWARD) == Integer.MAX_VALUE) {
				this.setPathLength(this.getPathLength() + 1) ; 
				getRobot().move(1,  false);
				return true;
			}
	    }
		return true; 
	}

	/**
	 * Returns the total energy consumption of the journey, i.e.,
	 * the difference between the robot's initial energy level at
	 * the starting position and its energy level at the exit position. 
	 * This is used as a measure of efficiency for a robot driver.
	 */
	@Override
	public float getEnergyConsumption() {
		return BasicRobot.INITIAL_BATTERY_LEVEL - getRobot().getBatteryLevel();
	
	}
	
	/**
	 * Returns the total length of the journey in number of cells traversed. 
	 * Being at the initial position counts as 0. 
	 * This is used as a measure of efficiency for a robot driver.
	 */
	@Override
	public int getPathLength() {
		return this.pathLength ;
	}
	
	/**
	 * Sets the given maze controller to this driver.
	 * @param controller
	 */
	public void setMazeController(MazeController controller) {
		this.mazeController = controller ; 
	}
	
	/**
	 * Gets a random CardinalDirection
	 * @return
	 */
	private CardinalDirection randomDirection() {
		Random random = new Random();
		CardinalDirection randomDir;
		int index = random.nextInt(4);
		randomDir = CardinalDirection.values()[index];
		return randomDir;
	}

	/**
	 * Gets the mazeController
	 * @return
	 */
	private MazeController getMazeController() {
		return mazeController;
	}

	/**
	 * gets the robot of the driver
	 * @return
	 */
	private Robot getRobot() {
		return robot;
	}

	/**
	 * set the pathlength
	 * @param pathLength
	 */
	private void setPathLength(int pathLength) {
		this.pathLength = pathLength;
	}

	/**
	 * @return the height
	 */
	private int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	private void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	private int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	private void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the distance
	 */
	private Distance getDistance() {
		return distance;
	}

}