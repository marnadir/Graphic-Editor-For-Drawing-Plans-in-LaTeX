package DNDAaction;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import Action.Action;
import Action.Node;
import DataTrasfer.MyType;
import GraphPart.GraphContent;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialStateCanvas;
import View.TreeActioDomain;

public class MyDropActionListener extends DropTargetAdapter {
	private Composite parentComposite;
	private DropTarget target;
	private TreeActioDomain treeAction;
	private ArrayList<Action> actionList;
	private GraphContent graphContent;
	private Node node;

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public MyDropActionListener(Composite parentComposite, DropTarget target, TreeActioDomain treeAction) {
		this.parentComposite = parentComposite;
		this.target = target;
		this.treeAction = treeAction;
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
			IState state = null;
			graphContent = (GraphContent) target.getControl();
			Composite comp = new Composite(graphContent, SWT.ALL);
			comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
			comp.setLayout(new FillLayout());
			comp.setLocation(comp.toControl(event.x, event.y));

			if (event.data != null) {
				MyType[] myTypes = (MyType[]) event.data;
				if (myTypes != null) {
					for (int i = 0; i < myTypes.length; i++) {
						switch (myTypes[i].getName()) {
						case "start":
							state = new IState(myTypes[i].getEff());
							InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, state);
							stateCanvas.draw();
							break;

						case "goal":
							state = new IState(myTypes[i].getEff());
							IStateCanvas stateCanvas2 = new GoalStateCanvas(comp, SWT.ALL, state);
							stateCanvas2.draw();
							break;

						default:
							actionList=treeAction.getActionList();
							for (int j = 0; j < actionList.size(); j++) {
								if (myTypes[i].getName().equals(actionList.get(j).getName())) {
									if (myTypes[i].getPrec().equals(actionList.get(j).getPrec())
											&& myTypes[i].getEff().equals(actionList.get(j).getEffect())) {
										action = actionList.get(j);

									}
								}
							}

							node = new Node(comp, SWT.ALL, action);
							node.draw();
							node.pack();
							comp.pack();
							graphContent.getActionInPlan().add(node);
							setNodeID();
							break;
						}

					}

				}
			}

			graphContent.addMoveListener(comp);
		}
	}

	public void setNodeID() {
		int t = 1;
		String ID = getNameAction(node.getAction().getName()) + "-" + t;
		for (int i = 0; i < graphContent.getActionInPlan().size(); i++) {
			if (graphContent.getActionInPlan().get(i).getID() != null) {
				if (graphContent.getActionInPlan().get(i).getID().equals(ID)) {
					t++;
					ID = getNameAction(node.getAction().getName()) + "-" + t;
					;
					i = 0;
				}
			}

		}
		node.setID(ID);
	}

	public String getNameAction(String string) {
		String name[] = string.split("\\(");
		StringBuilder sb = new StringBuilder();
		sb.append(name[0]);
		return sb.toString();
	}

}
