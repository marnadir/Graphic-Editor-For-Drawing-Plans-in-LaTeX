package Hope;


import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Composite;

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
	    		
	    		content.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
	    		canvas.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_BLUE));

	    		
	    		
	    		int x=canvas.getLocation().x-parentComposite.toDisplay(parentComposite.getLocation()).x;
	    		int y=canvas.getLocation().y-parentComposite.toDisplay(parentComposite.getLocation()).y;
	    		
	    		canvas.setLocation(x,y);

	    		String string=(String) event.data;
	
	    		canvas.draw(string,0,0,false);
	    		
	    	}
	    }
	    
	    
	    
	    
}
