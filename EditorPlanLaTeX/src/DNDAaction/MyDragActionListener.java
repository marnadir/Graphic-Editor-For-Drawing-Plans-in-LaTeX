package DNDAaction;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

import Action.Action;
import Action.ActionDomainCanvas;
import dataTrasfer.MyType;

/**
 * extended class of DragSourceAdapter,which is used to drag an action from the domain view.
 * @see MyDropActionListener
 * @author nadir
 * */

public class MyDragActionListener extends DragSourceAdapter {
    private DragSource source;
	
    public MyDragActionListener( DragSource source) {
        this.source = source;
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
		if (source.getControl() instanceof ActionDomainCanvas) {
			ActionDomainCanvas canvasAction = (ActionDomainCanvas) source.getControl();
			Action action = canvasAction.getAction();
			MyType myType1 = new MyType();
			myType1.setName(action.getName());
			myType1.setPrec(action.getPrec());
			myType1.setEff(action.getEffect());
			event.data = new MyType[] { myType1 };
		}

	}
}
