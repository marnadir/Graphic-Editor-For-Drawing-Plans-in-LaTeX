package command;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Dialog.IDialogNewState;

public class ChangeEffCommand  implements ICommand{


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
						getLabel().setText("Modify the Effect of " + a.getName());
						getLabel().pack();
						List l = getList();

						for (int i = 0; i < a.getEffect().size(); i++) {
							l.add(a.getEffect().get(i));
						}
						setListPCond(a.getEffect());
					}

					@Override
					public Listener getOkbtnListener() {

						return new Listener() {

							@Override
							public void handleEvent(Event event) {
								updateEff(getList());
								updateTree();
								dispose();
							}
						};
					}
				};
				dial.createContent();
			}

		}

	}

	public void updateEff(List l) {
		if(a.getEffect().size()>0 && a.getStandardLengthEff()==0) {
			a.setStandardLengthEff(14);
		}
		a.setEffect(new ArrayList<>());
		for(int i=0;i<l.getItemCount();i++) {
			a.getEffect().add(l.getItem(i));
		}
	}
	
	public void updateTree() {
		TreeItem eff=itemRoot.getItem(1);
		eff.removeAll();
		for(int i=0;i<a.getEffect().size();i++) {
			TreeItem item=new TreeItem(eff, SWT.ALL);
			item.setText(a.getEffect().get(i));
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
