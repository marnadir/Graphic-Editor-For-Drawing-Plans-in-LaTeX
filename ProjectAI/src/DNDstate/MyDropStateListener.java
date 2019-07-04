package DNDstate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import DNDAaction.MyDragActionListener;
import PlanPart.PlanContent;
import View.DomainView;
import View.PlanView;
import container.ContainerGoalState;
import container.ContainerInitialState;
import dataTrasfer.MyType;
import so_goalState.GoalState;
import so_goalState.GoalStateCanvas;
import so_goalState.IStateCanvas;
import so_goalState.InitialState;
import so_goalState.InitialStateCanvas;

/**
 * extended class of DropTargetAdapter,which is used to drop an initial/goal state into plan view.
 * @see MyDragStateListener
 * @author nadir
 * */

public class MyDropStateListener extends DropTargetAdapter {

	@SuppressWarnings("unused")
	private Composite parentComposite;
	private DropTarget target;
	private PlanContent graphContent;
	private DomainView domainView;

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public MyDropStateListener(Composite parentComposite, DropTarget target, DomainView domain) {
		this.parentComposite = parentComposite;
		this.target = target;
		this.domainView = domain;

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

		if (target.getControl() instanceof PlanContent) {

			InitialState inState = null;
			GoalState goalState = null;

			graphContent = (PlanContent) target.getControl();

			if (event.data != null) {
				MyType[] myTypes = (MyType[]) event.data;
				if (myTypes != null) {
					for (int i = 0; i < myTypes.length; i++) {
						switch (myTypes[i].getName()) {
						case "start":

							if (graphContent.getInitialStateCanvas() == null) {
								inState = new InitialState(myTypes[i].getEff());
								InitialState initialState = (InitialState) domainView.getInitialStateCanvas()
										.getState();
								inState.copyAttribute(initialState);

							}
							break;

						case "goal":
							if (graphContent.getGoalStateCanvas() == null) {
								goalState = new GoalState(myTypes[i].getEff());
								GoalState goal = (GoalState) domainView.getGoalStateCanvas().getState();
								goalState.copyAttribute(goal);

							}

							break;

						}

					}

					if (inState != null) {
						if (graphContent.getParent() instanceof PlanView) {
							PlanView planView = (PlanView) graphContent.getParent();
							if (planView.isShowConditionSelecte()) {

								inState.setShownCond(true);
							}
						}

						ContainerInitialState container = new ContainerInitialState(graphContent, SWT.DRAW_TRANSPARENT);
						container.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
						container.setLayout(new FillLayout());
						container.setLocation(container.toControl(event.x, event.y));

						InitialStateCanvas stateCanvas = new InitialStateCanvas(container, SWT.ALL, inState);
						stateCanvas.draw();
						graphContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);
						graphContent.addMoveListener(container);

					} else if (goalState != null) {

						if (graphContent.getParent() instanceof PlanView) {
							PlanView planView = (PlanView) graphContent.getParent();
							if (planView.isShowConditionSelecte()) {

								goalState.setShownCond(true);
							}
						}

						ContainerGoalState containerGoalState = new ContainerGoalState(graphContent, SWT.ALL);
						containerGoalState.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
						containerGoalState.setLayout(new FillLayout());
						containerGoalState.setLocation(containerGoalState.toControl(event.x, event.y));

						IStateCanvas stateCanvas2 = new GoalStateCanvas(containerGoalState, SWT.ALL, goalState);
						stateCanvas2.draw();
						graphContent.setGoalStateCanvas((GoalStateCanvas) stateCanvas2);
						graphContent.addMoveListener(containerGoalState);

					}
				}
			}

		}
	}

}
