package State;



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

import View.TreeActioDomainView;
import command.CreateActionDialogCommand;
import command.CreateGoalDialogCommand;
import command.CreateSoDialogCommand;
import command.EliminateActionCommand;
import resourceLoader.ResourceLoader;
import state.ContainerGoalState;
import state.ContainerInitialState;

public class CreateStateContainer extends Composite{

	CreateSoDialogCommand so = new CreateSoDialogCommand();
	CreateGoalDialogCommand goalCommand = new CreateGoalDialogCommand();
	CreateActionDialogCommand actionCommnd = new CreateActionDialogCommand();
	ContainerInitialState containerInitialState;
	ContainerGoalState containerGoalState;

	
	
	public CreateStateContainer(Composite parent, int style,String name) {
		super(parent, style);
		this.setLayout();
		// TODO Auto-generated constructor stub
	}

	public void setLayout() {
		this.setLayout(new GridLayout(2, false));
	}
	

	

	public void setContainerInitialState(ContainerInitialState containerInitialState) {
		this.containerInitialState = containerInitialState;
	}

	public void setContainerGoalState(ContainerGoalState containerGoalState) {
		this.containerGoalState = containerGoalState;
	}

	public void createContent() {

		Button bInitState = new Button(this, SWT.PUSH);
		bInitState.setText("Initial State");

		Image img ;
		img = new Image(getDisplay(), ResourceLoader.load("img/add.png"));

		bInitState.setImage(img);

		GridData gridData = new GridData(GridData.CENTER, GridData.CENTER, false, false);
		gridData.horizontalSpan = 2;

		Button bFnState = new Button(this, SWT.PUSH);
		bFnState.setText("Goal State");
		bFnState.setImage(img);

		bInitState.addListener(SWT.Selection, getListenerbtnIn());
		bFnState.addListener(SWT.Selection, getListenerBtnGoal());
	}
	
	public Listener getListenerbtnIn() {
		Listener l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				so.execute(containerInitialState);

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
	

	
	@Override
	protected void checkSubclass() {
	}
	
}
