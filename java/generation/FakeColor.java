package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation;

public class FakeColor {

	int val1; 
	int val2; 
	int val3; 
	public FakeColor() {
	}
	public FakeColor(int val1, int val2, int val3) {
		this.val1 = val1; 
		this.val2 = val2; 
		this.val3 = val3; 
	}
	
	public int[] getFakeColor() {
		int[] fakeCol = {val1, val2, val3} ;
		return fakeCol; 
	}
}
