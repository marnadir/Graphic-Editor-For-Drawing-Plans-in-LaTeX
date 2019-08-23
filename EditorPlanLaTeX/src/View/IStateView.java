package View;

import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import Menu.MenuContentState;
import containerState.IContainerState;
import so_goalState.IStateCanvas;
/**
 * Super class  which defines the views for the initial and goal state.
 * @author nadir
 * */
public abstract class IStateView extends Group {

	protected IContainerState containerState;
	DomainView domainView;

	
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
	

	
	
	public DomainView getDomainView() {
		return domainView;
	}


	public void setDomainView(DomainView domainView) {
		this.domainView = domainView;
	}


	@Override
	protected void checkSubclass() {
	}
}
