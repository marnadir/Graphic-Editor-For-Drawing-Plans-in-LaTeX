package View;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Action.Node;
import Dialog.PostionActionDialog;
import PlanPart.PlanContent;

public class PositionInPlanView extends Composite{

	
	Composite compositeSetting;
	Button addBtn;
	PlanContent contentPlan;

	
	public PositionInPlanView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		setLayout(new GridLayout(2, true));
	}

	public void createContent() {
		Label l = new Label(this, SWT.ALL);
		l.setText("setting up of actions/state cordinate ");
		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		l.setLayoutData(gridData);

		addBtn = new Button(this, SWT.ALL);
		addBtn.setText("add new setting");
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		addBtn.setLayoutData(gridData);
		addBtn.addListener(SWT.Selection, getAddListener());

		Group listSetting=new Group(this, SWT.ALL);
		listSetting.setText("List of Setting");
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		listSetting.setLayoutData(gridData);
		
		compositeSetting = new Composite(listSetting, SWT.ALL);
		compositeSetting.setLayout(new GridLayout(3, false));
		
		

		pack();
	}
	
	private Listener getAddListener() {
		Listener l;
		
		
		l=new Listener() {
		
			@Override
			public void handleEvent(Event event) {
				
				PostionActionDialog dialog=new PostionActionDialog(contentPlan.getShell()
						, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
				dialog.setNodes(contentPlan.getActionInPlan());
				dialog.setCompList(compositeSetting);
				dialog.createContent();
				dialog.pack();
			
				
				
			}
		};
		return l;
	}

	public void setContentPlan(PlanContent contentPlan) {
		this.contentPlan = contentPlan;
		
	
		
	}
	
	
	
}
