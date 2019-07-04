package command;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import View.TreeActioDomainView;
import dialogAction.ChangeActionDialog;
/**
 * Command which allows to open the dialog for editing an domain-action(name,preconditions and effects).
 * @author nadir
 * */
public class ChangeActionDialogCommand implements ICommand{

	Action a;
	TreeItem itemRoot;
	TreeActioDomainView treeActionDomain;
	
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

				ChangeActionDialog dialog = new ChangeActionDialog(itemRoot.getParent().getShell(),
						SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
				dialog.setVariable(a, itemRoot);
				dialog.createContent();
				dialog.pack();
			}
		}

	}

	public void execute(Object var1, Object var2, Object var3) {
	

	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
