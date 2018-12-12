package GraphPart;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import logic.Action;

public class Node extends Canvas {

	Action action;
	Composite parent;
	Composite comp;
	
	public Node(Composite parent, int style) {
		super(parent, style);
		this.parent=parent;
	
		// TODO Auto-generated constructor stub
	}



	
	public void draw(String string,int x,int y,boolean value) {
		//this.setFocus();
		
		
		this.addPaintListener(new PaintListener() {

			Point p=getDisplay().getCursorLocation();
			@Override
			public void paintControl(PaintEvent e) {
			
				if(value) {
					e.gc.drawString(string, x, y);
				}else {
				
					e.gc.drawString(string,x,y);
					//move();

				}
				
				
			}
		});

		this.pack();
		
	}
	
}
