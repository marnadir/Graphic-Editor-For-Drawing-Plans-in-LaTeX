package DNDstate;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

import Action.Action;
import Action.CanvasAction;
import DataTrasfer.MyType;
import State.IState;
import State.IStateCanvas;



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
		}

	}
}
