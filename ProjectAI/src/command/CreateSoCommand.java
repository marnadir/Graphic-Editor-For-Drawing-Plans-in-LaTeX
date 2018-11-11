package command;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class CreateSoCommand implements ICommand{

	
	//check if create as option
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof Shell) {
			Shell shell = (Shell) var1;

		sss
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
