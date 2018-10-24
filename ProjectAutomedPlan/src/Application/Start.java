/**
 * 
 */
package Application;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import gui.DomainPannel;
import gui.MenuBar;
import gui.PlanPannel;
import gui.SplitTwoPane;

/**
 * @author nadir
 *
 */
public class Start {
	
	/**
	 * 
	 */
	public Start() {
		createFrame("Tool");
	}

	private JFrame createFrame(String title) {
		JFrame frame=new JFrame(title);
		
		MenuBar menuBar=new MenuBar();
		DomainPannel domain=new DomainPannel();
		PlanPannel planPane=new PlanPannel();
		SplitTwoPane splitPane=new SplitTwoPane(domain, planPane);
		
		frame.setSize(1365, 768);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setJMenuBar(menuBar.getMenuBar());
	    frame.getContentPane().add(splitPane);
	    return frame;
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		
		EventQueue.invokeAndWait(() -> new Start());
	}
	
}
