package Hope;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.widgets.Composite;
 

public class MyDragSourceListener extends DragSourceAdapter {
	
	private Composite parentComposite;
    private DragSource source;
	
    public MyDragSourceListener(Composite parentComposite, DragSource source) {
        this.parentComposite = parentComposite;
        this.source = source;
    }
    
   
	@Override
	public void dragStart(DragSourceEvent event) {
		// TODO Auto-generated method stub
		//super.dragStart(event);
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
    	if(source.getControl() instanceof Node) {
    		Node canvas=(Node)source.getControl();
    		String string=canvas.getStringa();
    		event.data=string;
    	}

    }
    
}
