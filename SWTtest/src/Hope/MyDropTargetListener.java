package Hope;


import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
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
		if (target.getControl() instanceof ContentCanvas) {
			
			
			
			ContentCanvas content = (ContentCanvas) target.getControl();
			
			Composite comp=new Composite(content, SWT.ALL);
			comp.setEnabled(false);

			//content.setEnabled(false);

            Node canvas = new Node(comp, SWT.ALL);
			canvas.setLocation(parentComposite.getDisplay().getCursorLocation().x,
					parentComposite.getDisplay().getCursorLocation().y);

			canvas.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
			//canvas.setBackground(parentComposite.getDisplay().getSystemColor(SWT.COLOR_BLUE));

			int x = canvas.getLocation().x - parentComposite.toDisplay(parentComposite.getLocation()).x;
			int y = canvas.getLocation().y - parentComposite.toDisplay(parentComposite.getLocation()).y;

			canvas.setLocation(0, 0);

			String string = (String) event.data;

			canvas.draw(string, 0, 0, false);
			comp.setSize(canvas.getSize().x+100, canvas.getSize().y+100);
			comp.setLocation(x, y);
			
			content.addlistener(comp);
			
	

		}
	}
	    
	    
	    
	    
}
