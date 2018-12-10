package Hope;

import java.awt.MouseInfo;
import java.awt.PointerInfo;

import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.graphics.Point;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class CanvasString extends Canvas{

	String stringa;
	Composite parent;
	
	
	
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
				}
				
				
			}
		});

		this.pack();
		
	}
	
}
