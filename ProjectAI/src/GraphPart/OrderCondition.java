package GraphPart;

import javax.xml.crypto.dsig.CanonicalizationMethod;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Action.Node;

public class OrderCondition {
 
	GraphContent canvasContainer;
	Node cond1=null;
	Node cond2=null;
	Composite c1;
	Composite c2;
	
	public OrderCondition(GraphContent parent) {
		this.canvasContainer=parent;
	}
	
	public void addlistener(Label l1) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,comp));
		
		}
	}
	
	public void removelistener(Label l1) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,comp));
			comp.getChildren()[0].removeListener(SWT.MouseDoubleClick, addOrdCond(l1,comp));
		}
	}
	
	public Listener addOrdCond(Label l1,Composite comp) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (cond1 == null) {
					for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {
						
						canvasContainer.getActionInPlan().get(i).getClientArea();
						
						if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x,event.y))){
							c1=comp;

							cond1=canvasContainer.getActionInPlan().get(i);
							l1.setText(cond1.getAction().getName()+"<"+".....");
							l1.pack();
							
						}	
					}
				} else if (cond2 == null) {
					for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {

						canvasContainer.getActionInPlan().get(i).getClientArea();

						if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x, event.y))) {
							c2=comp;

							cond2 = canvasContainer.getActionInPlan().get(i);
							l1.setText(cond1.getAction().getName() + "<" + cond1.getAction().getName());
							l1.pack();

						}
					}
				}
			}
		};
			
			return l;
	}
	
	public void drawOrder() {
		canvasContainer.addPaintListener(new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				e.gc.setLineWidth(1);
				
				Point p=new Point(cond1.getBounds().x+cond1.getBounds().width,cond1.getBounds().y-20);
				Point p1=c1.getParent().toControl(c1.toDisplay(p.x, p.y));
				
				p=new Point(cond2.getBounds().x+cond2.getBounds().width,cond2.getBounds().y-20);
				Point p2=c2.getParent().toControl(c2.toDisplay(p.x, p.y));

	            Path path=new Path(canvasContainer.getDisplay());


			    
			    path.moveTo((float)(p1.x), (float)(p1.y));
			    if(p1.y>p2.y) {
			    	path.quadTo(p1.y, p2.x, p2.x, p2.y);
			    }else {
				    path.quadTo(p2.x, p1.y, p2.x, p2.y);

			    }
				
				e.gc.drawPath(path);

				//e.gc.drawLine(p1.x, p1.y+5, p2.x, p2.y+5);
				
//				Path path=new Path(canvasContainer.getDisplay());
//				path.addArc(p1.x, y, width, height, startAngle, arcAngle);
//				e.gc.drawPath(path);
				
//				double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
//		        
//				
//				
//		        e.gc.drawArc(p1.x, p1.y, 300, 50,0,(int)angle);

				
				
			}
		});
		
	}
	
	
	
	
}
