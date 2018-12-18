package newHope;


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

import Hope.Controller;

public class Oval {

	Point p;
	Composite canvasContainer;
	Color red;
	Color black;
	Controller controller;

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
		PaintListener l;
		
		l=new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				//e.gc=new GC(canvasContainer);
				e.gc.setForeground(black);
				e.gc.setLineWidth(0);
				e.gc.drawOval(p.x, p.y, 10, 10);
				System.out.println("Draw:" +p.x+"-"+p.y);
				
			}
			
		};
		
		return l;
	}
}
