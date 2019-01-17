package command;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Dialog.IDialog;
import Dialog.IDialogNewState;

public class ChangePrecCommand implements ICommand {

	Action a;
	TreeItem itemRoot;
	
	@Override
	public boolean canExecute(Object var1, Object var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Object var1, Object var2) {
		if (var1 instanceof Action) {
			if (var2 instanceof TreeItem) {
				itemRoot = (TreeItem) var2;
				a = (Action) var1;
				IDialogNewState dial = new IDialogNewState(itemRoot.getParent().getShell(),SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER) {
					@Override
					public void createContent() {
						// TODO Auto-generated method stub
						super.createContent();
						getLabel().setText("Modify the precondition of " + a.getName());
						List l = getList();

						for (int i = 0; i < a.getPrec().size(); i++) {
							l.add(a.getPrec().get(i));
						}
						setListPCond(a.getPrec());
					}

					@Override
					public Listener getOkbtnListener() {

						return new Listener() {

							@Override
							public void handleEvent(Event event) {
								updatePrec(getList());
								updateTree();
								getDialog().dispose();
							}
						};
					}
				};
				dial.createContent();
			}

		}

	}

	public void updatePrec(List l) {
		a.setPrec(new ArrayList<>());
		for(int i=0;i<l.getItemCount();i++) {
			a.getPrec().add(l.getItem(i));
		}
	}
	
	public void updateTree() {
		TreeItem prec=itemRoot.getItem(0);
		prec.removeAll();
		for(int i=0;i<a.getPrec().size();i++) {
			TreeItem item=new TreeItem(prec, SWT.ALL);
			item.setText(a.getPrec().get(i));
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
