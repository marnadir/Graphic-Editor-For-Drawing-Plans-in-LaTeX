package command;

import java.util.ArrayList;


import org.eclipse.swt.widgets.Combo;
import logic.Action;

public class EliminateActionCommand implements ICommand{

	@Override
	public boolean canExecute(Object var1, Object var2) {
		
		if (var1 instanceof Combo[]) {
			Combo[] comboAction = (Combo[])var1;
			Combo comboOption = (Combo) comboAction[0];
			if(comboOption.getText().equals("Eliminate")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (canExecute(var1, var2)) {
			if (var1 instanceof Combo[]) {
				Combo[] combo = (Combo[]) var1;
				Combo comboOption = (Combo) combo[0];
				Combo comboAction = (Combo) combo[1];
				if(var2 instanceof ArrayList<?>) {
					ArrayList<Action> actions=(ArrayList<Action>)var2;
					int index=-1;
					for(int i=0;i<actions.size();i++) {
						if(actions.get(i).getName().equals(comboAction.getText())) {
							index=i;
							comboAction.remove(i);
						}
					}
					actions.get(index).elimanate();
					actions.remove(index);
					
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
