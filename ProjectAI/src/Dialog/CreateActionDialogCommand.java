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
	ArrayList<Action> actions;
	
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
//				ArrayList<Action> list=getActionList();
				dialog = new CreateActionDialog(comp,actions);
				dialog.createContent();
				if (var1 instanceof Combo[]) {
					Combo[] combo = (Combo[]) var1;
					Combo comboOption = (Combo) combo[0];
					Combo comboAction = (Combo) combo[1];
					dialog.setListAction(actions);		
					dialog.setComboOption(comboOption);
					dialog.setComboAction(comboAction);
					

				}	
//				if(list !=null) {
//					list.addAll(getActionList());
//					setActionList(list);
//				}
				

			}
		}

	}
	
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
   public void setAction(ArrayList<Action> a) {
	   actions=a;
   }
	
	

	
//
//	
//	public void setActionList(ArrayList<Action> a) {
//		action=a;
//	}
	
	
}
