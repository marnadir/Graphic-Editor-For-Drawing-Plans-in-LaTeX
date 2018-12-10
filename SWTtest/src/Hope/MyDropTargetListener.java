package Hope;

import java.awt.event.FocusAdapter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class MyDropTargetListener extends DropTargetAdapter {
	  private Composite parentComposite;
	    private DropTarget target;


	    /**
	     * @param parentComposite - the composite that holds all pictures
	     * @param target - the drop target
	     */
	    public MyDropTargetListener(Composite parentComposite, DropTarget target) {
	        this.parentComposite = parentComposite;
	        this.target = target;
	    }
	    
	    
	    
	    
	    @Override
	    public void drop(DropTargetEvent event) {
	    	if(target.getControl() instanceof ContentCanvas) {
	    		ContentCanvas content=(ContentCanvas) target.getControl();
	    		CanvasString canvas=new CanvasString(content, SWT.ALL);
	    		canvas.setLocation(parentComposite.getDisplay().getCursorLocation().x,parentComposite.getDisplay().getCursorLocation().y);
	    		//Display.getCurrent().getFocusControl().toControl(cursorLocation);
	    		
	    		//parent viene considerato il ctab, devo settare il parent composite a content canvas
	    		
	    		
	    		int x=canvas.getLocation().x-parentComposite.getLocation().x;
	    		int y=canvas.getLocation().y-parentComposite.getLocation().y;
	    		
	    		System.out.println(x+" due "+y);


	    		canvas.setLocation(200,200);

	    		String string=(String) event.data;
	    		//canvas.setStringa(string);
	
	            canvas.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_CYAN));

	    		
	    		canvas.draw(string,0,0,false);
	    		System.out.println(canvas.getLocation().x+" tre "+canvas.getLocation().y);

	    		
	    		//canvas.setSize(parentComposite.getSize().x, parentComposite.getSize().y);
	    		
	    	}
	    }
	    
	    
	    
	    
}
