package Contrains;

import org.eclipse.swt.*;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;



public class TestCanvas {

	public static void main(String[] args) {

		Display display = new Display();
		final Shell shell = new Shell(display);
		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setEnabled(false);
		composite.setLayout(new GridLayout());
		composite.setLocation(80, 50);	
		composite.setSize(90, 50);

		Canvas canvas = new Canvas(composite, SWT.ALL);
		canvas.setSize(composite.getSize().x,composite.getSize().y);

		canvas.addPaintListener(getListener());

		canvas.removePaintListener(getListener());
		
		
		 //composite.pack();
		


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


			   // e.gc.drawArc(25, 25, 120, 120, 45, 270);
//				e.gc.drawArc(x, y, width, height, startAngle, arcAngle);
				Rectangle r=new Rectangle(25, 25, 80, 20);
				//e.gc.drawRectangle(r);
				
			    e.gc.drawArc(0, 25,80, 35, 0, 180);
			    drawArrow(e.gc, 60, 0, 82, 44, 8, Math.toRadians(45));
			    
				drawArrowE(e.gc, 80, 10, 40, 10, 10, Math.toRadians(45));

			    
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
	    path.close();
	    gc.fillPath(path);
	    gc.drawPath(path);

	    path.dispose();
	}
	
	public static void drawArrowE(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
	    double theta = Math.atan2(y2 - y1, x2 - x1);
	    double offset = (arrowLength - 2) * Math.cos(arrowAngle);


	    Path path = new Path(gc.getDevice());
	    path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
	    path.lineTo((float)x2, (float)y2);
	    path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
	 
	 
	    gc.drawPath(path);

	    path.dispose();
	}
	
}
