package command;

import java.util.ArrayList;


import Action.Action;
import View.TreeActioDomainView;
import dialogAction.CreateActionDialog;
/**
 * Command which allows to open the dialog for creating a new action.
 * @author nadir
 * */

public class CreateActionDialogCommand implements ICommand {

	
	CreateActionDialog dialog;
	ArrayList<Action> actions;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof TreeActioDomainView) {
			if (var2 instanceof ArrayList<?>) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (canExecute(var1, var2)) {
			TreeActioDomainView tree = (TreeActioDomainView) var1;
			actions = (ArrayList<Action>) var2;
			dialog = new CreateActionDialog(tree, actions);
			dialog.createContent();
		}

	}
	
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


}
