package GraphPart;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import Action.Action;
import State.IState;


public class Oval {

	Point p;
	Composite canvasContainer;
	Color red;
	Color black;
	String cond;
	Action action;
	IState state;
	boolean select = false;

	public Oval(Composite parent,Object a,String cond) {
		this.canvasContainer = parent;
		red = parent.getDisplay().getSystemColor(SWT.COLOR_RED);
		black = parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		this.cond=cond;
		if(a instanceof Action ) {
			this.action=(Action)a;
		}else {
			this.state=(IState)a;
		}
	}

	public void drawOval() {
		canvasContainer.addPaintListener(getListener());
		//canvasContainer.removePaintListener(getListener());
	}

	public void fillOval() {
		canvasContainer.addPaintListener(getListenerFill());

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
	
	public PaintListener getListenerFill() {
		PaintListener l;
		
		l=new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				//e.gc=new GC(canvasContainer);
				
				e.gc.setForeground(red);
				e.gc.setLineWidth(2);
				e.gc.drawOval(p.x, p.y, 5, 5);
				
			}
			
		};
		
		return l;
	}
	
	
	public String getCond() {
		return cond;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public IState getState() {
		return state;
	}

	public void setState(IState state) {
		this.state = state;
	}


	
	
}


