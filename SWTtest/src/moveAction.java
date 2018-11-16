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

public class moveAction {
	public static void main(String[] args) {
		Display display = new Display();

		final Shell shell = new Shell(display);
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setEnabled(false);
		composite.setLayout(new GridLayout());
		composite.setSize(shell.getSize());

		Label label=new Label(composite, SWT.NONE);
		label.setText("ciao");
		label.setLocation(10, 10);
		Canvas canvas = new Canvas(composite, SWT.ALL);
		
		
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Color color = display.getSystemColor(SWT.COLOR_CYAN);

				//e.gc.drawLine(20, 20, 500, 500);
				//e.gc.drawRectangle(0, 0, 50, 60);
				Rectangle rect=new Rectangle(0, 0, 60, 30);
				
				e.gc.drawRectangle(rect);

				//e.gc.drawLine(0, 40, 500, 500);
				//e.gc.drawLine(0, 0, 0, 1200);
				//e.gc.drawLine(0, 0, 1200, 0);
				
				

			}
		});

		//composite.pack();
		composite.setLocation(60, 60);
		
		final Point[] offset = new Point[1];
		Listener listener = event -> {
			switch (event.type) {
			case SWT.MouseDown:
				Rectangle rect = composite.getBounds();
				if (rect.contains(event.x, event.y)) {
					Point pt1 = composite.toDisplay(0, 0);
					Point pt2 = shell.toDisplay(event.x, event.y);
					offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
				}
				break;
			case SWT.MouseMove:
				if (offset[0] != null) {
					Point pt = offset[0];
					composite.setLocation(event.x - pt.x, event.y - pt.y);
				}
				break;
			case SWT.MouseUp:
				offset[0] = null;
				break;
			}
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.MouseUp, listener);
		shell.addListener(SWT.MouseMove, listener);
		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
