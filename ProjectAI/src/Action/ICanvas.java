package Action;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;
import PlanPart.Oval;
/**
 * Represents the abstract class for the graphic representation of an action.
 * @author nadir
 *
 */
public abstract class ICanvas extends Canvas {

	Action action;
	Composite parent;
	String latexCode;
	final double PIXEL_MEASUREMNT = 0.026458;
	final double CM_MEASUREMNT = 37.7957517575025;
	ArrayList<Oval> ovalList;


	public ICanvas(Composite parent, int style, Action a) {
		super(parent, style);
		this.parent = parent;
		this.action = a;
		ovalList=new ArrayList<>();


	}

	public abstract void draw();

	public void resizeParent() {
		if (action.isShownCond()) {
			double x1 = action.getLengthPrec() + action.getLengthEff() + action.getWidthRect()+2;
			if(action.getPrec().size()==0 && action.getEffect().size()==0) {
				x1=x1+5;
			}
			double y1 = action.getHeightRect() + 40;
			parent.setSize((int)x1,(int) y1);

		} else {
			double x1 ;
			x1=action.getStandardLengthPrec() + action.getStandardLengthEff() + action.getWidthRect()+2;
			if(action.getPrec().size()==0 && action.getEffect().size()==0) {
				x1=x1+5;
			}
			double y1 = action.getHeightRect() + 40;
			parent.setSize((int)x1, (int)y1);
		}
	}

	public void addOval(Action action, String cond, int x, int y) {
		if (parent.getParent() instanceof PlanContent) {
			PlanContent graphContent = (PlanContent) parent.getParent();
			for (Oval oval : ovalList) {
				if (oval.getCond().equals(cond)) {
					if (oval.getP().x != x || oval.getP().y != y) {
						oval.setLocation(x, y);
						return;
					}
					return;
				}
			}
			Oval oval = new Oval(graphContent, cond, this);
			oval.setLocation(x, y);
			ovalList.add(oval);
			graphContent.getOvalCounter().addA(oval);
		}

	}

	public void clearDisplay() {
		if (this != null) {
			this.dispose();
		}
	}

	public Action getAction() {
		return this.action;
	}

	public ArrayList<Oval> getOvalList() {
		return ovalList;
	}

	public void setOvalList(ArrayList<Oval> ovalList) {
		this.ovalList = ovalList;
	}

	public Color getColorSWT() {
		if (action.getColorString().equalsIgnoreCase("cyan")) {
			return getDisplay().getSystemColor(SWT.COLOR_CYAN);
		} else if (action.getColorString().equalsIgnoreCase("yellow")) {
			return getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		}

		return getDisplay().getSystemColor(SWT.COLOR_WHITE);
	}
	
	public Composite getContentCanvas() {
		Composite new_name = null; 
		
		if (getParent().getParent().getParent().getParent().getParent()  instanceof Composite) {
			 new_name = (Composite) getParent().getParent().getParent().getParent().getParent();
			
		}
		return new_name;
	}
	
	
}
