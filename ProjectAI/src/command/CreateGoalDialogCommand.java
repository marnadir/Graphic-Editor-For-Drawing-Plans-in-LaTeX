package command;



import java.security.GeneralSecurityException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import GUI.CreateGoalDialog;
import GUI.CreateSoDialog;
import logic.GoalState;
import logic.InitialState;

public class CreateGoalDialogCommand implements ICommand {

	
	CreateGoalDialog dialog;
	
	// check if create as option
	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof Combo) {
			Combo combo = (Combo) var1;
			if(combo.getText().equals("Create")) {
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
				
				dialog = new CreateGoalDialog(comp);
				dialog.createContent();
				if(var1 instanceof Combo) {
					Combo combo=(Combo)var1;
					dialog.setCombo(combo);
					
				}
				
			}
		}
	}

	public GoalState getGoalState() {
		return dialog.getGoalState();
	}
	
	public CreateGoalDialog getCreateGoalDialog() {
		return dialog;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}