package GraphPart;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import logic.Action;

import java.util.ArrayList;

import javax.xml.crypto.dsig.CanonicalizationMethod;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import DND.MyDragSourceListener;
import DataTrasfer.MyTransfer;

public class Node  extends Canvas{


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
	

	
	public Node(Composite parent, int style,Action a) {
		super(parent, style);
		this.parent=parent;
		this.action=a;
		
	}
	
	
	
	public void draw() {

		widthRect = action.getName().length() * 12;
		
		this.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,true,true));
		this.setLocation(0,0);



		this.addPaintListener(new PaintListener() {

			
			@Override
			public void paintControl(PaintEvent e) {

				int numPrec = action.getPrec().size();
				int numEff = action.getEffect().size();
				int startX = 70 ;
				int startY = 50;
				

				max = numPrec;
				if (numEff > numPrec) {
					max = numEff;
				}

				if (max > 1) {
					heightRect = 30 + max * 10;
				}

				
				Rectangle rect = new Rectangle(startX, startY, widthRect, heightRect);
				e.gc.drawRectangle(rect);

				if (shownName) {
					int l = rect.x + rect.width / 6;
					e.gc.drawString(action.getName(), l, rect.y + rect.height / 3);
				}

				int posY = rect.y + 10;
				
				
				int avergWidth = (int) e.gc.getFontMetrics().getAverageCharacterWidth();

				if(defaultValuePrecLenght) {
					lengthPrec=getLenght(action.getPrec())*avergWidth;
				}
				
				for (int i = 0; i < numPrec; i++) {

					if (shownCond) {
						String string = action.getPrec().get(i);
						e.gc.drawLine(rect.x, posY, rect.x - (lengthPrec+12), posY);
						e.gc.drawString(string, rect.x - (8 + lengthPrec), posY - 20, false);				
						e.gc.setLineWidth(4);
						e.gc.drawOval( rect.x - (13 + lengthPrec), posY-2, 5, 5);
						e.gc.setLineWidth(0);
									
					} else {
						e.gc.drawLine(rect.x, posY, rect.x - standardLengthPrec, posY);	
						e.gc.setLineWidth(4);
						e.gc.drawOval( rect.x - (standardLengthPrec+4), posY-2, 5, 5);
						e.gc.setLineWidth(0);
						

					}

					posY = posY + 30;
				}

				posY = rect.y + 10;
				if(defaultValueEffLenght) {
					lengthEff=getLenght(action.getEffect())*avergWidth;
				}
				for (int i = 0; i < numEff; i++) {
					int x = rect.x + rect.width;

					if (shownCond) {
						String string = action.getEffect().get(i); 
						e.gc.drawLine(x, posY, x + lengthEff+15, posY);
						e.gc.drawString(string, x + 10, posY - 20, false);
						e.gc.setLineWidth(4);
						e.gc.drawOval(x + (16 + lengthEff), posY-2, 5, 5);
						e.gc.setLineWidth(0);
						
					}else {
							
						e.gc.drawLine(x, posY, x + standardLengthEff, posY);
						e.gc.setLineWidth(4);
						e.gc.drawOval(x+standardLengthEff+1, posY-2, 5, 5);
						e.gc.setLineWidth(0);
						int x1=lengthPrec+standardLengthEff+widthRect+100;
						int y1=heightRect+100;
						
						//parent.setSize(x1, y1);
						//e.gc.drawString("dafd", x, posY);
						

					}

					posY = posY + 30;

				}

			}
		});
	
		
		
		//this.addMenuDetectListener(new MenuContentAction(this));
		int x1=lengthPrec+standardLengthEff+widthRect+100;
		int y1=heightRect+100;
		parent.setSize(x1, y1);
		
		this.setSize(parent.getBounds().width, parent.getBounds().height);
	
		

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

	
	public void addDNDListener() {
		DragSource source =new DragSource(this, DND.DROP_NONE);
	    source.setTransfer(new Transfer[] { MyTransfer.getInstance() });
	    source.addDragListener(new MyDragSourceListener(source));
	}
	
	
}
