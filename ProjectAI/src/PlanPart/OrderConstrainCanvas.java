package PlanPart;



import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import Menu.MenuConstrain;

/**
 * Class extends of Canvas which implement the graphic representation of the ordering constrains.
 * @author nadir
 * */
public class OrderConstrainCanvas extends Canvas{

	public static Composite parent;
	private OrderConstrain orderConstrain;

	
	public OrderConstrainCanvas(Composite parent, int style,OrderConstrain orderConstrain) {
		super(parent, style);
		OrderConstrainCanvas.parent=parent;
		this.orderConstrain=orderConstrain;
	}
	//have to be improved, working just with constant, should be dynamically
	public void draw() {
		addPaintListener(getListener());
		addMenuDetectListener(new MenuConstrain(this));
		
	}
	
	

	
	public static PaintListener getListener() {
		PaintListener p;
		

		p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

			    e.gc.drawArc(0, 25,80, 35, 0, 180);
			    drawArrow(e.gc, 60, 0, 82, 44, 8, Math.toRadians(45));    
				drawArrowE(e.gc, 80, 10, 40, 10, 10, Math.toRadians(45));
			    
			}

		};

		return p;

	}
	
	public static  void drawArrow(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
	    double theta = Math.atan2(y2 - y1, x2 - x1);
	    Path path = new Path(gc.getDevice());
	    path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
	    path.lineTo((float)x2, (float)y2);
	    path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
	    path.close();
	    gc.fillPath(path);
	    gc.drawPath(path);
	    path.dispose();
	}
	
	public static  void drawArrowE(GC gc, int x1, int y1, int x2, int y2, double arrowLength, double arrowAngle) {
	    double theta = Math.atan2(y2 - y1, x2 - x1);
	    Path path = new Path(gc.getDevice());
	    path.moveTo((float)(x2 - arrowLength * Math.cos(theta - arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta - arrowAngle)));
	    path.lineTo((float)x2, (float)y2);
	    path.lineTo((float)(x2 - arrowLength * Math.cos(theta + arrowAngle)), (float)(y2 - arrowLength * Math.sin(theta + arrowAngle)));
	    gc.drawPath(path);
	    path.dispose();
	}

	public void clearDisplay() {
		if (this != null) {
			this.dispose();
		}
	}
	public OrderConstrain getOrderConstrain() {
		return orderConstrain;
	}

	
	
}
