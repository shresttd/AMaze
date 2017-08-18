/*package tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.falstad;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import tjshrestha.cs301.cs.wm.edu.amazebytjshrestha.generation.Order;

/**
 *
 * @author TJ
 *
 */
/*public class SimpleActionListener implements ActionListener {
	
	private MazeApplication app;
	private MazeController controller;
	private Container container;
	
	public SimpleActionListener() {
	}

	public SimpleActionListener(MazeApplication application, MazeController controller, Container container) {
		this.app = application;
		this.controller = controller;
		this.container = container;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Chooses a builder algorithm
		String builder = (String) app.getBuilderBox().getSelectedItem();	
		if (builder == "Prim") {
			controller.setBuilder(Order.Builder.Prim);
		} else if (builder == "Eller") {
			controller.setBuilder(Order.Builder.Eller);
		} else {
			controller.setBuilder(Order.Builder.DFS);
		}
		
		// Chooses a driver algorithm
		String driver = (String) app.getDriverBox().getSelectedItem();
		if (driver == "Wizard") {
			controller.setDriver(new Wizard());
		} else if (driver == "Wall Follower") {
			controller.setDriver(new WallFollower());
		} else if (driver == "Pledge") {
			controller.setDriver(new Wizard());
		} else if (driver == "Explorer") {
			controller.setDriver(new Wizard());
		} else {
			controller.setDriver(null);
		}
		
		// Chooses a skill level
		String skill = (String) app.getSkillBox().getSelectedItem();
		int key = Integer.parseInt(skill);
		controller.switchToGeneratingScreen(key + 48);
		container.remove(app.getDriverBox());
		container.remove(app.getBuilderBox());
    	container.remove(app.getSkillBox());
    	container.remove(app.getStartButton()); 
		
		app.pack();
		container.repaint();
	}
}
	*/

