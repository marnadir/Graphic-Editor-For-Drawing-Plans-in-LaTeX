package View;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import PlanPart.PlanContent;
import dialog.PostionActionDialog;

public class PositionInPlanView extends Composite{

	
	Composite compositeSetting;
//	Composite compBtn;
//	Composite compList;
//	Composite compRef;
	
	
	Button addBtnAction;
	PlanContent contentPlan;
	Button addBtnState;

	
	public PositionInPlanView(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		setLayout(new GridLayout(2, true));
	}

	public void createContent() {
		Label l = new Label(this, SWT.ALL);
		l.setText("Setting up of actions/state cordinate "+"\n"+"\n"+
		"-Through the button 'Add new setting' it is possible to align different actions."+"\n"+
		"Check the actions that you want to align and select the action which the other actions"+"\n"+
		" must have the same X/Y-cordinate "+"\n"+
		"-Through the button 'Synchronization State' it possible to align the goal-state to the Y-cordinate of Iniatal-state"+"\n");
		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		l.setLayoutData(gridData);

		addBtnAction = new Button(this, SWT.ALL);
		addBtnAction.setText("Add new setting");
		addBtnAction.addListener(SWT.Selection, getAddListener());
		
		addBtnState=new Button(this, SWT.ALL);
		addBtnState.setText("Synchronization state");
		addBtnState.addListener(SWT.Selection, getAddStateListener());
		
		

		Group listSetting=new Group(this, SWT.ALL);
		listSetting.setText("List of recent settings");
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
	    listSetting.setLayoutData(gridData);
		
		compositeSetting = new Composite(listSetting, SWT.ALL);
	    compositeSetting.setLayout(new GridLayout(2, false));
		compositeSetting.pack();
		pack();
	}
	
	private Listener getAddListener() {
		Listener l;
		
		
		l=new Listener() {
		
			@Override
			public void handleEvent(Event event) {
				
				PostionActionDialog dialog=new PostionActionDialog(contentPlan.getShell()
						, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER | SWT.RESIZE);
				dialog.setNodes(contentPlan.getActionInPlan());
				dialog.setCompList(compositeSetting);
				dialog.setContentPlan(contentPlan);
				dialog.createContent();
				dialog.pack();
			
				
				
			}
		};
		return l;
	}

	private Listener getAddStateListener() {
		Listener l;

		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(contentPlan.getInitialStateCanvas()!=null && contentPlan.getGoalStateCanvas()!=null) {
					contentPlan.getGoalStateCanvas().getParent().setLocation(
							contentPlan.getGoalStateCanvas().getParent().getLocation().x,
							contentPlan.getInitialStateCanvas().getParent().getLocation().y);
				}	

			}
		};
		return l;
	}

	public void setContentPlan(PlanContent contentPlan) {
		this.contentPlan = contentPlan;	
	}


	
	
	
}
