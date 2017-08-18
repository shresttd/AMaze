package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.CardinalDirection;

/**
 * Implements the Robot.java interface
 * The robot can move forward and rotate 
 * The Robot has 4 direction sensors and a room sensor 
 * The Robot starts out with 2500 energy units
 * Distance sensing takes 1 energy units
 * Rotating 90 degrees takes 3 energy units  
 * Moving forward takes 5 energy units
 * @author TJ
 */
public class BasicRobot implements Robot {
	
	public static final float INITIAL_BATTERY_LEVEL = 2500 ;
	public static final float DISTANCE_SENSING = 1 ;
	public static final float ROTATE_90 = 3 ;
	public static final float MOVE_FORWARD = 5 ;
	
	private MazeController controller ;
	private float batteryLevel ;
	private boolean hasStopped = false ;
	
	public BasicRobot() {
		this.batteryLevel = INITIAL_BATTERY_LEVEL ;
	}
	
	@Override
	public void rotate(Turn turn) {				
		if ( this.batteryLevel <= 0 ) {
			return ;
		}
		System.out.println("Turn: " + turn);
		switch (turn) {
		case LEFT : {
			controller.rotate(1);
			this.batteryLevel -= ROTATE_90 ;
			setBatteryToZero() ;
			break;
		}
		case RIGHT : { 
			controller.rotate(-1);
			this.batteryLevel -= ROTATE_90 ;
			setBatteryToZero() ;
			break;
		}
		
		case AROUND : { 
			this.batteryLevel -= ROTATE_90 * 2;
			controller.rotate(-1);
			controller.rotate(-1);

			setBatteryToZero() ;
			break;
		}
		default : {		} 
		
		}
	}

	@Override
	public void move(int distance, boolean manual) {
		assert ( distance >= 0 ) ;
		
		if ( this.batteryLevel <= 0 ) {
			return ;
		}
		
		int i ;
		for (i = 0 ; i < distance ; i ++) {
			
			if ( this.batteryLevel <= 0 ) {
				return ;
			}
			if ( this.hasStopped() ) {
				return ;
			}
			
			this.batteryLevel -= MOVE_FORWARD ;
			
			controller.walk(1);
			if (controller.isOutside(controller.getCurrentPosition()[0],controller.getCurrentPosition()[1])) {
				//controller.switchToFinishScreen();
			}			
			setBatteryToZero() ;
		}
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		
		int currentX = controller.getCurrentPosition()[0] ;
		int currentY = controller.getCurrentPosition()[1] ; 
				
		assert ( 0 <= currentX && currentX  < controller.getMazeConfiguration().getWidth() );
		assert ( 0 <= currentY && currentY  < controller.getMazeConfiguration().getHeight() );
		
		return controller.getCurrentPosition() ;
	}

	@Override
	public void setMaze(MazeController maze) {
		this.controller = maze;
	}

