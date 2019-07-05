package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;

import container.ContainerInitialState;
/**
 * View which contains the initial state.
 * @author nadir
 * */
public class InitialStateView extends IStateView{

	
	
	public InitialStateView(Composite parent, int style) {
		super(parent, style);
	
		//setLayout();
	}

	public void setLayout() {
		setLayoutData(new RowData(200, 200));
	}

	public void createContent() {
		this.containerState=new ContainerInitialState(this, SWT.ALL);
		this.containerState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
		this.containerState.setLayout(new FillLayout());
		this.containerState.setLocation(30,30);
	}

	
	

	@Override
	protected void checkSubclass() {
	}
}
