
import org.eclipse.swt.*;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import Hope.ContentCanvas;
import Hope.Node;
import sun.management.GarbageCollectionNotifInfoCompositeData;

/*
 * Composite example snippet: intercept mouse events (drag a button with the mouse)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

public class Move2 {
	public static void main(String[] args) {
		Display display = new Display();
		

		final Shell shell = new Shell(display);
        shell.setLayout(new FillLayout());

		
        CTabFolder ctab1=new CTabFolder(shell, SWT.ALL);
        CTabItem cItem=new CTabItem(ctab1, SWT.ALL);
        cItem.setText("Item");

        ContentCanvas content = new ContentCanvas(ctab1, SWT.ALL);
		content.setSize(shell.getSize().x, shell.getSize().y);
        
        cItem.setControl(content);
        


		Composite comp = new Composite(content, SWT.ALL);
		comp.setEnabled(false);
		comp.setLayout(new GridLayout());
		comp.setSize(shell.getSize());
		comp.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));


		Node node=new Node(comp, SWT.ALL);
		node.draw("ciao", 0, 0, false);

		comp.pack();
		//composite.setLocation(60, 60);

		content.addlistener(comp);
		
		
		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}