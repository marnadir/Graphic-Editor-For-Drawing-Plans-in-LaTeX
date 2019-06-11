package View;

import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import Menu.MenuContentState;
import State.IContainerState;
import State.IStateCanvas;

public abstract class IStateView extends Group {

	protected IContainerState containerState;
	
	public IStateView(Composite parent, int style) {
		super(parent, style);
		this.addMenuDetectListener(new MenuContentState(this));
	}

	
	public void setLayout() {
		setLayoutData(new RowData(200, 200));
	}
	
	public abstract void createContent();
	
	
	
	public IContainerState getContainerState() {
		return containerState;
	}


	public void setContainerState(IContainerState containerState) {
		this.containerState = containerState;
	}

	public IStateCanvas getIstateCanvas() {
		
		IStateCanvas canvas=null;
		
		if(containerState.getChildren()[0] instanceof IStateCanvas) {
			canvas=(IStateCanvas) containerState.getChildren()[0];
		}
		return canvas;
	}
	

	@Override
	protected void checkSubclass() {
	}
}
