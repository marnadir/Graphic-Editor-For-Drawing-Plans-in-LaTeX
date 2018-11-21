package Dialog;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import GUI.CreateActionDialog;
import GUI.CreateSoDialog;
import command.ICommand;

public class CreateActionDialogCommand implements ICommand {

	
	CreateActionDialog dialog;
	
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
				dialog = new CreateActionDialog(comp);
				dialog.createContent();
				if(var1 instanceof Combo) {
					Combo combo=(Combo)var1;
					//dialog.setCombo(combo);
					
				}
				
			}
		}
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
