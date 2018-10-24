/**
 * 
 */
package logic;

import command.ExitCommand;
import command.OpenCommand;
import command.SaveAsCommand;
import command.SaveCommand;

/**
 * @author nadir
 *
 */
public class Commands {
	
	/**
	 * 
	 */
	public Commands() {
		// TODO Auto-generated constructor stub
	}
	public ICommand getSaveCommand() {
		return new SaveCommand();
	}

	public ICommand getOpenCommand() {
		return new OpenCommand();
	}
	
	public ICommand getSaveAsCommand() {
		return new SaveAsCommand();
	}
	
	public ICommand getExitCommand() {
		return new ExitCommand();
	}
}
