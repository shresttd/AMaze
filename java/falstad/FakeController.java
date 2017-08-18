package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.Constants.StateGUI;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.CardinalDirection;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Cells;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Distance;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Factory;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.MazeConfiguration;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.MazeContainer;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.MazeFactory;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Order;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Order.Builder;

//import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class extends the MazeController for testing purposes.
 * So that it ignores anything related to the GUI and redrawing operations.
 * @author TJ
 */
public class FakeController extends MazeController {
	
	int skill ;
	Builder builder ;
	boolean perfect ;
	private MazeConfiguration mazeConfig;
	//StateGUI state;
	int px, py;
	int dx, dy;
	private int angle ;
	
	Robot robot ;
	RobotDriver driver ;
	ManualDriver manualDriver; 
	
	public FakeController() {
		super() ;
		this.skill = 1 ;
		this.builder = Builder.DFS ;
		this.perfect = false ;
	//	this.mazeConfig = new MazeContainer() ;
		robot = new BasicRobot() ;
		robot.setMaze(this);
		driver = new Wizard() ;
		driver.setRobot(robot);	
		manualDriver = new ManualDriver();
		manualDriver.setRobot(robot) ;
		manualDriver.setMazeController(this) ;
	}
	
	public FakeController(Order.Builder builder, RobotDriver driver, int skill, boolean perfect) {
		super() ;
		this.skill = skill;
		this.builder = builder ;
		this.perfect = perfect ;
		//this.mazeConfig = new MazeContainer() ;
		robot = new BasicRobot() ;
		robot.setMaze(this);
		this.driver = driver ;
		this.driver.setRobot(robot);
		manualDriver = new ManualDriver();
		manualDriver.setRobot(robot) ;
		manualDriver.setMazeController(this) ;		
	}
	
	public FakeController(Order.Builder builder) {
		// TODO Auto-generated constructor stub
		super() ;
		this.skill = 1 ;
		this.builder = builder ; 
		this.perfect = false ;
		
	}
	public FakeController(String filename) {
		// TODO Auto-generated constructor stub
		super() ;
		this.skill = 1 ;
		this.builder = Builder.DFS ;
		this.perfect = false ;
		
	}
	
	@Override
	public void init() {
	//	this.state = StateGUI.STATE_PLAY ;
	}
		
	@Override
	public void deliver(MazeConfiguration mazeConfig) {

		this.mazeConfig = mazeConfig;
		int[] start = mazeConfig.getStartingPosition() ;

		setCurrentPosition(start[0],start[1]) ;
		setCurrentDirection(1, 0) ;
	}

	@Override
	public MazeConfiguration getMazeConfiguration() {
		return this.mazeConfig;
	}
	/*@Override
	protected StateGUI getState(){
		return state;
	}*/
	@Override
	public Robot getRobot() {
		return this.robot;
	}
	public ManualDriver getManualDriver() {
		return this.manualDriver;
	}
	@Override
	public RobotDriver getDriver() {
		return this.driver ;
	}
	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean checkMove(int dir) {
		CardinalDirection cd = null;
		switch (dir) {
		case 1: // forward
			cd = getCurrentDirection(); 
			break;
		case -1: // backward
			cd = getCurrentDirection().oppositeDirection();
			break;
		default:
			throw new RuntimeException("Unexpexted direction value: " + dir);
		}
		return !mazeConfig.hasWall(px, py, cd);
	}
	
	@Override
	public synchronized void walk(int dir) {
		if (!checkMove(dir)) 
			return;
		/*// walkStep is a parameter of the redraw method in FirstPersonDrawer
		// it is used there for scaling steps
		// so walkStep is implicitly used in slowedDownRedraw which triggers the redraw
		// operation on all listed viewers
		for (int step = 0; step != 4; step++) {
			walkStep += dir;
			//slowedDownRedraw();
		} */
		setCurrentPosition(px + dir*dx, py + dir*dy) ;
		//walkStep = 0;
		//logPosition();
	}
	
	@Override
	public synchronized void rotate(int dir) {
		final int originalAngle = angle;
		final int steps = 4;

		for (int i = 0; i != steps; i++) {
			// add 1/4 of 90 degrees per step 
			// if dir is -1 then subtract instead of addition
			angle = originalAngle + dir*(90*(i+1))/steps; 
			//rotateStep();
		}
		setCurrentDirection((int) Math.cos(radify(angle)), (int) Math.sin(radify(angle))) ;
		//logPosition();
	}
	
	@Override
	public boolean isOutside(int x, int y) {
		return !mazeConfig.isValidPosition(x, y) ;
	}
	
	@Override
	public void setCurrentPosition(int x, int y)
	{
		px = x ;
		py = y ;
	}
	
	@Override
	public void setCurrentDirection(int x, int y)
	{
		dx = x ;
		dy = y ;
	}
	
	@Override
	public int[] getCurrentPosition() {
		int[] result = new int[2];
		result[0] = px;
		result[1] = py;
		return result;
	}
	
	@Override
	public CardinalDirection getCurrentDirection() {
		return CardinalDirection.getDirection(dx, dy);
	}
	
	/*@Override
	protected void switchToFinishScreen() {
		setState(StateGUI.STATE_FINISH);
	}*/
	
	/*@Override
	public boolean keyDown (int key) { 
		switch (key) {
		case Event.UP: case 'k': case '8':
			walk(1);
			if (isOutside(px,py)) {
				//switchToFinishScreen();
			}
			break;
		case Event.LEFT: case 'h': case '4':
			rotate(1);
			break;
		case Event.RIGHT: case 'l': case '6':
			// turn right
			rotate(-1);
			break;
		case Event.DOWN: case 'j': case '2':
			walk(-1);
			if (isOutside(px,py)) {
				//switchToFinishScreen();
			}
			break;	
		}
		return true ;
	}*/
}
