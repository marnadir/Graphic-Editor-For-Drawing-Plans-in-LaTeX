package command;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import Dialog.IDialogNewState;
import State.IStateCanvas;

public class ChangeCondCommand implements ICommand {

	IStateCanvas canvas;
	ArrayList<String> conds;

	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof IStateCanvas) {

			canvas = (IStateCanvas) var1;
			IDialogNewState dial = new IDialogNewState(canvas.getShell(),
					SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER |SWT.RESIZE) {
				@Override
				public void createContent() {
					// TODO Auto-generated method stub
					super.createContent();
					btnEdit.setVisible(true);
					newCond.setVisible(true);

					
					label.setText("Add/remove conds.");
					List l = getList();
					conds = canvas.getState().getConds();

					for (int i = 0; i < conds.size(); i++) {
						l.add(conds.get(i));
					}
					setListPCond(conds);

					pack();

				}

				@Override
				public Listener getOkbtnListener() {

					return new Listener() {

						@Override
						public void handleEvent(Event event) {
							if (getList().getItemCount() > 0) {
								updatePrec(getList());
								dispose();
							}
						}
					};
				}
			};
			
			
			dial.createContent();
		}
		

	}

	public void updatePrec(List l) {
		canvas.getState().removeConds();
		for(int i=0;i<l.getItemCount();i++) {
			canvas.getState().addCond((l.getItem(i)));
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}

