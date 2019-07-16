package Action;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import DNDAaction.MyDragActionListener;
import Menu.MenuContentAction;
import dataTrasfer.MyTransfer;
/**
 * Represents the graphic part of an action, which is created during the domain definition phase.
 * @author nadir
 *
 */
public class ActionDomainCanvas  extends ICanvas{

	int style;
	PaintListener p;

	
	public ActionDomainCanvas(Composite parent, int style, Action a) {
		super(parent, style, a);
		this.action = a;
		this.style=style;
		// TODO Auto-generated constructor stub
	}
	
	public void draw() {
		this.addPaintListener(createPaintListener());
		this.redraw();
		this.addMenuDetectListener(new MenuContentAction(this));
		resizeParent();
	}
	
	
	public PaintListener createPaintListener() {
		

		 p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				action.resize();
		
				Font font = new Font(getDisplay(), "Arabic Transparent", 6, SWT.NORMAL);
			
				Color colorNull=e.gc.getBackground();
				e.gc.setFont(font);

				int y = 20;
				
				if(action.isDefaultAction()) {
					if(action.isPrimitive) {
						action.setIsFett(GlobalValue.borderIsFatPr);
						action.setIsborder(GlobalValue.formIsBlackPr);
						action.setBorderIsSquare(GlobalValue.cornerIsSquarePr);
						action.setColorString(GlobalValue.colorP);
					}else {
						action.setIsFett(GlobalValue.borderIsFatAbst);
						action.setIsborder(GlobalValue.formIsBlackAbst);
						action.setBorderIsSquare(GlobalValue.cornerIsSquareAbst);
						action.setColorString(GlobalValue.colorAbst);
					}
				}

				
				int posY=(int) ((action.getHeightRect()/action.getNumPrec())/2)+y; 
				int incr=(int) (action.getHeightRect()/action.getNumPrec());

				for (int i = 0; i < action.getNumPrec(); i++) {

					if (action.isShownCond()) {
						String string = action.getPrec().get(i);
						e.gc.drawLine(0, posY, (int) (action.getLengthPrec()), posY);
						e.gc.drawString(string, 2, posY - 10, false);

					} else {
						e.gc.drawLine(0, posY, (int) action.getStandardLengthPrec(), posY);
					}

					posY = posY + incr;
				}

				/* Drawing rectangle w/o name */
				Rectangle rect;
				if(action.isFett()) {
					e.gc.setLineWidth(3);
				}else {
					e.gc.setLineWidth(0);

				}
				if (action.isShownCond()) {
					rect = new Rectangle((int)(action.getLengthPrec()),y,(int) action.getWidthRect(), (int)action.getHeightRect());
				} else {
					rect = new Rectangle((int)(action.getStandardLengthPrec()), y, (int)action.getWidthRect(),(int) action.getHeightRect());	
				}


				if (action.Isborder()) {
					if(action.isFillColor()) {
						e.gc.setBackground(getColorSWT());
						if(!action.isBorderIsSquare()) {
							e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);
							e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);

						}else {
							e.gc.fillRectangle(rect);
							e.gc.drawRectangle(rect);	

						}
					}else {
						if(!action.isBorderIsSquare()) {
							e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);

						}else {
							e.gc.drawRectangle(rect);	
						}
					}
					

				}
				
				
				e.gc.setLineWidth(0);

				int widthSize = (int)e.gc.getFontMetrics().getAverageCharacterWidth();

				int val=(int) (getTextPosition(widthSize)+rect.x);

				
				if (action.isShownName()) {
					e.gc.drawString(action.getName(), val, rect.y + rect.height / 3);
				}

				e.gc.setBackground(colorNull);
				
				
				
				posY=(int) ((action.getHeightRect()/action.getNumEff())/2)+y; 
				incr=(int) (action.getHeightRect()/action.getNumEff());	
				
				for (int i = 0; i < action.getEffect().size(); i++) {
					int x = rect.x + rect.width;

					if (action.isShownCond()) {
						String string = action.getEffect().get(i);
						e.gc.drawLine(x, posY, (int) (x + action.getLengthEff()), posY);
						e.gc.drawString(string, x + 2, posY - 10, false);

					} else {

						e.gc.drawLine(x, posY, (int) (x + action.getStandardLengthEff()), posY);

					}

					posY = posY + incr;

				}

				resizeParent();
				e.gc.dispose();
			}
		};
		
		return p;
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
	
    private  int getTextPosition(int avergWidth) {
  	  int i = 5;
  	  int stringLenght=action.getName().length()*avergWidth+6;
  	  if(stringLenght>action.getWidthRect()) {
     		  action.setWidthRect(stringLenght);
  		  return i;
  	  }else {
  		  i=(int) ((action.getWidthRect()-stringLenght)/2);
  		  return i;
  	  }
  	  
    }
	
}


