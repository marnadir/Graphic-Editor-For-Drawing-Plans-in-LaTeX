package DNDAaction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import Action.Action;
import Action.CanvasAction;
import Action.ICanvasAction;
import Action.Node;
import DataTrasfer.MyType;
import GraphPart.GraphContent;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialStateCanvas;
import logic.ContentAction;

public class MyDropActionListener extends DropTargetAdapter {
	private Composite parentComposite;
	private DropTarget target;

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public MyDropActionListener(Composite parentComposite, DropTarget target) {
		this.parentComposite = parentComposite;
		this.target = target;
	}

	public void dragEnter(DropTargetEvent event) {
		if (event.detail == DND.DROP_DEFAULT) {
			event.detail = DND.DROP_COPY;
		}
	}

	public void dragOperationChanged(DropTargetEvent event) {
		if (event.detail == DND.DROP_DEFAULT) {
			event.detail = DND.DROP_COPY;
		}
	}

	@Override
	public void drop(DropTargetEvent event) {

		if (target.getControl() instanceof Composite) {

			Action action = null;
			IState state=null;
			GraphContent content = (GraphContent) target.getControl();
			Composite comp = new Composite(content, SWT.ALL);
			comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
			comp.setLayout(new FillLayout());
			comp.setLocation(comp.toControl(event.x, event.y));
			
			if (event.data != null) {
		          MyType[] myTypes = (MyType[]) event.data;
				if (myTypes != null) {
					for (int i = 0; i < myTypes.length; i++) {
						switch (myTypes[i].getName()) {
						case "SoState":
							state=new IState(myTypes[i].getEff());
							InitialStateCanvas stateCanvas=new InitialStateCanvas(comp, SWT.ALL, state);
							stateCanvas.draw();
							break;
							
						case "GoalState":
							state=new IState(myTypes[i].getEff());
							IStateCanvas stateCanvas2=new GoalStateCanvas(comp, SWT.ALL, state);
							stateCanvas2.draw();
							break;

						default:
							action = new Action(myTypes[i].getName(), myTypes[i].getPrec(), myTypes[i].getEff());
							Node canvas = new Node(comp, SWT.NO_REDRAW_RESIZE | SWT.DOUBLE_BUFFERED,action);
							canvas.draw();
							canvas.pack();
							
							
							break;
						}
						
						
			            }

				}
			}			
			
			content.addlistener(comp);
		}
	}

}
