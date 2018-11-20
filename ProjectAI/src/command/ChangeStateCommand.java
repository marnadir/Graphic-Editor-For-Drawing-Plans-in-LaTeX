package command;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import GUI.CreateSoDialog;
import logic.InitialState;

public class ChangeStateCommand  implements ICommand{

	CreateSoDialog dialog;
	Composite c;
	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof Combo) {
			Combo combo = (Combo) var1;
			if(combo.getText().equals("Change")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if(var1 instanceof CreateSoDialog) {
			dialog=(CreateSoDialog)var1;
			dialog.getDialog().setVisible(true);

		}
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
