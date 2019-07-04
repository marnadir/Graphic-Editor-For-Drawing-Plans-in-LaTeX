package command;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
/**
 * Command which allows to close the programm.
 * @author nadir
 * */


public class ExitCommand  implements ICommand {
	
	public ExitCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof Shell) {
			if (var2 instanceof Event) {
				return true;
			}
			
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (canExecute(var1, var2)) {
			Shell shell = (Shell) var1;
			MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
			dialog.setText("Question");
			dialog.setMessage("Exit?");
			Event e = (Event) var2;
			if (e.type == SWT.Close)
				e.doit = false;
			if (dialog.open() != SWT.OK)
				return;
			shell.dispose();

		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
