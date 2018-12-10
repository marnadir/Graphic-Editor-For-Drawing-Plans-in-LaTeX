package Hope;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;




public class Hope {
 
	public static void main(String[] args) {
		Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());
        shell.setText("Photo Shuffler");
        
        Group g1=new Group(shell, SWT.ALL);
        g1.setText("Group1");
        ContentCanvas content=new ContentCanvas(g1, SWT.ALL);
        content.pack();
        CanvasString  canvas =new CanvasString(content, SWT.ALL);
        canvas.setStringa("ciao");
        canvas.draw("ciao",15,15,true);
		System.out.println(canvas.getLocation().x+" "+canvas.getLocation().y);

        
        
        CTabFolder ctab1=new CTabFolder(shell, SWT.ALL);
        
        CTabItem cItem=new CTabItem(ctab1, SWT.ALL);
        cItem.setText("Item");
        ContentCanvas comp=new ContentCanvas(ctab1, SWT.ALL);
        cItem.setControl(comp);
        
        
        DragSource source =new DragSource(canvas, DND.DROP_NONE);
        source.setTransfer(TextTransfer.getInstance()); // varargs are supported as of 4.7
        source.addDragListener(new MyDragSourceListener(content, source));
        
        DropTarget target = new DropTarget(comp, DND.DROP_NONE);
        target.setTransfer(new Transfer[] { TextTransfer.getInstance() }); // varargs are not yet supported see https://git.eclipse.org/r/#/c/92236         // add a drop listener
        target.addDropListener(new MyDropTargetListener(comp, target));
        
        
        // show the SWT window
		shell.setSize(500, 350);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        // tear down the SWT window
        display.dispose();
	}
	
	
}
