package DNDstate;


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
import PlanPart.PlanContent;
import State.GoalState;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import View.DomainView;
import View.TreeActioDomain;

public class myDropStateListener extends DropTargetAdapter {
	private Composite parentComposite;
	private DropTarget target;


	private PlanContent graphContent;
	private DomainView domainView;
	

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public myDropStateListener(Composite parentComposite, DropTarget target,DomainView domain) {
		this.parentComposite = parentComposite;
		this.target = target;
		this.domainView=domain;
	
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

			InitialState initialState=null;
			GoalState goalState=null;
			InitialState inState=null;
			GoalState goal=null;
			
			graphContent = (PlanContent) target.getControl();
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
							
							if (graphContent.getInitialStateCanvas()==null) {
								initialState = new InitialState(myTypes[i].getEff());
								inState=(InitialState)domainView.getInitialStateCanvas().getState();
								initialState.copyAttribute(inState);
//								InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, initialState);
//								stateCanvas.draw();
//								
//								graphContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);
							}
							break;

						case "goal":
							if(graphContent.getGoalStateCanvas()==null){
								goalState = new GoalState(myTypes[i].getEff());
								goal=(GoalState)domainView.getGoalStateCanvas().getState();
								goalState.copyAttribute(goal);
//								IStateCanvas stateCanvas2 = new GoalStateCanvas(comp, SWT.ALL, goalState);
//								stateCanvas2.draw();
//								graphContent.setGoalStateCanvas((GoalStateCanvas)stateCanvas2);
							}
						
							break;

					
						}
						
					}
					if(inState!=null && goal!= null) {
						InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, initialState);
						stateCanvas.draw();
						graphContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);

						Composite comp2 = new Composite(graphContent, SWT.ALL);
						comp2.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
						comp2.setLayout(new FillLayout());
						comp2.setLocation(comp2.toControl(event.x, event.y));
						int x=graphContent.getClientArea().width;
						x=x-(comp.getLocation().x*2);
						comp2.setLocation(comp.getLocation().x+x, comp.getLocation().y);
						IStateCanvas stateCanvas2 = new GoalStateCanvas(comp2, SWT.ALL, goalState);
						stateCanvas2.draw();
						graphContent.setGoalStateCanvas((GoalStateCanvas)stateCanvas2);

						
					}else {
						if(initialState!=null) {
							InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, initialState);
							stateCanvas.draw();
							graphContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);
							graphContent.addMoveListener(comp);

							
						}else if(goal!=null) {
							IStateCanvas stateCanvas2 = new GoalStateCanvas(comp, SWT.ALL, goalState);
							stateCanvas2.draw();
							graphContent.setGoalStateCanvas((GoalStateCanvas)stateCanvas2);
							graphContent.addMoveListener(comp);

						}
					}
				}
			}

		}
	}


	
}
