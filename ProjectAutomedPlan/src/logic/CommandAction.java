/**
 * 
 */
package logic;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

/**
 * @author nadir
 *
 */
public class CommandAction extends AbstractAction{

	private  ICommand  command;
	private Object obj;
	private JComponent component;
	
	
	public CommandAction(ICommand command,Object object,JComponent j) {
		this.command=command;
		this.obj=object;
		this.component=j;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		command.execute(component);
		
	}

	public ICommand getCommand() {
		return this.command;
	}

	public Object getParameter() {
		return this.obj;
	}

	public void setParameter(Object object) {
		this.obj = object;
	}
	
	
}
