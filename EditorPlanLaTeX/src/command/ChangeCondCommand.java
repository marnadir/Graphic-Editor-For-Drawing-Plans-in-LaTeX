package command;

import org.eclipse.swt.SWT;

import dialog.state.ChangeCondDialog;
import so_goalState.IStateCanvas;
/**
 * Command which allows to open the dialog for add/remove preconditions and/or effects of 
 * the initial/goal state.
 * @author nadir
 * */
public class ChangeCondCommand implements ICommand {

	IStateCanvas canvas;

	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof IStateCanvas) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (canExecute(var1,null)) {
			canvas = (IStateCanvas) var1;
			ChangeCondDialog dial = new ChangeCondDialog(canvas.getShell(),
					SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER |SWT.RESIZE);
			dial.setCanvas(canvas);
			dial.createContent();
		}
	}


	
	@Override
	public String getName() {
		return null;
	}

}

