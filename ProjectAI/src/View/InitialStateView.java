package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class InitialStateView extends Group{

	Composite containerInitState;
	Composite parent;
	
	public InitialStateView(Composite parent, int style) {
		super(parent, style);
		
	}

	public void setLayout() {
		
	}

	public void createContent() {
		containerInitState=new Composite(this, SWT.ALL);
		containerInitState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		containerInitState.setLayout(new FillLayout());
		containerInitState.setLocation(30,80);
	}
	
	
	
	public Composite getContainerInitState() {
		return containerInitState;
	}

	@Override
	protected void checkSubclass() {
	}
}
