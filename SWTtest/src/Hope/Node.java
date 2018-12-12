package Hope;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

public class Node extends Canvas{

	String stringa;
	Composite parent;
	Composite comp;
	
	
	
	public Node(Composite parent, int style) {
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
					//move();

				}
				
				
			}
		});

		this.pack();
		
	}
	
//	public void move() {
//		final Point[] offset = new Point[1];
//		Listener listener = event -> {
//			switch (event.type) {
//			case SWT.MouseDown:
//				Rectangle rect = this.getBounds();
////				int x=rect.x+event.x;
////				int y=rect.y+event.y;
//				
//				int x=event.x;
//				int y=event.y;
//
//				
//				//controlla se e al interno e non esce fuori
//				if (rect.contains(x, y)) {
//					Point pt1 = this.toDisplay(30, 30);
//					Point pt2 = parent.toDisplay(x, y);
//					offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
//				}
//				break;
//			case SWT.MouseMove:
//				if (offset[0] != null) {
//					Point pt = offset[0];
//		            this.setBounds(pt.x, pt.y , this.getBounds().width, this.getBounds().height);
//					//this.setLocation(event.x - pt.x, event.y - pt.y);
//				}
//				break;
//			case SWT.MouseUp:
//				offset[0] = null;
//				break;
//			}
//		};
//		
//        parent.addListener(SWT.MouseDown, listener);
//        parent.addListener(SWT.MouseUp, listener);
//
//        parent.addListener(SWT.MouseMove, listener);
//		
//
//			
//	}
//	
//	public void action(int x,int y) {
//		this.setBounds(x, y, this.getBounds().width, this.getBounds().height);
//	}
//	
}
