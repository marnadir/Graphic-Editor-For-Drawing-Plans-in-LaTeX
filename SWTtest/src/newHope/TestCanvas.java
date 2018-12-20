package newHope;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;



public class TestCanvas {

public static void main(String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	Composite composite = new Composite (shell, SWT.NO_REDRAW_RESIZE | SWT.DOUBLE_BUFFERED);
//	GridLayout layout = new GridLayout();
//	layout.marginHeight = 0;
//	layout.marginWidth = 0;
//	composite.setLayout(layout);
	composite.setLayout(new FillLayout());
	
//	Canvas container = new Canvas(composite, SWT.ALL);
//	container.setLayout(new FillLayout());

//	Controller controller=new Controller(container);
	//OvalCounter counter=new OvalCounter();

//	LineCanvas lineConstructer=new LineCanvas(container,counter);
//	lineConstructer.addlistener();
	
	Oval canvas1=new Oval(composite,20, 20);
	canvas1.drawO();
	
	
//	Oval canvas3 =new Oval(container,50, 50);
//	canvas3.drawO();
//	
//	Oval canvas2 =new Oval(container,200, 40);
//	canvas2.drawO();
//	
	
	
	
	
//	Display.getDefault().timerExec(100, new Runnable() {
//	    @Override
//	    public void run() {
//	      //composite.redraw();
//	      //container.redraw();
//	      // Run again - TODO add logic to stop after correct number of moves
//	      Display.getDefault().timerExec(100, this);
//	    }
//	   });
	
	
	
	
	
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}

