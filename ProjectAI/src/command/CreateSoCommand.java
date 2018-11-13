package command;



import org.eclipse.swt.widgets.Combo;
import GUI.CreateSoDialog;

public class CreateSoCommand implements ICommand {

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
			if (var1 instanceof Combo) {

				Combo combo = (Combo) var1;
				CreateSoDialog dialog = new CreateSoDialog(combo.getShell());
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