	@Override
	public boolean isAtExit() {
		int currentX = controller.getCurrentPosition()[0] ;
		int currentY = controller.getCurrentPosition()[1] ;
		
		if ( controller.getMazeConfiguration().getMazedists().isExitPosition(currentX, currentY) ) {
			return true;
		} 
		return false;
	}

	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		
		if ( this.distanceToObstacle(direction) == Integer.MAX_VALUE ) {
			return true;
		}
		return false;
	}
	
	public MazeController getMazeController () {
		return this.controller ;
	}
	
	
	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		
		int currentX = controller.getCurrentPosition()[0] ;
		int currentY = controller.getCurrentPosition()[1] ;
		
		return controller.getMazeConfiguration().getMazecells().isInRoom(currentX, currentY) ;
	}

	@Override
	public boolean hasRoomSensor() {
		return true;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return controller.getCurrentDirection();
	}
	
	@Override
	public float getBatteryLevel() {
		return this.batteryLevel ;
	}
	
	@Override
	public void setBatteryLevel(float level) { 		
		
		assert (level >= 0) ;
		
		this.batteryLevel = level ;
		setBatteryToZero() ;
	}

	@Override
	public float getEnergyForFullRotation() {
		return ROTATE_90 * 4;
	}

	@Override
	public float getEnergyForStepForward() {
		return MOVE_FORWARD;
	}

	@Override
	public boolean hasStopped() {	
		return hasStopped;
	}
	
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		CardinalDirection cardinalDirection = convertToCardinalDirection(direction) ;
		
		int[] curPos = controller.getCurrentPosition() ;
		int distanceTo = 0;
		
		if (isAtExit() ){
			if ( !controller.getMazeConfiguration().hasWall(curPos[0], curPos[1], cardinalDirection) ) {
					
				switch(cardinalDirection){
				case East : {
					if (controller.isOutside(curPos[0] + 1 , curPos[1])) {
						distanceTo = Integer.MAX_VALUE ;
						this.batteryLevel -= DISTANCE_SENSING ;
						assert (distanceTo >= 0) ;
						return distanceTo ;
					}
					break;
				}
				case West : {
					if (controller.isOutside(curPos[0] - 1 , curPos[1])) {
						distanceTo = Integer.MAX_VALUE ;
						this.batteryLevel -= DISTANCE_SENSING ;
						assert (distanceTo >= 0) ;
						return distanceTo ;
					}
					break;
				}
				case South: {
					if (controller.isOutside(curPos[0] , curPos[1] + 1 )) {
						distanceTo = Integer.MAX_VALUE ;
						this.batteryLevel -= DISTANCE_SENSING ;
						assert (distanceTo >= 0) ;
						return distanceTo ;
					}
					break;
				}
				case North: {
					if (controller.isOutside(curPos[0] , curPos[1] - 1 )) {
						distanceTo = Integer.MAX_VALUE ;
						this.batteryLevel -= DISTANCE_SENSING ;
						assert (distanceTo >= 0) ;
						return distanceTo ;
					}
					break;
				}
				}
			}
		}
		
		if (facingExit(curPos[0], curPos[1], cardinalDirection)) {
			distanceTo = Integer.MAX_VALUE ;
			this.batteryLevel -= DISTANCE_SENSING ;
			setBatteryToZero() ;
			assert (distanceTo >= 0) ;
			return distanceTo ;
		}
		while (	!controller.getMazeConfiguration().hasWall(curPos[0], curPos[1], cardinalDirection ) ) {
			switch (cardinalDirection) {
			case East : {
				curPos[0] ++ ;
				break;
			}
			case West : {
				curPos[0] -- ;
				break;
			}
			case South : {
				curPos[1] ++ ;
				break;
			}
			case North: {
				curPos[1] -- ;
				break;
			}
			}
			
			distanceTo ++ ;  
		}
		this.batteryLevel -= DISTANCE_SENSING ;
		setBatteryToZero() ;
		assert (distanceTo >= 0) ;
		return distanceTo;
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		for ( Robot.Direction dir : Robot.Direction.values() ) {
			if (dir == direction) {
				return true ;
			}
		}
		return false ;
	}
	
	/** 
	 * Sets the battery level to 0 if the battery level is less than 0.
	 * Also sets the hasStopped flag to true if battery level is 0 or less than 0.
	 */
	private void setBatteryToZero() {
		if ( this.batteryLevel <= 0 || this.batteryLevel == 0 ) {
			this.batteryLevel = 0 ;
			hasStopped = true ;
		}
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
	    	cardinalDirection = controller.getCurrentDirection().rotateClockwise();	
		    break;

	    }
	    case RIGHT: {
	    	cardinalDirection = controller.getCurrentDirection().rotateCounterClockwise();
	    	break;
	    }
	    case FORWARD: {
	    	cardinalDirection = controller.getCurrentDirection();
	    	break;
	    	
	    }
	    case BACKWARD: {
	    	cardinalDirection = controller.getCurrentDirection().oppositeDirection() ;
	    	break;
	    }
	    
	    default: System.out.println("Inconsistent Direction Type");
	    }
	    
	    assert (cardinalDirection != null) ;
	    
	    return cardinalDirection ;
	}

	/**
	 * Returns true if robot is facing the exit.
	 * Used in distanceToObstacle(), where the distance is equal to infinity if 
	 * the robot is facing the exit.
	 * @param x
	 * @param y
	 * @param cardDirection, the cardinal direction that the robot is facing
	 * @return true if facing exit, false otherwise
	 */
	private boolean facingExit(int x, int y, CardinalDirection cardDirection) {

		int[] exitPos = controller.getMazeConfiguration().getMazedists().getExitPosition() ;
		
		while (	!controller.getMazeConfiguration().hasWall(x, y, cardDirection ) 
				&& x < controller.getMazeConfiguration().getWidth() 
				&& y < controller.getMazeConfiguration().getHeight() ) {  					//might have case where it goes out of bounds
			
			if (x == exitPos[0] && y == exitPos[1] ) {
				if (getCurrentDirection() == cardDirection) {
					return true ;
				}
			}
						
			switch (cardDirection) {
			case East : {
				x ++ ;
				break;
			}
			case West : {
				x -- ;
				break;
			}
			case South : {
				y ++ ;
				break ;
			}
			case North: {
				y --;
				break;
			}
			}
		}
		return false;
	}

}
