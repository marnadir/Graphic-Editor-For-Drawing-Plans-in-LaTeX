package Contrains;

import java.awt.geom.AffineTransform;

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

public class Arrow {

	public final static int ARR_SIZE = 4;
	public static Display display;

	public static void main(String[] args) {

		display = new Display();
		final Shell shell = new Shell(display);
		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setEnabled(false);
		composite.setLayout(new GridLayout());
		composite.setLocation(80, 50);
		composite.setSize(200, 100);

		Canvas canvas = new Canvas(composite, SWT.ALL);
		canvas.setSize(composite.getSize().x, composite.getSize().y);

		canvas.addPaintListener(getListener());

		canvas.removePaintListener(getListener());

		// composite.pack();

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

		p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				drawArrow(e.gc, 80, 20, 50, 20, 10, Math.toRadians(45));

			}

		};

		return p;

	}
	
	public static void drawArrow(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
	    double theta = Math.atan2(y2 - y1, x2 - x1);
	    double offset = (arrowLength - 2) * Math.cos(arrowAngle);


	    Path path = new Path(gc.getDevice());
	    path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
	    path.lineTo((float)x2, (float)y2);
	    path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
//	    path.close();

	    gc.drawPath(path);

	    path.dispose();
	}
	
	
	
	
}