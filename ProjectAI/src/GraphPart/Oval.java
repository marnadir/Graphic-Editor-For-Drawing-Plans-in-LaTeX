package GraphPart;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public class Oval {

	Point p;
	Composite canvasContainer;
	Color red;
	Color black;
	String cond;

	boolean select = false;

	public Oval(Composite parent,String cond) {
		this.canvasContainer = parent;
		red = parent.getDisplay().getSystemColor(SWT.COLOR_RED);
		black = parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		this.cond=cond;
	}

	public void drawO() {
		canvasContainer.addPaintListener(getListener());
		canvasContainer.removePaintListener(getListener());
	}

	public void setLocation(int x, int y) {
		p = new Point(x, y);
	}
	
	public Point getP() {
		return p;
	}

	public PaintListener getListener() {
		PaintListener l;
		
		l=new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				//e.gc=new GC(canvasContainer);
				
				e.gc.setForeground(black);
				e.gc.setLineWidth(2);
				e.gc.drawOval(p.x, p.y, 5, 5);
				
			}
			
		};
		
		return l;
	}
}


