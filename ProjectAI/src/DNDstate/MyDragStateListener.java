package DNDstate;


import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import DataTrasfer.MyType;
import State.GoalStateCanvas;
import State.IState;
import State.IStateCanvas;
import State.InitialStateCanvas;
import View.GoalStateView;



public class MyDragStateListener extends DragSourceAdapter {
    private DragSource source;
    private String name;
	
    public MyDragStateListener( DragSource source,String name) {
        this.source = source;
        this.name=name;
    }
   
    @Override
    public void dragStart(DragSourceEvent event) {
    
    	if (event.detail == DND.DROP_NONE) {
			if ((event.detail & DND.DROP_COPY) != 0) {
				event.detail = DND.DROP_COPY;
			} else {
				event.detail = DND.DROP_NONE;

			}
		}
    	
    }

    
	@Override
	public void dragSetData(DragSourceEvent event) {
		if (source.getControl() instanceof IStateCanvas) {
			IStateCanvas stateCanvas = (IStateCanvas) source.getControl();
			IState state=(IState) stateCanvas.getState();
			
			MyType myType1 = new MyType();
			myType1.setName(name);
			myType1.setPrec(state.getConds());
			myType1.setEff(state.getConds());
			event.data = new MyType[] { myType1 };

			if (stateCanvas.getState().isText()) {

				Composite parentView = (Composite) stateCanvas.getParent().getParent().getParent();
				Control[] c = parentView.getChildren();
				if (stateCanvas instanceof InitialStateCanvas) {
					for (int i = 0; i < c.length; i++) {
						if (c[i] instanceof GoalStateView) {
							GoalStateView goalStateView = (GoalStateView) c[i];
							if (goalStateView.getContainerGoalState().getChildren().length > 0) {
								GoalStateCanvas canv = (GoalStateCanvas) goalStateView.getContainerGoalState()
										.getChildren()[0];

								if (canv != null) {
									MyType myType2 = new MyType();
									myType2.setName(canv.getState().getName());
									myType2.setPrec(canv.getState().getConds());
									myType2.setEff(canv.getState().getConds());
									event.data = new MyType[] { myType1, myType2 };
								}
							}

						}
					}

				}

			}

		}

	}
}
