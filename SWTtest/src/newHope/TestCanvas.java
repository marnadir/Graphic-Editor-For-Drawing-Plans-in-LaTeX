package newHope;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;



public class TestCanvas {

public static void main(String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	Composite composite = new Composite (shell, SWT.BORDER);

	composite.setLayout(new FillLayout());
	
	Canvas container = new Canvas(composite, SWT.ALL);
	container.setLayout(new FillLayout());

	OvalCounter counter=new OvalCounter();

	LinkCanvas lineConstructer=new LinkCanvas(container,counter);
	lineConstructer.addlistener();
	
	Oval canvas1=new Oval(container,50, 200);
	canvas1.drawO();

	Oval canvas2 =new Oval(container,200, 150);
	canvas2.drawO();
		
	counter.add(canvas1);
	counter.add(canvas2);

	
	
	Display.getDefault().timerExec(100, new Runnable() {
	    @Override
	    public void run() {
	      composite.redraw();
	      container.redraw();
	      // Run again - TODO add logic to stop after correct number of moves
	      Display.getDefault().timerExec(100, this);
	    }
	   });
	
	
	
	
	
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}