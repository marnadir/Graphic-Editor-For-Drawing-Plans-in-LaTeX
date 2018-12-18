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


public class Node  extends ICanvasAction{
	
	public Node(Composite parent, int style, Action a) {
		super(parent, style, a);
	}


	
	@Override
	public void draw() {

		widthRect = action.getName().length() * 12;
		this.addPaintListener(new PaintListener() {

			
			@Override
			public void paintControl(PaintEvent e) {

				int numPrec = action.getPrec().size();
				int numEff = action.getEffect().size();

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
					String string = action.getPrec().get(i);

					if (shownCond) {
						e.gc.drawLine(0, posY, (lengthPrec), posY);
						e.gc.drawString(string, 2, posY - 20, false);				
						addOval(string,1, posY-2);
					
						
									
					} else {
						e.gc.drawLine(0, posY,  standardLengthPrec, posY);	
						addOval(string,1, posY-2);

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
				if(defaultValueEffLenght) {
					lengthEff=getLenght(action.getEffect())*avergWidth;
				}
				for (int i = 0; i < numEff; i++) {
					int x = rect.x + rect.width;
					String string = action.getEffect().get(i); 

					if (shownCond) {
						e.gc.drawLine(x, posY, x + lengthEff, posY);
						e.gc.drawString(string, x + 2, posY - 20, false);
			
						addOval(string,x+lengthEff, posY-2);
						//e.gc.drawOval(x + (16 + lengthEff), posY-2, 5, 5);
							
					}else {
							
						e.gc.drawLine(x, posY, x + standardLengthEff, posY);
						addOval(string,x+standardLengthEff-3, posY-2);
			
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
	
	
	
	public void addOval(String name,int x, int y) {
		Oval oval=new Oval(this,name);
		oval.setLocation(x, y);
		oval.drawO();
	}
	

	
}
