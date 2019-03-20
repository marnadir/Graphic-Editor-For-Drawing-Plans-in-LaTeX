package PlanPart;



import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Action.Node;
import LaTex.LaTexGeneratorNode;

public class OrderCondition{
 
	PlanContent canvasContainer;
	Node nod1=null;
	Node nod2=null;
	Point p1;
	Point p2;
	Point p1D;
	Point p2D;
	Composite c1;
	Composite c2;
	Composite parent;
	String latexCode;

	
	public OrderCondition(Composite parent) {
		this.canvasContainer=(PlanContent)parent.getParent();
		this.parent=parent;

		//parent.pack();
	}

	public void addlistener(Label l1, Label l2) {

		for (int i = 0; i < canvasContainer.getChildren().length; i++) {
			if (!(canvasContainer.getChildren()[i] instanceof Button)) {
				Composite comp = (Composite) canvasContainer.getChildren()[i];
				comp.setEnabled(true);
				if (comp.getChildren().length > 0) {
					comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1, l2, comp));

				}
			}

		}
	}
	
	public void removelistener(Label l1,Label l2,Button btn) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,l2,comp));
			comp.getChildren()[0].removeListener(SWT.MouseDoubleClick, addOrdCond(l1,l2,comp));
		}
	}
	
	public Listener addOrdCond(Label l1,Label l2, Composite comp) {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (l1.getText().contains("ordering")) {
					if (nod1 == null) {
						for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {

							canvasContainer.getActionInPlan().get(i).getClientArea();

							if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x, event.y))) {
								c1 = comp;

								nod1 = canvasContainer.getActionInPlan().get(i);
								l2.setText(nod1.getAction().getName() + "<" + ".....");
								l2.pack();
								break;

							}
						}
					} else if (nod2 == null) {
						for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {

							canvasContainer.getActionInPlan().get(i).getClientArea();

							if ((canvasContainer.getActionInPlan().get(i).getClientArea().contains(event.x, event.y))) {
								if(canvasContainer.getActionInPlan().get(i)!=nod1) {
									c2 = comp;
									nod2 = canvasContainer.getActionInPlan().get(i);
									l2.setText(nod1.getAction().getName() + "<" + nod2.getAction().getName());
									l2.pack();
								}
							

							}
						}
					}
				}

			}
		};

		return l;
	}
	
	public void drawOrder() {

		Point p = new Point(nod1.getBounds().x + nod1.getBounds().width, nod1.getBounds().y - 20);
		p1 = c1.getParent().toControl(c1.toDisplay(p.x, p.y));
		p = new Point(nod1.getBounds().x + nod1.getBounds().width, nod1.getBounds().y);

		p1D = c1.getParent().getLocation();
		


		p = new Point(nod2.getBounds().x, nod2.getBounds().y - 20);
		p2 = c2.getParent().toControl(c2.toDisplay(p.x, p.y));
		p = new Point(nod2.getBounds().x, nod2.getBounds().y);

		p2D = c2.getParent().toControl(c2.toDisplay(p.x, p.y));
		
		
		parent.setSize(90,60);
		
		parent.setLocation(p1.x+((p2.x-p1.x-parent.getBounds().width)/2), p1.y - 30);

		
		Contrain c=new Contrain(parent, SWT.ALL);
		c.draw();
		c.pack();
		c.setSize(parent.getSize().x,parent.getSize().y);

	}

	public Node getCond1() {
		return nod1;
	}

	public Node getCond2() {
		return nod2;
	}
	
	public void generateLatexCode() {
		LaTexGeneratorNode generator=new LaTexGeneratorNode();
		latexCode=generator.getLatexOrderCodePlan(this);
	
		
	}

	public String getLatexCode() {
		return latexCode;
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public Point getP1D() {
		return p1D;
	}

	public Point getP2D() {
		return p2D;
	}
	
	
	
}
