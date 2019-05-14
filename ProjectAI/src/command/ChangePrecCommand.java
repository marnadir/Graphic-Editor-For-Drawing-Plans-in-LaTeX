package command;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import DialogAction.ChangePrecDialog;

public class ChangePrecCommand implements ICommand {

	Action a;
	TreeItem itemRoot;
	final double CM_MEASUREMNT= 37.7957517575025;

	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof Action) {
			if (var2 instanceof TreeItem) {
				itemRoot = (TreeItem) var2;
				a = (Action) var1;
				ChangePrecDialog dialog=new ChangePrecDialog(itemRoot.getParent().getShell(),
						SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
				dialog.setVariable(a, itemRoot);
				dialog.createContent();
				dialog.pack();
			}

		}

	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
