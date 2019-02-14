package Action;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;
import PlanPart.Oval;

public abstract class ICanvasAction extends Canvas {

	Action action;
	Composite parent;
	String latexCode;
	final double PIXEL_MEASUREMNT = 0.026458;
	final double CM_MEASUREMNT = 37.7957517575025;
	ArrayList<Oval> ovalList;


	public ICanvasAction(Composite parent, int style, Action a) {
		super(parent, style);
		this.parent = parent;
		this.action = a;
		ovalList=new ArrayList<>();


	}

	public abstract void draw();

	public void resizeParent() {
		if (action.isShownCond()) {
			int x1 = action.getLengthPrec() + action.getLengthEff() + action.getWidthRect() + 5;
			int y1 = action.getHeightRect() + 40;
			parent.setSize(x1, y1);

		} else {
			int x1 = action.getStandardLengthPrec() + action.getStandardLengthEff() + action.getWidthRect() + 4;
			int y1 = action.getHeightRect() + 40;
			parent.setSize(x1, y1);
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
		if (action.getColorString().equals("cyan")) {
			return getDisplay().getSystemColor(SWT.COLOR_CYAN);
		} else if (action.getColorString().equals("yellow")) {
			return getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		}

		return getDisplay().getSystemColor(SWT.COLOR_WHITE);
	}
	
}
