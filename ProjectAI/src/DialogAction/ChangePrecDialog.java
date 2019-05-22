package DialogAction;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

import Action.Action;
import Action.GlobalValue;
import Dialog.IDialogNewState;

public class ChangePrecDialog extends IDialogNewState{

	
	ArrayList<String>  listPCond;
	List list;
	Text newPrec;
	Button buttonNeg;
	Action a;
	TreeItem itemRoot;
	final double CM_MEASUREMNT= 37.7957517575025;
	
	public ChangePrecDialog(Shell shell, int style) {
		super(shell, style);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		super.createContent();
		btnEdit.setVisible(true);
		newCond.setVisible(true);
	    label.setText("Change preconditions of the action: " + a.getName());
		List l = getList();

		for (int i = 0; i < a.getPrec().size(); i++) {
			l.add(a.getPrec().get(i));
		}
		setListPCond(a.getPrec());
		pack();
		setSize(250,300);
		mainComposite.pack();		

	}

	@Override
	public Listener getOkbtnListener() {

		return new Listener() {

			@Override
			public void handleEvent(Event event) {
				updatePrec(getList());
				updateTree();
				dispose();
			}
		};
	}
	
	public void setVariable(Action a,TreeItem itemRoot) {
		this.a=a;
		this.itemRoot=itemRoot;
	}
	
	private void updatePrec(List l) {
		if(a.getPrec().size()>0 && a.getStandardLengthPrec()==0) {
			
			a.setStandardLengthPrec( CM_MEASUREMNT*Double.parseDouble(GlobalValue.lengthsOfEmptyTasks));
		}
		a.setPrec(new ArrayList<>());
		for(int i=0;i<l.getItemCount();i++) {
			a.getPrec().add(l.getItem(i));
		}
	}
	
	private void updateTree() {
		TreeItem prec=itemRoot.getItem(0);
		prec.removeAll();
		for(int i=0;i<a.getPrec().size();i++) {
			TreeItem item=new TreeItem(prec, SWT.ALL);
			item.setText(a.getPrec().get(i));
		}
	}

}
