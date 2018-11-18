package command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import GUI.CreateSoDialog;
import logic.InitialState;

public class ElimanateStateCommand  implements ICommand{

	@Override
	public boolean canExecute(Object var1, Object var2) {
		if (var1 instanceof Combo) {
			Combo combo = (Combo) var1;
			if(combo.getText().equals("Elimanate")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (canExecute(var1, var2)) {
			if (var2 instanceof InitialState) {
				InitialState so = (InitialState) var2;
				so.elimanate();
				
				if(var1 instanceof Combo) {
					Combo combo=(Combo)var1;
					updateCombo(combo);
				}
			}
		}
		
	}

	
	public void updateCombo(Combo combo) {
		List<String> possibleOption=new ArrayList<String>();
		possibleOption.add("Create");
		String[] convertList=possibleOption.toArray(new String[possibleOption.size()]);
		combo.setItems (convertList);
		combo.pack();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
