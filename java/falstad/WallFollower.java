package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.CardinalDirection;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Cells;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Distance;

/**
 * The wall follower is a classic solution technique. The robot needs a distance
 * sensor at the front and at one side (here: pick left) to perform. It follows the wall on its left
 * hand side.
 * @author TJ
 *
 */
public class WallFollower implements RobotDriver{
	
	private Distance distance ;	
	private int width ;
	private int height ;
	private int pathLength ;
    
	private Robot robot ;
	private MazeController mazeController ;
	
	public WallFollower() {
		pathLength = 0 ;
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
		
		this.width = width;
		this.height = height;
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
	 * Called twice; once to get to the exit position, then to go through the exit of the maze.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws exception if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception {	
		if (robot.hasStopped()) {
			return false ;
		}
	   // while (!robot.isAtExit()) {
			if (robot.hasStopped()) {
				return false ;
			}
			if (robot.distanceToObstacle(Robot.Direction.LEFT) != 0 ){
				robot.rotate(Robot.Turn.LEFT);
			}
			if (robot.distanceToObstacle(Robot.Direction.LEFT) == 0 
					&& robot.distanceToObstacle(Robot.Direction.FORWARD) == 0 ) {
				robot.rotate(Robot.Turn.RIGHT);
			}
			this.pathLength ++ ;
			robot.move(1,  false);
	    //}
	    assert(robot.isAtExit());
	    if (robot.isAtExit()) {
		    if (robot.distanceToObstacle(Robot.Direction.RIGHT) == Integer.MAX_VALUE) {
				robot.rotate(Robot.Turn.RIGHT);
				this.pathLength ++;
				robot.move(1, false);
				return true;
			}
			if (robot.distanceToObstacle(Robot.Direction.LEFT) == Integer.MAX_VALUE) {
				robot.rotate(Robot.Turn.LEFT);
				this.pathLength ++;
				robot.move(1, false);
				return true;
			}
			if (robot.distanceToObstacle(Robot.Direction.FORWARD) == Integer.MAX_VALUE) {
				this.pathLength ++ ; 
				robot.move(1,  false);
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
		return BasicRobot.INITIAL_BATTERY_LEVEL - robot.getBatteryLevel();
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
	 * @return the distance
	 */
	private Distance getDistance() {
		return distance;
	}

	/**
	 * @return the robot
	 */
	private Robot getRobot() {
		return robot;
	}

	/**
	 * @return the mazeController
	 */
	private MazeController getMazeController() {
		return mazeController;
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
}