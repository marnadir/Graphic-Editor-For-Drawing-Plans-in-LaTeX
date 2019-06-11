package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import State.IContainerState;
import state.ContainerGoalState;

public class GoalStateView extends IStateView{

	
	public GoalStateView(Composite parent, int style) {
		super(parent, style);
		
	}

	public void setLayout() {
		
	}

	public void createContent() {
		this.containerState=new ContainerGoalState(this, SWT.BORDER);
		this.containerState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		this.containerState.setLayout(new FillLayout());
		this.containerState.setLocation(70,80);

	}
	
	

	@Override
	protected void checkSubclass() {
	}
}
