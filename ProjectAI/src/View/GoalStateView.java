package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import container.ContainerGoalState;
import container.IContainerState;

public class GoalStateView extends IStateView{

	
	public GoalStateView(Composite parent, int style) {
		super(parent, style);
		
	}

	public void setLayout() {
		
	}

	public void createContent() {
		this.containerState=new ContainerGoalState(this, SWT.NONE);
		this.containerState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		this.containerState.setLayout(new FillLayout());
		this.containerState.setLocation(30,30);

	}
	
	

	@Override
	protected void checkSubclass() {
	}
}
