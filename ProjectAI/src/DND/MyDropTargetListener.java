package DND;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import DataTrasfer.MyType;
import GraphPart.Node;
import logic.Action;
import logic.CanvasAction;
import logic.ContentAction;

public class MyDropTargetListener extends DropTargetAdapter {
	private Composite parentComposite;
	private DropTarget target;

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public MyDropTargetListener(Composite parentComposite, DropTarget target) {
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

		if (target.getControl() instanceof ContentAction) {

			Action action = null;

			if (event.data != null) {
		          MyType[] myTypes = (MyType[]) event.data;
				if (myTypes != null) {
					for (int i = 0; i < myTypes.length; i++) {
						action = new Action(myTypes[i].getActionName(), myTypes[i].getPrec(), myTypes[i].getEff());
			            }

				}
			}

			ContentAction content = (ContentAction) target.getControl();

			Composite comp = new Composite(content, SWT.ALL);
			comp.setEnabled(false);
			comp.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
			comp.setSize(300, 300);
			
			comp.setLocation(comp.toControl(event.x, event.y));

			

			Node canvas = new Node(comp, SWT.ALL,action);
			canvas.setLocation(parentComposite.getDisplay().getCursorLocation().x,
					parentComposite.getDisplay().getCursorLocation().y);

			 canvas.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_BLUE));

			canvas.setLocation(5, 5);

			canvas.draw();
			comp.setSize(300, 300);

			content.addlistener(comp);
		}
	}

}
