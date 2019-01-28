package GraphPart;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

import Action.Node;
import DNDAaction.MyDropActionListener;
import DataTrasfer.MyTransfer;
import State.GoalState;
import State.GoalStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import View.TreeActioDomain;

public class GraphContent extends Canvas {

	private Composite parent;
	private OvalCounter ovalCounter;
	private ArrayList<Node> actionInPlan;
	private ArrayList<LinkCanvas> link;
	private ArrayList<OrderCondition> ords;
	private InitialStateCanvas initialStateCanvas;
	private GoalStateCanvas goalStateCanvas;
	
	public GraphContent(Composite parent, int style) {
		super(parent, style);
		this.parent=parent;
		this.ovalCounter=new OvalCounter();
		actionInPlan=new ArrayList<>();
		link=new ArrayList<>();
		ords=new ArrayList<>();

		
		// TODO Auto-generated constructor stub
	}

	public OvalCounter getOvalCounter() {
		return ovalCounter;
	}

	public ArrayList<Node> getActionInPlan() {
		return actionInPlan;
	}

	public ArrayList<LinkCanvas> getLink() {
		return link;
	}

	public ArrayList<OrderCondition> getOrds() {
		return ords;
	}


	

	public InitialStateCanvas getInitialStateCanvas() {
		return initialStateCanvas;
	}

	public void setInitialStateCanvas(InitialStateCanvas initialStateCanvas) {
		this.initialStateCanvas = initialStateCanvas;
	}

	public GoalStateCanvas getGoalStateCanvas() {
		return goalStateCanvas;
	}

	public void setGoalStateCanvas(GoalStateCanvas goalStateCanvas) {
		this.goalStateCanvas = goalStateCanvas;
	}

	public void addMoveListener(Composite compi) {
	
		final Point[] offset = new Point[1];
		Composite comp=compi;
		Listener listener = event -> {
		    comp.setEnabled(false);
			switch (event.type) {
			case SWT.MouseDown:
					Rectangle rect = comp.getBounds();
					int x = event.x;
					int y = event.y;
					// controlla se e al interno e non esce fuori
					if (rect.contains(x, y)) {
						
						Point pt1 = comp.toDisplay(0, 0);
						Point pt2 = this.toDisplay(x, y);
						offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
					}
			
				break;
			case SWT.MouseMove:
				if (offset[0] != null) {
					Point pt = offset[0];
					comp.setLocation(event.x - pt.x, event.y - pt.y);
					redraw();
				}
				break;
			case SWT.MouseUp:
				offset[0] = null;
				comp.setEnabled(true);
				break;
			}
		};
		
        this.addListener(SWT.MouseDown, listener);
        this.addListener(SWT.MouseUp, listener);
        this.addListener(SWT.MouseMove, listener);
	}

	public void addDndListener(TreeActioDomain treeAction) {
		DropTarget target = new DropTarget(this, DND.DROP_MOVE | DND.DROP_COPY);
		target.setTransfer(new Transfer[] { MyTransfer.getInstance() });
		target.addDropListener(new MyDropActionListener(parent, target, treeAction));
	}
	
}
