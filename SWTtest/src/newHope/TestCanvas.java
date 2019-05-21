package newHope;

import org.eclipse.swt.*;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class TestCanvas {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.BORDER);

		composite.setLayout(new FillLayout());

		Canvas container = new Canvas(composite, SWT.ALL);
		container.setLayout(new FillLayout());

		Canvas canvas=new Canvas(container, SWT.NONE);
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				drawArrow(e.gc, 10, 50, 50, 50, 10, 0.785);
				
			}
		});
	

//		Display.getDefault().timerExec(100, new Runnable() {
//			@Override
//			public void run() {
//				composite.redraw();
//				// Run again - TODO add logic to stop after correct number of moves
//				Display.getDefault().timerExec(100, this);
//			}
//		});

		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void drawArrow(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
		double theta = Math.atan2(y2 - y1, x2 - x1);
		double s=Math.cos(arrowAngle);
		double offset = (arrowLength - 2) * Math.cos(arrowAngle);

		gc.drawLine(x1, y1, (int) (x2 - offset * Math.cos(theta)), (int) (y2 - offset * Math.sin(theta)));

		Path path = new Path(gc.getDevice());
		path.moveTo((float) (x2 - arrowLength * Math.cos(theta - arrowAngle)),
				(float) (y2 - arrowLength * Math.sin(theta - arrowAngle)));
		path.lineTo((float) x2, (float) y2);
		path.lineTo((float) (x2 - arrowLength * Math.cos(theta + arrowAngle)),
				(float) (y2 - arrowLength * Math.sin(theta + arrowAngle)));
		path.close();

		gc.fillPath(path);

		path.dispose();
	}

}