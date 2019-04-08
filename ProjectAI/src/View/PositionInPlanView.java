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

import Dialog.PostionActionDialog;
import PlanPart.PlanContent;

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
		l.setText("setting up of actions/state cordinate ");
		GridData gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
		l.setLayoutData(gridData);

		addBtnAction = new Button(this, SWT.ALL);
		addBtnAction.setText("add new setting");
//		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
//		gridData.horizontalSpan = 2;
//		addBtnAction.setLayoutData(gridData);
		addBtnAction.addListener(SWT.Selection, getAddListener());
		
		addBtnState=new Button(this, SWT.ALL);
		addBtnState.setText("synchronization State");
		addBtnState.addListener(SWT.Selection, getAddStateListener());
		
		

		Group listSetting=new Group(this, SWT.ALL);
		listSetting.setText("List of recent Setting");
		gridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		gridData.horizontalSpan = 2;
	    listSetting.setLayoutData(gridData);
		
		compositeSetting = new Composite(listSetting, SWT.ALL);
	    compositeSetting.setLayout(new GridLayout(2, false));
//		compositeSetting.setLayout(new FillLayout());
		
//		compBtn=new Composite(compositeSetting, SWT.BORDER);
//	    compBtn.setLayout(new FillLayout(SWT.VERTICAL));
//	    Label l1=new Label(listSetting, SWT.ALL);
//	    l1.setText("Remove?");
//	    l1.pack();
	    
	    
//	    compList=new Composite(compositeSetting, SWT.BORDER);
//	    compList.setLayout(new FillLayout(SWT.VERTICAL));
//		Label l2=new Label(listSetting, SWT.ALL);
//		l2.setText("list of Action/state");
//		l2.pack();
		
//		compRef=new Composite(compositeSetting, SWT.BORDER);
//		compRef.setLayout(new FillLayout(SWT.VERTICAL));
//		Label l3=new Label(listSetting, SWT.ALL);
//		l3.setText("reference");
//		l3.pack();
		
		//compositeSetting.pack();
		compositeSetting.pack();
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
