package GraphPart;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import Action.Node;
import State.IStateCanvas;


public class Oval extends Canvas{

	Point p;
	Color red;
	Color black;
	String cond;
	Node node;
	IStateCanvas state;
	boolean select = false;
	Composite container;

	public Oval(Composite container,String cond) {
		super(container, SWT.ALL);
		this.container=container;
		red = container.getDisplay().getSystemColor(SWT.COLOR_RED);
		black = container.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		this.cond=cond;
		if(container instanceof Node ) {
			this.node=(Node)container;
		}else {
			this.state=(IStateCanvas)container;
		}
	}

	public void drawOval() {
		container.addPaintListener(getListener());
		//canvasContainer.removePaintListener(getListener());
	}

	public void fillOval() {
		container.addPaintListener(getListenerFill());

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
				if(!isDisposed()) {
					e.gc.setForeground(black);
					e.gc.setLineWidth(2);
					e.gc.drawOval(p.x, p.y, 5, 5);
				}
			
				
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
	
	
	public void remove() {
		
	}
	
	public String getCond() {
		return cond;
	}

	public Node getNode() {
		return node;
	}



	public IStateCanvas getStateCanvas() {
		return state;
	}

}


