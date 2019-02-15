package DNDstate;



import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import DataTrasfer.MyType;
import PlanPart.PlanContent;
import State.GoalState;
import State.GoalStateCanvas;
import State.IStateCanvas;
import State.InitialState;
import State.InitialStateCanvas;
import View.DomainView;
import View.PlanView;

public class MyDropStateListener extends DropTargetAdapter {
	private Composite parentComposite;
	private DropTarget target;


	private PlanContent graphContent;
	private DomainView domainView;
	

	/**
	 * @param parentComposite - the composite that holds all pictures
	 * @param target          - the drop target
	 */
	public MyDropStateListener(Composite parentComposite, DropTarget target,DomainView domain) {
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

		
			InitialState inState=null;
			GoalState goalState=null;
			
			graphContent = (PlanContent) target.getControl();
		

			if (event.data != null) {
				MyType[] myTypes = (MyType[]) event.data;
				if (myTypes != null) {
					for (int i = 0; i < myTypes.length; i++) {
						switch (myTypes[i].getName()) {
						case "start":
							
							if (graphContent.getInitialStateCanvas()==null) {
								inState = new InitialState(myTypes[i].getEff());
								InitialState initialState=(InitialState)domainView.getInitialStateCanvas().getState();
								inState.copyAttribute(initialState);

							}
							break;

						case "goal":
							if(graphContent.getGoalStateCanvas()==null){
								goalState = new GoalState(myTypes[i].getEff());
								GoalState goal=(GoalState)domainView.getGoalStateCanvas().getState();
								goalState.copyAttribute(goal);

							}
						
							break;

					
						}
						
					}
					if (inState != null && goalState != null) {
						if (graphContent.getParent() instanceof PlanView) {
							PlanView planView = (PlanView) graphContent.getParent();
							if (planView.isShowConditionSelecte()) {

								inState.setShownCond(true);
								goalState.setShownCond(true);
							}
						}

						Composite comp = new Composite(graphContent, SWT.ALL);
						comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
						comp.setLayout(new FillLayout());
						comp.setLocation(comp.toControl(event.x, event.y));
						
						
						InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, inState);
						stateCanvas.draw();
						graphContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);

						
						
						Composite comp2 = new Composite(graphContent, SWT.ALL);
						comp2.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
						comp2.setLayout(new FillLayout());
						comp2.setLocation(comp2.toControl(event.x, event.y));
						int x=graphContent.getClientArea().width;
						x=x-(comp.getLocation().x*2);
						comp2.setLocation(comp.getLocation().x+x, comp.getLocation().y);
						if(!goalState.isText()) {
							goalState.setIsText(true);
							goalState.setText("goal");
						}
						IStateCanvas stateCanvas2 = new GoalStateCanvas(comp2, SWT.ALL, goalState);
						stateCanvas2.draw();
						graphContent.setGoalStateCanvas((GoalStateCanvas)stateCanvas2);

						
					}else {
						if(inState!=null) {
							if (graphContent.getParent() instanceof PlanView) {
								PlanView planView = (PlanView) graphContent.getParent();
								if (planView.isShowConditionSelecte()) {

									inState.setShownCond(true);
								}
							}
							
							
							Composite comp = new Composite(graphContent, SWT.DRAW_TRANSPARENT);
							comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
							comp.setLayout(new FillLayout());
							comp.setLocation(comp.toControl(event.x, event.y));
							
							
							InitialStateCanvas stateCanvas = new InitialStateCanvas(comp, SWT.ALL, inState);
							stateCanvas.draw();
							graphContent.setInitialStateCanvas((InitialStateCanvas) stateCanvas);
							graphContent.addMoveListener(comp);

							
						}else if(goalState!=null) {
							
							
							if (graphContent.getParent() instanceof PlanView) {
								PlanView planView = (PlanView) graphContent.getParent();
								if (planView.isShowConditionSelecte()) {

									goalState.setShownCond(true);
								}
							}
							
							Composite comp = new Composite(graphContent, SWT.ALL);
							comp.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
							comp.setLayout(new FillLayout());
							comp.setLocation(comp.toControl(event.x, event.y));
							
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
