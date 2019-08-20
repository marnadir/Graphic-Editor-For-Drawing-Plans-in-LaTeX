package PlanPart;




import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import Action.Node;
import Menu.MenuConstrain;

/**
 * Class extends of Canvas which implement the graphic representation of the ordering constrains.
 * @author nadir
 * */
public class OrderConstrainCanvas extends Canvas{

	public static Composite parent;
	private OrderConstrain orderConstrain;
	int initialFontSize = -1;
    private  Font  font;
	public static float  scale = 1;
	PlanContent planContent;


	
	public OrderConstrainCanvas(Composite parent, int style,OrderConstrain orderConstrain) {
		super(parent, style);
		OrderConstrainCanvas.parent=parent;
		this.orderConstrain=orderConstrain;
	}
	//have to be improved, working just with constant, should be dynamically
	public void draw() {
		this.addPaintListener(getListener());
		orderConstrain.setConstrainCanvas(this);
		addMenuDetectListener(new MenuConstrain(this));
		if(parent.getParent() instanceof PlanContent) {
			planContent=(PlanContent)parent.getParent();
			scale=planContent.getScale();
		}
		this.addMouseWheelListener(getMouseListener());

		
	}
	
	private MouseWheelListener getMouseListener() {

		MouseWheelListener listener = new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				if(planContent !=null) {
					scale=planContent.getScale();

				}
				if (e.count > 0)
					scale += .2f;
				else
					scale -= .2f;

				if (scale > 1.2) {
					scale = 1.2f;
				}
				if (scale < 0.6) {
					scale = 0.6f;
				}
				scale = Math.max(scale, 0);
				if(planContent!=null) {
					 planContent.setScale(scale);
			            if( planContent.getInitialStateCanvas() != null) {
				            planContent.getInitialStateCanvas().redraw();

			            }
			            for(Node node:planContent.getActionInPlan()) {
			            	node.redraw(); 	
			            }
			            if( planContent.getInitialStateCanvas() != null) {
				            planContent.getInitialStateCanvas().redraw();

			            }
			            
				}
				
		        
		            

				redraw();

			}
		};

		return listener;
	}
	
	public PaintListener getListener() {
		PaintListener p;
		

		p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				scale=planContent.getScale();

				Font tempFont = new Font(parent.getDisplay(), "Arabic Transparent", 6, SWT.NORMAL);
	            FontData data = tempFont.getFontData()[0];
	            if (initialFontSize == -1)
	                initialFontSize = tempFont.getFontData()[0].getHeight();
	            else
	            {
	                if(font != null && !font.isDisposed())
	                    font.dispose();

	                data.setHeight((int)(initialFontSize * scale));

	                font = new Font(getDisplay(), data);

	                e.gc.setFont(font);
	            }
				
			    e.gc.drawArc(0, (int) (25*scale),(int) (80*scale), (int) (35*scale), 0, 180);
			    drawArrow(e.gc,(int)  (60*scale), 0, (int)  (82*scale), (int)  (44*scale), (int)  (8*scale), Math.toRadians(45));    
				drawArrowE(e.gc, (int) (80*scale), (int) (10*scale), (int) (40*scale), (int) (10*scale),(int) (10*scale), Math.toRadians(45));
			    
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
