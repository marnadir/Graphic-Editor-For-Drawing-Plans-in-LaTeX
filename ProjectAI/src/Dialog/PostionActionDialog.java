package Dialog;

import java.util.ArrayList;

import org.bouncycastle.voms.VOMSAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import Action.Action;
import Action.Node;

public class PostionActionDialog extends IDialog{
	
	ArrayList<Node> nodes;
	ArrayList<Button> buttonList;
	ArrayList<Action> actionList;
	ArrayList<Action> selectedAction;
	Composite compList;
	Combo combo;
	Button btnX;
	Button btnY;
	
	


	public PostionActionDialog(Shell shell, int style) {
		super(shell, style);
		buttonList=new ArrayList<>();
		actionList=new ArrayList<>();
		selectedAction=new ArrayList<>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContent() {
		label.setText("list of action in Plan");

		
		composite.setLayout(new GridLayout(1, false));

		GridData gridData;
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		
		for(Node node:nodes) {
			Button btn =new Button(composite, SWT.CHECK);
			btn.setText(node.getAction().getName()+" ["+node.getID()+"]");
			btn.addListener(SWT.Selection, getbtnSelectionListener(btn));
			//btn.setLayoutData(gridData);
			buttonList.add(btn);
			actionList.add(node.getAction());
			btn.pack();
			
			
		}
		composite.pack();
		
		combo=new Combo(composite, SWT.READ_ONLY);
		combo.setSize(composite.getSize().x,combo.getSize().y);
		combo.setLayoutData(gridData);
		
		Composite comp=new Composite(composite, SWT.ALL);
		comp.setLayout(new GridLayout(2, false));
		
		btnX=new  Button(comp, SWT.CHECK);
		btnX.setText("X");
		
		btnY=new  Button(comp, SWT.CHECK);
		btnY.setText("Y");
		
		btnX.setSelection(true);
		
		btnX.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnX.getSelection()) {
					btnY.setSelection(false);
				}else {
					btnY.setSelection(true);
				}
				
			}
		});
		
		
		btnY.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btnY.getSelection()) {
					btnX.setSelection(false);
				}else {
					btnX.setSelection(true);
				}
				
			}
		});
		
		

		
		
	}

	
	public Listener getbtnSelectionListener(Button btn) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(btn.getSelection()) {
					combo.add(btn.getText());
					combo.setSize(composite.getSize().x,combo.getSize().y);
//					composite.pack();
//					pack();
					
				}else {
					combo.remove(btn.getText());
				}
			
			}
		};
		
		return l;
	}
	
	@Override
	public Listener getOkbtnListener() {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				for(int i=0;i<buttonList.size();i++) {
					if(buttonList.get(i).getSelection()) {
						selectedAction.add(actionList.get(i));
					}
				}
				changeCompList();
				dispose();
			}
		};
		
		return l;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	
	
	public void setCompList(Composite compList) {
		this.compList = compList;
	}

	private void changeCompList() {
		Button btnDelete=new Button(compList, SWT.ALL);
		Image img=new Image(getDisplay(), "img/deleteCond.png");
		btnDelete.setImage(img);
		Label lb=new Label(compList, SWT.BORDER);
		lb.setText(printArrayList(selectedAction));
		Label comparator=new Label(compList, SWT.BORDER);
		comparator.setText(combo.getText());
		compList.pack();
		compList.getParent().pack();
	}
	
	
	private String printArrayList(ArrayList<Action> actions) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < actions.size(); i++) {
			sb.append(actions.get(i).getName());
			if (i < actions.size() - 1) {
				sb.append(",");
			}

		}

		return sb.toString();
	}
}
