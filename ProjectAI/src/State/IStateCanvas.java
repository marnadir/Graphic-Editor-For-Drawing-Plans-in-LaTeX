package State;


import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import DNDstate.MyDragStateListener;
import DataTrasfer.MyTransfer;
import Menu.MenuContentState;
import PlanPart.PlanContent;
import PlanPart.Oval;

public abstract class IStateCanvas extends Canvas  {

	/**
	 * 
	 */

	IState state;
	Composite contentCanvas;
	Composite parent;
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
		int numCond = state.getConds().size();
		state.setLenIn(numCond*30);
		this.addMenuDetectListener(new MenuContentState(this));
		resizeParent();
		
	}

	public void resizeParent() {
		if(state.isShownCond()) {
			int x1;
			if(state.isText()) {
				 x1=(int)state.getLengthCond()+27;

			}else {
				 x1=(int)state.getLengthCond()+6;

			}
			int y1=state.getLenIn()+4;
			parent.setSize(x1,y1);
			
		}else {
			int x1;
			if(state.isText()) {
				x1=state.getStandardLength()+22;
			}else {
				x1=state.getStandardLength()+6;
			}
			int y1=state.getLenIn()+4;
			parent.setSize(x1,y1);

		}
	}
	
	
	public IState getState() {
		return state;
	}



	public void clear() {
//		canvasSo.redraw();
//		canvasSo.layout();
		dispose();
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
	
		if(parent.getParent() instanceof PlanContent) {
			PlanContent graphContent=(PlanContent) parent.getParent();
			Oval oval=new Oval(this,cond);
			oval.setLocation(x, y);
			graphContent.getOvalCounter().addSt(oval);
		}
	}
	



	
	


	
	
}
