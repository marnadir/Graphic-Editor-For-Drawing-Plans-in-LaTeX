package newHope;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class LineCanvas  {

	Point p1=null;
	Point p2=null;
	Composite canvasContainer;
	Color black;
	OvalCounter counter;
	
	public LineCanvas(Composite parent,OvalCounter counter) {
		this.canvasContainer=parent;
	    black=parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);
	    this.counter=counter;

		// TODO Auto-generated constructor stub
	}
	
	public void setLine(Point p1,Point p2) {
		this.p1=p1;
		this.p2=p2;
	}
	
	public void addlistener() {
		canvasContainer.addListener(SWT.MouseDoubleClick, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (p1 == null) {
					for (int i = 0; i < counter.getListOval().size(); i++) {
						Point p=counter.getListOval().get(i).getP();
						if ((p.x - 11 < event.x && event.x < p.x + 11) && (p.y - 11 < event.y && event.y < p.y + 11)) {
							p1=p;
						}	
					}
				}else if(p2==null) {
					for (int i = 0; i < counter.getListOval().size(); i++) {
						Point p=counter.getListOval().get(i).getP();
						if ((p.x - 11 < event.x && event.x < p.x + 11) && (p.y - 11 < event.y && event.y < p.y + 11)) {
							p2=p;
							drawLine();
						}	
					}
				}
			}
		});
	}

	public void drawLine() {
		canvasContainer.addPaintListener(new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				e.gc.setLineWidth(1);
				e.gc.setForeground(black);
				
				e.gc.drawLine(p1.x, p1.y+5, p2.x, p2.y+5);
			}
		});
		
	}
	

	
}
