package command;

import java.util.ArrayList;


import org.eclipse.swt.widgets.Tree;

import Action.Action;
import Dialog.CreateActionDialog;


public class CreateActionDialogCommand implements ICommand {

	
	CreateActionDialog dialog;
	ArrayList<Action> actions;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		
//		if (var1 instanceof Combo[]) {
//			Combo[] comboAction = (Combo[])var1;
//			Combo comboOption = (Combo) comboAction[0];
//			if(comboOption.getText().equals("Create")) {
//				return true;
//			}
//		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof Tree) {
			Tree tree = (Tree) var1;
			if (var2 instanceof ArrayList<?>) {
				actions=(ArrayList<Action>)var2;
				dialog = new CreateActionDialog(tree,actions);
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
