package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import java.util.ArrayList;
import java.util.Random;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Robot.Direction;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.CardinalDirection;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Cells;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Distance;

/**
 * Explorer: the explorer is inspired by Robert Frost�s poem and
 * takes the road less traveled. It is a simple random search algorithm that retains a 
 * memory where it has been before, i.e., it counts how often it has been on a position (x,y).
 * It operates by 3 rules:
 * 4.1.If outside of a room, it 1) checks its options by asking its available distance sensors, 2)
 * picks a direction to the adjacent cell least traveled (ties are resolved by throwing the dice,
 * i.e., randomization) 3) it rotates if necessary and moves a step into the chosen direction.
 * 4.2.If the explorer gets into a room, it scans it to find all possible doors, picks the door least
 * used (ties are resolved by randomization) and then runs for the door to leave the room.
 * 4.3.Of course, if it is at the exit or it can see the exit, it goes for it.
 * @author TJ
 *
 */
public class Explorer implements RobotDriver{
	
	private Distance distance ;	
	private int width ;
	private int height ;
	private int pathLength ;
    
	private Robot robot ;
	private MazeController mazeController ;
	
	public Explorer() {
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
		ArrayList< Integer> visitedX = new ArrayList <Integer> () ;
		ArrayList < Integer > visitedY = new ArrayList <Integer> () ;
		while (!robot.isAtExit()) {
			if (robot.hasStopped()) {
				return false ;
			}
			ArrayList <Robot.Direction> possibleDir = new ArrayList <Robot.Direction>() ;
			for (Robot.Direction dir : Robot.Direction.values() ) {
				if (robot.distanceToObstacle(dir) != 0) {
					possibleDir.add(dir); 
				}
			}
			Random random = new Random() ;
			int index = random.nextInt(possibleDir.size());
			Robot.Direction chosenDir = possibleDir.get(index) ;
			switch(chosenDir) {
			case FORWARD: 
				this.pathLength ++ ;
				robot.move(1, false);
				visitedX.add(robot.getCurrentPosition()[0]);
				visitedY.add(robot.getCurrentPosition()[1]);
				break;
			case BACKWARD:
				robot.rotate(Robot.Turn.AROUND);
				this.pathLength ++ ;
				robot.move(1, false);
				visitedX.add(robot.getCurrentPosition()[0]);
				visitedY.add(robot.getCurrentPosition()[1]);
				break;
			case RIGHT:
				robot.rotate(Robot.Turn.RIGHT);
				this.pathLength ++ ;
				robot.move(1, false);
				visitedX.add(robot.getCurrentPosition()[0]);
				visitedY.add(robot.getCurrentPosition()[1]);
				break;
			case LEFT:
				robot.rotate(Robot.Turn.LEFT);
				this.pathLength ++ ;
				robot.move(1, false);
				visitedX.add(robot.getCurrentPosition()[0]);
				visitedY.add(robot.getCurrentPosition()[1]);
				break;
			}
		}
		
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
		System.out.println(getPathLength());
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
	 * Converts the given Robot's relative direction into universal cardinal direction (East, West, South, North)
	 * South and North are flipped, consistent with the rest of the maze. 
	 * @param direction
	 * @return cardinal direction converted from the Robot's relative direction
	 */
	private CardinalDirection convertToCardinalDirection(Direction direction) {
		CardinalDirection cardinalDirection = null ;
		
	    switch(direction) {
	    case LEFT: {
	    	cardinalDirection = mazeController.getCurrentDirection().rotateClockwise();	
		    break;

	    }
	    case RIGHT: {
	    	cardinalDirection = mazeController.getCurrentDirection().rotateCounterClockwise();
	    	break;
	    }
	    case FORWARD: {
	    	cardinalDirection = mazeController.getCurrentDirection();
	    	break;
	    	
	    }
	    case BACKWARD: {
	    	cardinalDirection = mazeController.getCurrentDirection().oppositeDirection() ;
	    	break;
	    }
	    
	    default: System.out.println("Inconsistent Direction Type");
	    }
	    
	    assert (cardinalDirection != null) ;
	    return cardinalDirection ;
	}

	/**
	 * @return the distance
	 */
	private Distance getDistance() {
		return distance;
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
}