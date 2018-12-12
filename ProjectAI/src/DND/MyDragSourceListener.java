package DND;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.widgets.Composite;

import logic.Action;
import logic.ContentAction;



public class MyDragSourceListener extends DragSourceAdapter {
    private DragSource source;
	
    public MyDragSourceListener( DragSource source) {
        this.source = source;
    }
        
    @Override
    public void dragSetData(DragSourceEvent event) {
    	if(source.getControl() instanceof ContentAction) {
    		ContentAction contentAction=(ContentAction)source.getControl();
    		Action action=contentAction.getPaintAction().getAction();
    		event.data=action;
    	}

    }
}
