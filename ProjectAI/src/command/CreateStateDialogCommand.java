package command;



import java.security.GeneralSecurityException;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import GUI.CreateSoDialog;
import logic.InitialState;

public class CreateStateDialogCommand implements ICommand {

	
	CreateSoDialog dialog;
	
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
				dialog = new CreateSoDialog(comp);
				dialog.createContent();
				if(var1 instanceof Combo) {
					Combo combo=(Combo)var1;
					dialog.setCombo(combo);
					
				}
				
			}
		}
	}

	public InitialState getInitialState() {
		return dialog.getInitialState();
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
