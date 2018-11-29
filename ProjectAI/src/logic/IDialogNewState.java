package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class IDialogNewState extends IDialog{




	ArrayList<String>  listPCond;
	List list;
	Text newPrec;
	Button buttonNeg;
	Composite composite;
	
	
	public IDialogNewState(Shell shell) {
		super(shell);
		listPCond=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		// TODO Auto-generated method stub
		this.getLabel().setText("Create a new  state");
		composite = this.getComposite();
		composite.setLayout(new GridLayout(3, false));

		newPrec = new Text(composite, SWT.SINGLE | SWT.BORDER);
		buttonNeg = new Button(composite, SWT.CHECK);
		buttonNeg.setText("neg");

		Button btnAddPrec = new Button(composite, SWT.PUSH);
		Image icon = new Image(composite.getDisplay(), "img/addCond.png");
		btnAddPrec.setImage(icon);

		btnAddPrec.addListener(SWT.Selection, getAddListener());
		
		list = new List (composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		
		Button btnDeletePrec=new Button(composite, SWT.PUSH);
		icon = new Image(composite.getDisplay(), "img/deleteCond.png");
		btnDeletePrec.setImage(icon);
		btnDeletePrec.addListener(SWT.Selection, getDelListener());
		

		this.getDialog().pack();
	}

	
	public Listener getAddListener() {
		Listener buttonListener = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String cond=newPrec.getText();
				boolean isChecked=buttonNeg.getSelection();
				if(!(listPCond.contains(cond)) && !cond.equals("")) {
					if(isChecked) {
						cond="¬"+cond;
					} 
					listPCond.add(cond);
					list.add(cond);
				}
				newPrec.setText("");
			}
		};
		
		return buttonListener;
	}
	
	
	public Listener getDelListener() {
		Listener buttonListener = new Listener() {

			@Override
			public void handleEvent(Event event) {
				int index = list.getSelectionIndex();
				if (!(index > listPCond.size()) && index != -1) {
					list.remove(index);
					listPCond.remove(index);
				}

			}
		};

		return buttonListener;

	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public ArrayList<String> getListPCond() {
		return listPCond;
	}

	public void setListPCond(ArrayList<String> listPCond) {
		this.listPCond = listPCond;
	}

	public ArrayList<String> getCond(){
		return listPCond;
	}
}
