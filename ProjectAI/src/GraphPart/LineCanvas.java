package GraphPart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;


public class LineCanvas  {

	Point p1=null;
	Point p2=null;
	
	Oval oval1=null;
	Oval oval2=null;
	
	Composite c1;
	Composite c2;
	
	GraphContent canvasContainer;
	Color black;
	
	public LineCanvas(GraphContent parent) {
		this.canvasContainer=parent;
	    black=parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);

		// TODO Auto-generated constructor stub
	}
	
	public void setLine(Point p1,Point p2) {
		this.p1=p1;
		this.p2=p2;
	}
	
	public void addlistener() {
//		
//		Control[] child=canvasContainer.getChildren();
//		for(int i=0;i<child.length;i++) {
//		
//		child[i].addListener(SWT.MouseDoubleClick, new Listener() {
		
		
		
		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (oval1 == null) {
					for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
						Point p=canvasContainer.getOvalCounter().getListOval().get(i).getP();
						if ((p.x - 11 < event.x && event.x < p.x + 11) && (p.y - 11 < event.y && event.y < p.y + 11)) {
							
							oval1=canvasContainer.getOvalCounter().getListOval().get(i);
							c1=comp;
							//p1=comp.getParent().toControl(comp.toDisplay(p.x, p.y));
						}	
					}
				}else if(oval2==null) {
					for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
						Point p=canvasContainer.getOvalCounter().getListOval().get(i).getP();
						if ((p.x - 11 < event.x && event.x < p.x + 11) && (p.y - 11 < event.y && event.y < p.y + 11)) {
							
							oval2=canvasContainer.getOvalCounter().getListOval().get(i);
							c2=comp;
							//p2=comp.getParent().toControl(comp.toDisplay(p.x, p.y));

							drawLine();
							
						}	
					}
				}
			}
		});
		
		}
	}

	public void drawLine() {
		canvasContainer.addPaintListener(new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				e.gc.setLineWidth(1);
				e.gc.setForeground(black);
				
				Point p=oval1.getP();
				p1=c1.getParent().toControl(c1.toDisplay(p.x, p.y));
				
				p=oval2.getP();
				p2=c2.getParent().toControl(c2.toDisplay(p.x, p.y));

				System.out.println(p1);
				System.out.println(p2);

				
				//e.gc.drawLine(p1.x, p1.y+5, p2.x, p2.y+5);
				
		        e.gc.drawArc(p1.x, p1.y, 300, 50,0,180);

				
				
			}
		});
		
	}
	

	
}
