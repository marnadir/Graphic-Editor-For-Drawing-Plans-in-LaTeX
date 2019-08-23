package dialog.state;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import so_goalState.IStateCanvas;
/**
 * Dialog which allows to add/remove conditions of the initial/goal state.
 * @author nadir
 * */
public class ChangeCondDialog extends IDialogNewState{

	IStateCanvas canvas;
	ArrayList<String> conds;
	
	
	public ChangeCondDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}
	
	
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
	
	
	public void updatePrec(List l) {
		canvas.getState().removeConds();
		for(int i=0;i<l.getItemCount();i++) {
			canvas.getState().addCond((l.getItem(i)));
		}
	}


	public void setCanvas(IStateCanvas canvas) {
		this.canvas = canvas;
	}

	
	
}
