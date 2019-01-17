package command;




import org.eclipse.swt.widgets.Composite;

import Dialog.CreateSoDialog;

public class CreateSoDialogCommand implements ICommand {

	
	CreateSoDialog dialog;
	
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
				
				/* container of So is empty*/ 
				if(comp.getChildren().length<1) {
					dialog = new CreateSoDialog(comp);
					dialog.createContent();		
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
