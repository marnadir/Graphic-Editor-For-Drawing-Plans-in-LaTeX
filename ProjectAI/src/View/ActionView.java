package View;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class ActionView extends Composite {

	public ActionView(Composite parent, int style) {
		super(parent, style);
		this.setLayout();
	}

	public void setLayout() {
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 2;
		this.setLayoutData(gridData);
	}
	
}
