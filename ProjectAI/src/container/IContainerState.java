package container;

import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;
import State.IStateCanvas;
import View.IStateView;

public class IContainerState  extends Composite{

	IStateView iStateView;
	PlanContent planContent;

	
	
	public IContainerState(Composite parent, int style) {
		super(parent, style);
		if(parent instanceof IStateView) {
			this.iStateView=(IStateView) parent;
		}else if(parent instanceof PlanContent){
			this.planContent=(PlanContent) parent;
		}
		// TODO Auto-generated constructor stub
	}


	

	public PlanContent getPlanContent() {
		return planContent;
	}

	public IStateCanvas getCanvas() {
		
		IStateCanvas canvas=null;
		if(getChildren().length > 0) {
			if(getChildren()[0] instanceof IStateCanvas) {
				canvas=(IStateCanvas) getChildren()[0];
			}
		}
		
		return canvas;
	}
	

	public IStateView getiStateView() {
		return iStateView;
	}

	


}
