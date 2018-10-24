/**
 * 
 */
package command;

import java.awt.Window;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import logic.ICommand;

/**
 * @author nadir
 *
 */
public class ExitCommand implements ICommand {

	private final String  name="Exit";

	public ExitCommand() {

	}
	
	
	/* (non-Javadoc)
	 * @see logic.ICommand#canExecute(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see logic.ICommand#execute(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void execute(Object var1, Object var2) {
		if(var1 instanceof JMenuBar) {
			JMenuBar menuBar=(JMenuBar)var1;
			Window window = SwingUtilities.getWindowAncestor(menuBar);
			if(window instanceof JFrame) {
				JFrame frame=(JFrame)window;
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see logic.ICommand#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}
