package Hope;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

public class CanvasString extends Canvas{

	String stringa;
	Composite parent;
	Composite comp;
	
	
	
	public CanvasString(Composite parent, int style) {
		super(parent, style);
		this.parent=parent;
	
		// TODO Auto-generated constructor stub
	}

	public String getStringa() {
		return stringa;
	}

	public void setStringa(String stringa) {
		this.stringa = stringa;
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
					move();

				}
				
				
			}
		});

		this.pack();
		
	}
	
	
	public void move() {
		this.addDragDetectListener(new DragDetectListener() {
			
			@Override
			public void dragDetected(DragDetectEvent e) {
				
			}
		});
	}
	
}
