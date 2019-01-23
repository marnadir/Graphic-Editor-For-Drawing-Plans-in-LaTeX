import org.eclipse.swt.*;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Composite example snippet: intercept mouse events (drag a button with the mouse)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

public class TestCanvas {

	public static void main(String[] args) {

		Display display = new Display();
		final Shell shell = new Shell(display);
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setEnabled(false);
		composite.setLayout(new GridLayout());
		composite.setSize(shell.getSize());
		composite.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));

		Canvas canvas = new Canvas(composite, SWT.ALL);

		canvas.addPaintListener(getListener());
		
		canvas.removePaintListener(getListener());

		composite.pack();
		composite.setLocation(60, 60);

		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static PaintListener getListener() {
		PaintListener p;
		
		p=new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				//e.gc.drawLine(20, 20, 500, 500);
				//e.gc.drawRectangle(0, 0, 50, 60);
				Rectangle rect=new Rectangle(0, 0, 60, 30);
				
				e.gc.drawRectangle(rect);

				//e.gc.drawLine(0, 40, 500, 500);
				//e.gc.drawLine(0, 0, 0, 1200);
				//e.gc.drawLine(0, 0, 1200, 0);
				
				

			}
		
	};
	
	return p;
	
	}
}
