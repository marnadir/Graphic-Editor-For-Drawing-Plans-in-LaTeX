package Action;

import java.util.ArrayList;


import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import DNDAaction.MyDragActionListener;
import DataTrasfer.MyTransfer;
import GraphPart.Oval;
import logic.MenuContentAction;

public class CanvasAction  extends ICanvasAction{


//	Action action;
//	Composite parent;
//	int max;
//	boolean shownCond = false;
//	boolean shownName = true;
//	int widthRect;
//	int lengthPrec;
//	int lengthEff;
//	int heightRect = 40;
//	int standardLengthEff=30; //Standard lenght of effect line 
//	int standardLengthPrec=30;
//	boolean defaultValuePrecLenght=true;
//	boolean defaultValueEffLenght=true;
	

	public CanvasAction(Composite parent, int style, Action a) {
		super(parent, style, a);
		// TODO Auto-generated constructor stub
	}


	
	
	
	public void draw() {

		widthRect = action.getName().length() * 12;
		
		//this.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,true));
		//this.setLocation(40,parent.getLocation().y+parent.getSize().y/3);



		this.addPaintListener(new PaintListener() {

			
			@Override
			public void paintControl(PaintEvent e) {

				int numPrec = action.getPrec().size();
				int numEff = action.getEffect().size();
//				int startX = 70 ;
//				int startY = 50;
				

				/*for setting the height of rectangle action, depending on Precs or Effs*/
				max = numPrec;
				if (numEff > max) {
					max = numEff;
				}

				if (max > 1) {
					heightRect = 30 + max * 10;
				}

				if(numPrec==0) {
					standardLengthPrec=0;
				}else {
					standardLengthPrec=40;
				}
				
				if(numEff==0) {
					standardLengthEff=0;
				}else {
					standardLengthEff=40;
				}
				
				
				/* draw precs with their "point"*/
				

				int posY = 30;
				int y=25;
				
				int avergWidth = (int) e.gc.getFontMetrics().getAverageCharacterWidth();

				if(defaultValuePrecLenght) {
					lengthPrec=getLenght(action.getPrec())*avergWidth;
				}
				
				for (int i = 0; i < numPrec; i++) {

					if (shownCond) {
						String string = action.getPrec().get(i);
						e.gc.drawLine(0, posY, (lengthPrec), posY);
						e.gc.drawString(string, 2, posY - 20, false);				
												
					} else {
						e.gc.drawLine(0, posY,  standardLengthPrec, posY);	
					}

					posY = posY + 30;
				}
				

								

				/*Drawing rectangle w/o name*/
				Rectangle rect;
				if(shownCond) {
				    rect = new Rectangle((lengthPrec), y-5, widthRect, heightRect);
					e.gc.drawRectangle(rect);
				}else {
					rect = new Rectangle((standardLengthPrec), y-5, widthRect, heightRect);
					e.gc.drawRectangle(rect);
				}

				if (shownName) {
					int l = rect.x + rect.width / 6;
					e.gc.drawString(action.getName(), l, rect.y + rect.height / 3);
				}
				
				
				
				posY = rect.y + 10;
				Oval oval;
				if(defaultValueEffLenght) {
					lengthEff=getLenght(action.getEffect())*avergWidth;
				}
				for (int i = 0; i < numEff; i++) {
					int x = rect.x + rect.width;

					if (shownCond) {
						String string = action.getEffect().get(i); 
						e.gc.drawLine(x, posY, x + lengthEff, posY);
						e.gc.drawString(string, x + 2, posY - 20, false);
			
							
					}else {
							
						e.gc.drawLine(x, posY, x + standardLengthEff, posY);
						
			
					}

					posY = posY + 30;

				}
				
				resizeParent();

				
			}
		});
	
		this.addMenuDetectListener(new MenuContentAction(this));
		resizeParent();
		
		
		

	}
	
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
	    source.addDragListener(new MyDragActionListener(source));
	}
	
	
}


