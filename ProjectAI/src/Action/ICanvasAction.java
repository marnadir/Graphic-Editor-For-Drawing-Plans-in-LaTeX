package Action;

import java.util.ArrayList;


import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import DNDAaction.MyDragActionListener;
import DataTrasfer.MyTransfer;
import GraphPart.GraphContent;
import GraphPart.Oval;

public abstract class ICanvasAction  extends Canvas{


	Action action;
	Composite parent;
	int max;
	boolean shownCond = false;
	boolean shownName = true;
	int widthRect;
	int lengthPrec;
	int lengthEff;
	int heightRect = 40;
	int standardLengthEff=30; //Standard lenght of effect line 
	int standardLengthPrec=30;
	boolean defaultValuePrecLenght=true;
	boolean defaultValueEffLenght=true;
	String actionName;
	

	
	public ICanvasAction(Composite parent, int style,Action a) {
		super(parent, style);
		this.parent=parent;
		this.action=a;
		
	}
	
	
	
	public abstract void draw();
	
	public void resizeParent() {
		if(shownCond) {
			int x1=lengthPrec+lengthEff+widthRect+4;
			int y1=heightRect+40;
			parent.setSize(x1,y1);
			
		}else {
			int x1=standardLengthPrec+standardLengthEff+widthRect+4;
			int y1=heightRect+40;
			parent.setSize(x1,y1);
		}
	}
	
	public void addOval(String name,String cond,int x, int y) {
		Oval oval=new Oval(this,name,cond);
		oval.setLocation(x, y);
		oval.drawOval();
		if(parent.getParent() instanceof GraphContent) {
			GraphContent graphContent=(GraphContent) parent.getParent();
			graphContent.getOvalCounter().add(oval);
		}
	}
	
	public int getLenght(ArrayList<String> conds) {

		int lenght = 0;
		if (conds.size() > 0) {
			String stringa = conds.get(0);
			lenght=stringa.length();
			for (String cond : conds) {
				if (cond.length() > stringa.length()) {
					stringa = cond;
					lenght = cond.length();
				}
			}
		}
		return lenght;
	}
	
	
	
	
	public void clearDisplay() {
		if (this != null) {
			this.dispose();
		}
	}

	public Action getAction() {
		return this.action;
	}
	
	public boolean isShownCond() {
		return shownCond;
	}



	public void setShownCond(boolean shownCond) {
		this.shownCond = shownCond;
	}

	public void negateIsShownCond() {
		shownCond=!shownCond;
	}

	public boolean isShownName() {
		return shownName;
	}



	public void setShownName(boolean shownName) {
		this.shownName = shownName;
	}

	public void negateIsShownName() {
		shownName=!shownName;
	}
	
	

	public int getWidthRect() {
		return widthRect;
	}



	public void setWidthRect(int widthRect) {
		this.widthRect = widthRect;
	}



	public int getLengthPrec() {
		return lengthPrec;
	}



	public void setLengthPrec(int lengthPrec) {
		this.lengthPrec = lengthPrec;
	}



	public int getLengthEff() {
		return lengthEff;
	}



	public void setLengthEff(int lengthEff) {
		this.lengthEff = lengthEff;
	}



	public int getHeightRect() {
		return heightRect;
	}



	public void setHeightRect(int heightRect) {
		this.heightRect = heightRect;
	}



	public int getStandardLengthEff() {
		return standardLengthEff;
	}



	public void setStandardLengthEff(int standardLengthEff) {
		this.standardLengthEff = standardLengthEff;
	}



	public int getStandardLengthPrec() {
		return standardLengthPrec;
	}



	public void setStandardLengthPrec(int standardLengthPrec) {
		this.standardLengthPrec = standardLengthPrec;
	}



	public boolean isDefaultValuePrecLenght() {
		return defaultValuePrecLenght;
	}



	public void setDefaultValuePrecLenght(boolean defaultValuePrecLenght) {
		this.defaultValuePrecLenght = defaultValuePrecLenght;
	}



	public boolean isDefaultValueEffLenght() {
		return defaultValueEffLenght;
	}



	public void setDefaultValueEffLenght(boolean defaultValueEffLenght) {
		this.defaultValueEffLenght = defaultValueEffLenght;
	}

		
	
}

