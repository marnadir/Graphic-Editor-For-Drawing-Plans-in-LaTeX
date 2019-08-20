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
/**
 * object which represents the logical part of the ordering constrain.
 * @author nadir
 * */
public class OrderConstrain{
 
	public void setNod1(Node nod1) {
		this.nod1 = nod1;
	}

	public void setNod2(Node nod2) {
		this.nod2 = nod2;
	}

	PlanContent planContent;
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
	
	OrderConstrainCanvas constrainCanvas;

	
	public OrderConstrain(Composite parent) {
		this.planContent=(PlanContent)parent.getParent();
		this.parent=parent;

		//parent.pack();
	}

	public void addlistener(boolean isCon, Label l2) {

		for (int i = 0; i < planContent.getChildren().length; i++) {
			if (!(planContent.getChildren()[i] instanceof Button)) {
				Composite comp = (Composite) planContent.getChildren()[i];
				comp.setEnabled(true);
				if (comp.getChildren().length > 0) {
					comp.getChildren()[0].addListener(SWT.MouseDoubleClick, addOrdCond(isCon, l2));

				}
			}

		}
		

		
	}
	
	
	public Listener addOrdCond(boolean isConstrain,Label l2) {
		Listener l;
		l = new Listener() {

			@Override
			public void handleEvent(Event event) {
				
				Point point=((Control) event.widget).getParent().getLocation();
				
				if (isConstrain) {
					if (nod1 == null) {
						for (int i = 0; i < planContent.getActionInPlan().size(); i++) {
							if ((planContent.getActionInPlan().get(i).getParent().getLocation().equals(point))) {

								nod1 = planContent.getActionInPlan().get(i);
								//c1=nod1.getParent();
								l2.setText(nod1.getAction().getName() + "<" + "null");
								l2.setText(nod1.getID() + "<" + "null");

								l2.pack();
								break;

							}
						}
					} else if (nod2 == null) {
						for (int i = 0; i < planContent.getActionInPlan().size(); i++) {


							if ((planContent.getActionInPlan().get(i).getParent().getLocation().equals(point))) {
								if(planContent.getActionInPlan().get(i)!=nod1) {
								
									nod2 = planContent.getActionInPlan().get(i);
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
		parent.setSize((int) (90*getScale()),(int) (60*getScale()));	
		parent.setLocation(p1.x+((p2.x-p1.x-parent.getBounds().width)/2), p1.y - 30);


	}

	public Node getCond1() {
		return nod1;
	}

	public Node getCond2() {
		return nod2;
	}
	
	public void generateLatexCode() {
		LaTexGeneratorNode generator=new LaTexGeneratorNode(planContent);
		latexCode=generator.getLatexOrderingCodePlan(this);
	
		
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

	public OrderConstrainCanvas getConstrainCanvas() {
		return constrainCanvas;
	}

	public void setConstrainCanvas(OrderConstrainCanvas constrainCanvas) {
		this.constrainCanvas = constrainCanvas;
	}


	private float getScale() {
		return planContent.getScale();
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
	
}
