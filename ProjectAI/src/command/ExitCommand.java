package command;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ExitCommand  implements ICommand {
	
	public ExitCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof Shell) {
			Shell shell = (Shell) var1;
			MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
			dialog.setText("Question");
			dialog.setMessage("Exit?");
			if (var2 instanceof Event) {
				Event e = (Event) var2;
				if (e.type == SWT.Close)
					e.doit = false;
				if (dialog.open() != SWT.OK)
					return;
				shell.dispose();
			}
		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
