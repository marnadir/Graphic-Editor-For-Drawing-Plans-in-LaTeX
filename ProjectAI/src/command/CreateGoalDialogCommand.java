package command;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import GUI.CreateGoalDialog;
import State.GoalStateCanvas;

public class CreateGoalDialogCommand implements ICommand {

	
	CreateGoalDialog dialog;
	
	// check if create as option
	@Override
	public boolean canExecute(Object var1, Object var2) {
		return true;
	}

	@Override
	public void execute(Object var1, Object var2) {

		if (canExecute(var1, var2)) {
			if (var1 instanceof Composite) {
				Composite comp = (Composite) var1;
				if(dialog ==null) {
					dialog = new CreateGoalDialog(comp);	
				}
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