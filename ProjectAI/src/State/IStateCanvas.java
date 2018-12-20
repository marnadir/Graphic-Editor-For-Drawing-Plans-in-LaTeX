package State;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import DNDAaction.MyDragActionListener;
import DNDstate.MyDragStateListener;
import DataTrasfer.MyTransfer;
import GraphPart.GraphContent;
import GraphPart.Oval;

public abstract class IStateCanvas extends Canvas {

	String name;
	IState state;
	Composite contentCanvas;
	boolean shownCond = false;
	int lengthCond;
	int standardLength=30;
	boolean defaultValue;
	Composite parent;
	int lenIn;

	public IStateCanvas(Composite parent, int style, IState state) {
		super(parent, style);
		this.parent=parent;
		this.state = state;
		this.contentCanvas = parent;

		// TODO Auto-generated constructor stub
	}

	

	public void draw() {

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
//		this.setSize(contentCanvas.getSize());
		int numCond = state.getConds().size();
		lenIn=numCond*30;
		this.addMenuDetectListener(new MenuContentState(this));
		resizeParent();
		
	}

	public void resizeParent() {
		if(shownCond) {
			int x1=lengthCond+6;
			int y1=lenIn;
			parent.setSize(x1,y1);
			
		}else {
			int x1=standardLength+6;
			int y1=this.lenIn;
			parent.setSize(x1,y1);
		}
	}
	
	
	public IState getState() {
		return state;
	}



	public void clear() {
//		canvasSo.redraw();
//		canvasSo.layout();
		this.dispose();
		state.removeConds();
	}



	public boolean isShownCond() {
		return shownCond;
	}



	public void setShownCond(boolean shownCond) {
		this.shownCond = shownCond;
	}



	public int getLength() {
		return lengthCond;
	}



	public void setLength(int length) {
		this.lengthCond = length;
	}



	public int getStandardLength() {
		return standardLength;
	}



	public void setStandardLength(int standardLengthPrec) {
		this.standardLength = standardLengthPrec;
	}



	public boolean isDefaultValuePrec() {
		return defaultValue;
	}



	public void setDefaultValue(boolean defaultValuePrec) {
		this.defaultValue = defaultValuePrec;
	}
	
	public void negateIsShownCond() {
		shownCond=!shownCond;
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
	
	public String getName() {
		return name;
	}
	
	public void addDNDListener() {
		DragSource source =new DragSource(this, DND.DROP_NONE);
	    source.setTransfer(new Transfer[] { MyTransfer.getInstance() });
	    source.addDragListener(new MyDragStateListener(source,name));
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
	
	
}
