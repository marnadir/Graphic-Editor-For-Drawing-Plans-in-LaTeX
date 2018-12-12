package logic;

import org.eclipse.swt.widgets.Composite;

public class ContentAction extends Composite {

	public CanvasAction paintAction;
	
	public ContentAction(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public CanvasAction getPaintAction() {
		return paintAction;
	}


	public void setPaintAction(CanvasAction paintAction) {
		this.paintAction = paintAction;
	}
	
	
}
