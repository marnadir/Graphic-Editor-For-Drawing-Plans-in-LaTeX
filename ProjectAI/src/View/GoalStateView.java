package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class GoalStateView extends Composite{

	Composite containerGoalState;
	Composite parent;
	
	public GoalStateView(Composite parent, int style) {
		super(parent, style);
		
	}

	public void setLayout() {
		
	}

	public void createContent() {
		containerGoalState=new Composite(this, SWT.ALL);
		containerGoalState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		containerGoalState.setLayout(new FillLayout());
		containerGoalState.setLocation(70,80);
	}
	
	
	
	public Composite getContainerGoalState() {
		return containerGoalState;
	}

	@Override
	protected void checkSubclass() {
	}
}
