package NewHopeMikealson;

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

	
	
	
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
