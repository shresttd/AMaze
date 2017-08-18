/**
 * 
 */
package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation;

import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad.MazeController;

/**
 * @author TJShrestha
 *
 */
public class OrderStub implements Order{
    
	int skillStub = 0;
	
	Builder builderStub = Builder.DFS;
	
	boolean perfectStub = false;  
	
	private MazeConfiguration mazeConfigStub;

	int px, py, dx, dy, viewdx, viewdy, walkStep, angle;
	
	/**
	 * 
	 */
	public OrderStub() {
		
	}
	
	public OrderStub(int skill, Builder builder, boolean perfect) {
		// TODO Auto-generated constructor stub
		skillStub = skill ;
		builderStub = builder ;
		perfectStub = perfect; 
	}

	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return skillStub;
	}
	

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return builderStub;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfectStub;
	}

	@Override
	public void deliver(MazeConfiguration mazeConfig) {

		this.mazeConfigStub = mazeConfig;
		
		setCurrentPosition(0, 0) ;
		setCurrentDirection(1, 0) ;
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		
	}
	
	public MazeConfiguration getMazeConfiguration() {
		return this.mazeConfigStub;
	}
	
	/*private MazeConfiguration setMazeConfig(){
		
		int widthStub = 10;
		int heightStub = 10;
		
		mazeConfigStub = new MazeContainer();
		
		Cells cellsStub = new Cells(widthStub, heightStub);
		Distance distStub = new Distance(widthStub, heightStub);
		
		mazeConfigStub.setWidth(widthStub);
		mazeConfigStub.setHeight(heightStub);
		mazeConfigStub.setMazecells(cellsStub);
		mazeConfigStub.setMazedists(distStub);
		mazeConfigStub.setStartingPosition(0, 0);
		
		return mazeConfigStub;
	}*/
	
	protected void setCurrentPosition(int x, int y)
	{
		px = x ;
		py = y ;
	}
	private void setCurrentDirection(int x, int y)
	{
		dx = x ;
		dy = y ;
	}
	protected int[] getCurrentPosition() {
		int[] result = new int[2];
		result[0] = px;
		result[1] = py;
		return result;
	}
	protected CardinalDirection getCurrentDirection() {
		return CardinalDirection.getDirection(dx, dy);
	}

}
