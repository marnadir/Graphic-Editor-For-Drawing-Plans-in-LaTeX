package so_goalState;
/**
 * Represents the super class for the implementation of the graphical part of the initial and goal state.
 * @author nadir
 * */

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import DNDstate.MyDragStateListener;
import Menu.MenuContentState;
import PlanPart.PlanContent;
import container.IContainerState;
import dataTrasfer.MyTransfer;
import PlanPart.Oval;

public abstract class IStateCanvas extends Canvas {



	IState state;
	IContainerState containerState;
	final double PIXEL_MEASUREMNT= 0.026458;
	final double CM_MEASUREMNT= 37.7957517575025;
	ArrayList<Oval> ovalList;


	

	public IStateCanvas(Composite parent, int style, IState state) {
		super(parent, style);
		this.state = state;
		this.containerState = (IContainerState) parent;
		ovalList=new ArrayList<>();
	}



	public void draw() {

		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		int numCond = state.getConds().size();
		state.setLenIn(numCond*30);
		if((state.isDefaultValuePrec())) {
			state.setLengthCond(getLenght(state.getConds())*7+10);

		}

		
		this.addMenuDetectListener(new MenuContentState(containerState));
		resizeParent();
		
	}

	public void resizeParent() {
		if(state.isShownCond()) {
			int x1;
			if(state.isText()) {
				 x1=(int)state.getLengthCond()+22;

			}else {
				 x1=(int)state.getLengthCond()+3;

			}
			int y1=(int) (state.getLenIn()+4);
			containerState.setSize(x1,y1);
			
		}else {
			double x1;
			if(state.isText()) {
				x1=state.getStandardLengthCond()+22;
			}else {
				x1=state.getStandardLengthCond()+3;
			}
			int y1=(int) (state.getLenIn()+4);
			containerState.setSize((int) x1,y1);

		}
	}
	
	
	public IState getState() {
		return state;
	}



	public void clearDisplay() {
		if (this != null) {
			this.dispose();
		}
		state.removeConds();
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
	
	
	
	public void addDNDListener() {
		DragSource source =new DragSource(this, DND.DROP_NONE);
	    source.setTransfer(new Transfer[] { MyTransfer.getInstance() });
	    source.addDragListener(new MyDragStateListener(source,state.getName()));
	}


	public void addOval(IState state,String cond,int x, int y) {
	
		if(containerState.getParent() instanceof PlanContent) {
			PlanContent graphContent=(PlanContent) containerState.getParent();
			for(Oval oval:ovalList) {
				if(oval.getCond().equals(cond)) {
					if(oval.getP().x != x || oval.getP().y != y) {
						oval.setLocation(x, y);
						return;
					}
					return;
				}
			}
			Oval oval=new Oval(graphContent,cond,this);
			oval.setLocation(x, y);	
			ovalList.add(oval);
			graphContent.getOvalCounter().addSt(oval);
		}
	}



	public ArrayList<Oval> getOvalList() {
		return ovalList;
	}



	public void setOvalList(ArrayList<Oval> ovalList) {
		this.ovalList = ovalList;
	}



	public IContainerState getContainerState() {
		return containerState;
	}
	



	
	


	
	
}
