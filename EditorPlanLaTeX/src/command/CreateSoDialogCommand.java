package command;

import containerState.IContainerState;
import dialog.state.CreateSoDialog;

/**
 * Command which allows to open the dialog for creating the initial state.
 * @author nadir
 * */

public class CreateSoDialogCommand implements ICommand {

	
	CreateSoDialog dialog;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof IContainerState) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {

		if (canExecute(var1, null)) {

			IContainerState comp = (IContainerState) var1;
			/* container of So is empty */
			if (comp.getChildren().length < 1) {
				dialog = new CreateSoDialog(comp);
				dialog.createContent();
			}

		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
