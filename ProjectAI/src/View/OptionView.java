package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import command.CreateActionDialogCommand;
import command.CreateGoalDialogCommand;
import command.CreateSoDialogCommand;
import command.EliminateActionCommand;

public class OptionView extends Group{

	CreateSoDialogCommand so = new CreateSoDialogCommand();
	CreateGoalDialogCommand goalCommand = new CreateGoalDialogCommand();
	EliminateActionCommand elimAct = new EliminateActionCommand();
	CreateActionDialogCommand actionCommnd = new CreateActionDialogCommand();

	Composite containerInitState;
	Composite containerGoalState;
	TreeActioDomainView treeAction;
	
	
	public OptionView(Composite parent, int style,String name) {
		super(parent, style);
		this.setText(name);
		this.setLayout();
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		this.setLayout(new GridLayout(4, false));
	}
	
	public void setContainerInitialState(Composite container) {
		this.containerInitState=container;
	}
	
	public void setContainerGoalState(Composite container) {
		this.containerGoalState=container;
	}
	
	
	
	public void setTreeAction(TreeActioDomainView treeAction) {
		this.treeAction = treeAction;
	}

	public void createContent() {
		Label initialState = new Label(this, SWT.ALL);
		initialState.setText("Initial State: ");

		Button bInitState = new Button(this, SWT.PUSH);
		Image img = new Image(this.getDisplay(), "img/add.png");
		bInitState.setImage(img);

		GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.horizontalSpan = 2;

		Label finalState = new Label(this, SWT.ALL);
		finalState.setText("Goal State: ");
		
		Button bFnState = new Button(this, SWT.PUSH);
		bFnState.setImage(img);

		Label actionLabel = new Label(this, SWT.ALL);
		actionLabel.setText("Action:  ");

		Button bntAct = new Button(this, SWT.PUSH);
		bntAct.setImage(img);
		
		bInitState.addListener(SWT.Selection, getListenerbtnIn());
		bFnState.addListener(SWT.Selection, getListenerBtnGoal());
		bntAct.addListener(SWT.Selection, getListenerbtnAction());
	}
	
	public Listener getListenerbtnIn() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				so.execute(containerInitState);
			}
		};
		
		
		return l;
	}
	
	public Listener getListenerBtnGoal() {
		Listener l =  new Listener() {

			
			@Override
			public void handleEvent(Event event) {
				goalCommand.execute(containerGoalState);

			}
		};
		
		
		return l;
	}
	
	public Listener getListenerbtnAction() {
		Listener l =  new Listener() {

			@Override
			public void handleEvent(Event event) {
				actionCommnd.execute(treeAction, treeAction.getActionList());
				elimAct.execute(treeAction.getActionList());
				pack();
			}	
		};
		
		
		return l;
	}
	
	@Override
	protected void checkSubclass() {
	}
	
}
