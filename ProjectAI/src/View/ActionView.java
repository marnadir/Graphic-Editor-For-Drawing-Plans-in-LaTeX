package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class ActionView extends Composite {
	
	Composite containerAction;

	public ActionView(Composite parent, int style) {
		super(parent, style);
		this.setLayout();
	}

	public void setLayout() {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 2;
		this.setLayoutData(gridData);
	}
	
	public void creareContent() {
		containerAction = new Composite(this, SWT.BORDER);
		containerAction.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		containerAction.setLayout(new FillLayout());
		containerAction.setLocation(15, 150);
		
	}

	public Composite getContainerAction() {
		return containerAction;
	}

	public void setContainerAction(Composite containerAction) {
		this.containerAction = containerAction;
	}
	
	
	
	
}
