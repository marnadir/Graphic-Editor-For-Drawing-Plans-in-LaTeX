package State;


import java.text.DecimalFormat;
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
import LaTex.LaTexGeneratorAction;
import LaTex.LaTexGeneratorState;

public abstract class IStateCanvas extends Canvas {

	String name;
	IState state;
	Composite contentCanvas;
	boolean shownCond = false;
	int lengthCond;
	int standardLength=53;
	boolean defaultValue;
	Composite parent;
	int lenIn;
	String latexCode;
	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;


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
			int x1=(int)lengthCond+6;
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



	public int getLengthCond() {
		return lengthCond;
	}

	public String getLengthCondInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(lengthCond*PIXEL_MEASUREMNT);
		return angleFormated;
	}


	public void setLengthFromCm(double d) {
		this.lengthCond = (int)(d*CM_MEASUREMNT);
	}



	public int getStandardLength() {
		return standardLength;
	}

	public String getStandardLengthInCm() {
		DecimalFormat df = new DecimalFormat("#.00");
	    String angleFormated = df.format(standardLength*PIXEL_MEASUREMNT);
		return angleFormated;
	}

	public void setStandardLengthFromCm(double standardLengthPrec) {
		this.standardLength = (int)(standardLengthPrec*CM_MEASUREMNT);
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


	public void addOval(IState state,String cond,int x, int y) {
		Oval oval=new Oval(this,state,cond);
		oval.setLocation(x, y);
		oval.drawOval();
		if(parent.getParent() instanceof GraphContent) {
			GraphContent graphContent=(GraphContent) parent.getParent();
			graphContent.getOvalCounter().add(oval);
		}
	}
	
	public void generateLatexCode() {
		LaTexGeneratorState generator=new LaTexGeneratorState();

		if(this instanceof GoalStateCanvas) {
			latexCode=generator.getLatexGoalcode(this);
		}else {
			latexCode=generator.getLatexSocode(this);

		}
	
	}
	
	public String getLatexCode() {
		System.out.println(latexCode);
		return latexCode;
	}
	
	
	
	
}
