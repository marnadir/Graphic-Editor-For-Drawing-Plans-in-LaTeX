package Dialog;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import GUI.CreateActionDialog;
import GUI.CreateSoDialog;
import command.ICommand;
import logic.Action;
import logic.InitialState;

public class CreateActionDialogCommand implements ICommand {

	
	CreateActionDialog dialog;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		
		if (var1 instanceof Combo[]) {
			Combo[] comboAction = (Combo[])var1;
			Combo comboOption = (Combo) comboAction[0];
			if(comboOption.getText().equals("Create")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (canExecute(var1, var2)) {
			if (var2 instanceof Composite) {
				Composite comp = (Composite) var2;
				dialog = new CreateActionDialog(comp);
				dialog.createContent();
				if (var1 instanceof Combo[]) {
					Combo[] combo = (Combo[]) var1;
					Combo comboOption = (Combo) combo[0];
					Combo comboAction = (Combo) combo[1];
								
					dialog.setComboOption(comboOption);
					dialog.setComboAction(comboAction);

				}

			}
		}

	}
	
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Action> getActionList() {
		return dialog.getActionList();
	}
	
	
}
