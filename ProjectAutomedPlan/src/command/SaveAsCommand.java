/**
 * 
 */
package command;
import logic.ICommand;

/**
 * @author nadir
 *
 */
public class SaveAsCommand implements ICommand {

	private final String  name="Save As...";

	public SaveAsCommand() {

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
		if(canExecute(var1,var2)) {
			
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