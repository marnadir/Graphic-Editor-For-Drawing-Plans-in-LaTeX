package PlanPart;

import java.awt.geom.Arc2D;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import LaTex.LaTexGeneratorNode;

public class LinkCanvas {

	Point p1 = null;
	Point p2 = null;

	Oval oval1 = null;
	Oval oval2 = null;

	Composite c1;
	Composite c2;

	PlanContent canvasContainer;
	Color black;

	String latexCode;

	public LinkCanvas(PlanContent parent) {
		this.canvasContainer = parent;
		black = parent.getDisplay().getSystemColor(SWT.COLOR_BLACK);

		// TODO Auto-generated constructor stub
	}

	public void setLine(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void addlistener(Label l1, Label l2, Button btn) {

		for (int i = 0; i < canvasContainer.getChildren().length; i++) {
			Composite comp = (Composite) canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			if(comp instanceof Oval) {
				//comp.addListener(SWT.Selection, addLink(l1, l2, comp, btn));
				Oval oval=(Oval) comp;
				

			}
			

		}
		canvasContainer.addListener(SWT.MouseDoubleClick, addLink(l1, l2, btn));
	}

/*	public void removelistener(Label l1, Label l2, Button btn) {

		for (int i = 0; i < canvasContainer.getChildren().length; i++) {
			Composite comp = (Composite) canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			if (comp instanceof Oval) {
				comp.removeListener(SWT.MouseDoubleClick, addLink(l1, l2, comp, btn));

			}
		}
	}*/

	public Listener addLink(Label l1, Label l2, Button btn) {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(l1.isDisposed() || l2.isDisposed()) {
					return;
				}
				
				if (!(l1.getText().contains("ordering"))) {
					if (oval1 == null) {
						int d = canvasContainer.getOvalCounter().getListOval().size();
						for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
							Point p = canvasContainer.getOvalCounter().getListOval().get(i).getP();
							if ((p.x - 11 < event.x && event.x < p.x + 11)
									&& (p.y - 11 < event.y && event.y < p.y + 11)) {

								oval1 = canvasContainer.getOvalCounter().getListOval().get(i);
//								c1 = comp;
								l1.setText("First Cond. :" + oval1.getCond());
								l1.pack();

							}
						}
					} else if (oval2 == null) {
						for (int i = 0; i < canvasContainer.getOvalCounter().getListOval().size(); i++) {
							Point p = canvasContainer.getOvalCounter().getListOval().get(i).getP();
							if ((p.x - 11 < event.x && event.x < p.x + 11)
									&& (p.y - 11 < event.y && event.y < p.y + 11)) {
								if (canvasContainer.getOvalCounter().getListOval().get(i).getNode() != oval1
										.getNode()) {
									oval2 = canvasContainer.getOvalCounter().getListOval().get(i);
//									c2 = comp;
									l2.setText("Second Cond. :" + oval2.getCond());
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
		p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				// TODO Auto-generated method stub
//				e.gc.setLineWidth(1);
//				e.gc.setForeground(black);//
//				Path path = new Path(canvasContainer.getDisplay());
//				path.moveTo((float) (p1.x), (float) (p1.y));
//				Point temp1=p1;
//				Point temp2=p2;
//				
//				
//				if (p1.y > p2.y) {
//					Point temp = null;
//					temp = temp1;
//					temp1 = temp2;
//					temp2 = temp;
//					path.quadTo(temp1.x, temp2.y, temp1.x, temp1.y);
//
//
//				} else {
//					path.quadTo(temp2.x, temp1.y, temp2.x, temp2.y);
//				}
//
			    
//				e.gc.drawPath(path);
//			    e.gc.drawArc(0, 50,80, 35, 0, 180);
//			    e.gc.drawRectangle(0, 50, 80, 35/2);
//
				Point p = oval1.getP();
				p1=p;
				p = oval2.getP();
				p2=p;

			    
			    double theta = Math.atan2(p2.y - p1.y, p2.x - p1.x);
			    Transform t=new Transform(canvasContainer.getDisplay());
			    
			    float angle = (float) Math.toDegrees(Math.atan2(p2.y - p1.y, p2.x - p1.x));

			    if(angle < 0){
			        angle += 360;
			    }

				int distance = (int) Math.sqrt((p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y));
				t.rotate((float) angle);
				t.translate((float) (theta*250),- (float) (theta*100));

				e.gc.setTransform(t);

				e.gc.drawArc(p1.x, p1.y-25, distance, 50, 0, 180);
			
				t.rotate(0);
				e.gc.setTransform(t);

			   
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
		LaTexGeneratorNode generator = new LaTexGeneratorNode();
		latexCode = generator.getLatexLinkCodePlan(this);

	}

	public String getLatexCode() {
		return latexCode;
	}

}
