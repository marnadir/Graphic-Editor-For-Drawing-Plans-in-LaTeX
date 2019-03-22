package PlanPart;




import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Action.Node;
import LaTex.LaTexGeneratorNode;

public class OrderConstrain{
 
	public void setNod1(Node nod1) {
		this.nod1 = nod1;
	}

	public void setNod2(Node nod2) {
		this.nod2 = nod2;
	}

	PlanContent canvasContainer;
	Node nod1=null;
	Node nod2=null;
	Point p1;
	Point p2;
	Composite c1;
	Composite c2;
	Composite parent;
	String latexCode;
	
	String id1;
	String id2;
	
	OrderConstrainCanvas c;

	
	public OrderConstrain(Composite parent) {
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
					comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1, l2));

				}
			}

		}
		

		
	}
	
	public void removelistener(Label l1,Label l2,Button btn) {

		for(int i=0;i<canvasContainer.getChildren().length;i++) {
			Composite comp=(Composite)canvasContainer.getChildren()[i];
			comp.setEnabled(true);
			comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(l1,l2));
			comp.getChildren()[0].removeListener(SWT.MouseDoubleClick, addOrdCond(l1,l2));
			
		}
	}
	
	public Listener addOrdCond(Label l1,Label l2) {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				Point point=((Control) event.widget).getParent().getLocation();
				
				if (l1.getText().contains("ordering")) {
					if (nod1 == null) {
						for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {
							if ((canvasContainer.getActionInPlan().get(i).getParent().getLocation().equals(point))) {

								nod1 = canvasContainer.getActionInPlan().get(i);
								//c1=nod1.getParent();
								l2.setText(nod1.getAction().getName() + "<" + ".....");
								l2.setText(nod1.getID() + "<" + ".....");

								l2.pack();
								break;

							}
						}
					} else if (nod2 == null) {
						for (int i = 0; i < canvasContainer.getActionInPlan().size(); i++) {


							if ((canvasContainer.getActionInPlan().get(i).getParent().getLocation().equals(point))) {
								if(canvasContainer.getActionInPlan().get(i)!=nod1) {
								
									nod2 = canvasContainer.getActionInPlan().get(i);
									//c2=nod2.getParent();
									l2.setText(nod1.getAction().getName() + "<" + nod2.getAction().getName());
									l2.setText(nod1.getID() + "<" + nod2.getID());

									l2.pack();
									break;
								}
							

							}
						}
					}
				}

			}
		};

		return l;
	}
	
	public void setLocationParent() {

		Point p = new Point(nod1.getBounds().x + nod1.getBounds().width, nod1.getBounds().y - 20);
		c1=nod1.getParent();
		p1 = c1.getParent().toControl(c1.toDisplay(p.x, p.y));
		id1=nod1.getID();
		p = new Point(nod2.getBounds().x, nod2.getBounds().y - 20);
		c2=nod2.getParent();
		p2 = c2.getParent().toControl(c2.toDisplay(p.x, p.y));
		id2=nod2.getID();
		parent.setSize(90,60);	
		parent.setLocation(p1.x+((p2.x-p1.x-parent.getBounds().width)/2), p1.y - 30);


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

	public Composite getParent() {
		return parent;
	}


	
	
	
}
