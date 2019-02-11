package PlanPart;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Contrain extends Canvas{

	public Contrain(Composite parent, int style) {
		super(parent, style);
		
	}
	
	public void draw() {
		
		addPaintListener(getListener());
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
