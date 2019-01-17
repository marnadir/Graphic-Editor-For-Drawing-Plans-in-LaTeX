package GraphPart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import LaTex.LaTexGeneratorNode;


public class LinkCanvas  {

	Point p1=null;
	Point p2=null;
	
	Oval oval1=null;
	Oval oval2=null;
	
	Composite c1;
	Composite c2;
	
	GraphContent canvasContainer;
	Color black;
	
	String latexCode;

	
	public LinkCanvas(GraphContent parent) {
		this.canvasContainer=parent;
	    black=parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);

		// TODO Auto-generated constructor stub
	}
	
	public void setLine(Point p1,Point p2) {
		this.p1=p1;
		this.p2=p2;
	}
	
	public void addlistener(Label l1,Label l2, Button btn) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addLink(l1,l2,comp,btn));
		
		}
	}

	public void removelistener(Label l1,Label l2, Button btn) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addLink(l1,l2,comp,btn));
			comp.getChildren()[0].removeListener(SWT.MouseDoubleClick, addLink(l1,l2,comp,btn));
		}
	}
	
	
	
	public Listener addLink(Label l1,Label l2,Composite comp, Button btn) {
		Listener l;
		l=new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(!(l1.getText().contains("ordering"))) {
					if (oval1 == null) {
						int d=canvasContainer.getOvalCounter().getListOval().size();
						for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
							Point p=canvasContainer.getOvalCounter().getListOval().get(i).getP();
							if ((p.x - 11 < event.x && event.x < p.x + 11) && (p.y - 11 < event.y && event.y < p.y + 11)) {
								
								oval1=canvasContainer.getOvalCounter().getListOval().get(i);
								c1=comp;
								l1.setText("First Cond. :"+oval1.getCond());
								l1.pack();
								
							}	
						}
					}else if(oval2==null) {
						for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
							Point p=canvasContainer.getOvalCounter().getListOval().get(i).getP();
							if ((p.x - 11 < event.x && event.x < p.x + 11) && (p.y - 11 < event.y && event.y < p.y + 11)) {
								if(canvasContainer.getOvalCounter().getListOval().get(i).getNode()!=oval1.getNode()) {
									oval2=canvasContainer.getOvalCounter().getListOval().get(i);
									c2=comp;
									l2.setText("Second Cond. :"+oval2.getCond());
									l2.pack();
								}
//								drawLine();
								
							}	
						}
					}
				}
					
			
			}
		};
			
			return l;
	}
	
	public void drawLine() {
		canvasContainer.addPaintListener(getListener());
	}

	public void removeL() {
		canvasContainer.removePaintListener(getListener());
	}
	
	public PaintListener getListener() {
		PaintListener p;
		p=new PaintListener() {
			
			
			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
				e.gc.setLineWidth(1);
				e.gc.setForeground(black);
				
				Point p=oval1.getP();
				p1=c1.getParent().toControl(c1.toDisplay(p.x, p.y));
				
				p=oval2.getP();
				p2=c2.getParent().toControl(c2.toDisplay(p.x, p.y));

				
	            Path path=new Path(canvasContainer.getDisplay());
			    path.moveTo((float)(p1.x), (float)(p1.y));
			    if(p1.y>p2.y) {
			    	path.quadTo(p1.y, p2.x, p2.x, p2.y);
			    }else {
				    path.quadTo(p2.x, p1.y, p2.x, p2.y);

			    }
				
				e.gc.drawPath(path);
							
			}
		
		};
		return p;
	}
	
	public Oval getOval1() {
		return oval1;
	}

	public Oval getOval2() {
		return oval2;
	}
	
	public void generateLatexCode() {
		LaTexGeneratorNode generator=new LaTexGeneratorNode();
		latexCode=generator.getLatexLinkCodePlan(this);
	
		
	}
	
	public String getLatexCode() {
		return latexCode;
	}
	
	
}
