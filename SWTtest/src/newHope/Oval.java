package newHope;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import Hope.Controller;

public class Oval {

	Point p;
	Composite canvasContainer;
	Color red;
	Color black;
	Controller controller;
	PaintListener paint;
	boolean drawed=false;

	boolean select = false;

	public Oval(Composite parent, int x, int y) {
		p = new Point(x, y);
		this.canvasContainer = parent;
		red = parent.getDisplay().getSystemColor(SWT.COLOR_RED);
		black = parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);
	}

	void drawO() {
		canvasContainer.addPaintListener(getListener());
		//canvasContainer.removePaintListener(getListener());
	}

	public Point getP() {
		return p;
	}
	


	public PaintListener getListener() {
		
		
		paint=new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				
				
				//e.gc=new GC(canvasContainer);
				e.gc.setForeground(black);
				e.gc.setLineWidth(0);
				e.gc.drawOval(p.x, p.y, 10, 10);
			
				//removePaint();
			}
			
		};
		
		return paint;
	}
}
