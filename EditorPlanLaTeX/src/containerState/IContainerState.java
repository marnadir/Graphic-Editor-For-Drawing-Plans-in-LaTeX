package containerState;

import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;
import View.IStateView;
import so_goalState.IStateCanvas;
/**
 * represents containers where initial/goal state are drawn 
 * @author nadir
 * */

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
	}

	public PlanContent getPlanContent() {
		return planContent;
	}


	public IStateCanvas getCanvas() {
		IStateCanvas canvas = null;
		if (getChildren().length > 0) {
			if (getChildren()[0] instanceof IStateCanvas) {
				canvas = (IStateCanvas) getChildren()[0];
			}
		}

		return canvas;
	}

	public IStateView getiStateView() {
		return iStateView;
	}

	


}
