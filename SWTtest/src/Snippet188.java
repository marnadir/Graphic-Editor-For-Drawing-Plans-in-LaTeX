import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


public class Snippet188 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new GridLayout());
	final ScrolledComposite sc = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	Composite c = new Composite(sc, SWT.NONE);
	c.setLayout(new GridLayout(10, true));
	for (int i = 0 ; i < 300; i++) {
		Button b = new Button(c, SWT.PUSH);
		b.setText("Button "+i);
	}
	sc.setContent(c);
	sc.setExpandHorizontal(true);
	sc.setExpandVertical(true);
	sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	sc.setShowFocusedControl(true);

	shell.setSize(300, 500);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
