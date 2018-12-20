package GraphPart;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

public class GraphContent extends Canvas {

	private Composite parent;
	private OvalCounter ovalCounter;
	
	public GraphContent(Composite parent, int style) {
		super(parent, style);
		this.parent=parent;
		this.ovalCounter=new OvalCounter();
		// TODO Auto-generated constructor stub
	}








	public OvalCounter getOvalCounter() {
		return ovalCounter;
	}








	public void addlistener(Composite compi) {
	
		final Point[] offset = new Point[1];
		Composite comp=compi;
		Listener listener = event -> {
		    comp.setEnabled(false);
			switch (event.type) {
			case SWT.MouseDown:
					Rectangle rect = comp.getBounds();
					int x = event.x;
					int y = event.y;
					// controlla se e al interno e non esce fuori
					if (rect.contains(x, y)) {
						
						Point pt1 = comp.toDisplay(0, 0);
						Point pt2 = this.toDisplay(x, y);
						offset[0] = new Point(pt2.x - pt1.x, pt2.y - pt1.y);
					}
			
				break;
			case SWT.MouseMove:
				if (offset[0] != null) {
					Point pt = offset[0];
					comp.setLocation(event.x - pt.x, event.y - pt.y);
				}
				break;
			case SWT.MouseUp:
				offset[0] = null;
				comp.setEnabled(true);
				break;
			}
		};
		
        this.addListener(SWT.MouseDown, listener);
        this.addListener(SWT.MouseUp, listener);
        this.addListener(SWT.MouseMove, listener);
	}

	
	
}
