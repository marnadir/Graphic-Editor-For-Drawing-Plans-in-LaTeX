package PlanPart;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

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

	public Oval(Composite container,String cond,Composite referenceComposite) {
		super(container, SWT.ALL);
		this.container=container;
		red = container.getDisplay().getSystemColor(SWT.COLOR_RED);
		black = container.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		this.cond=cond;
		if(referenceComposite instanceof Node ) {
			this.node=(Node)referenceComposite;
		}else {
			this.state=(IStateCanvas)referenceComposite;
		}
	}

	public void drawOval() {
		container.addPaintListener(getListener());
		//canvasContainer.removePaintListener(getListener());
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

				if(!isDisposed() && !e.gc.isDisposed()) {
				e.gc.setForeground(black);
					e.gc.setLineWidth(2);
					e.gc.drawOval(getP().x, getP().y, 5, 5);
				}
			
				
			}
			
		};
		
		return l;
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


