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
import Menu.MenuContentAction;

public class CanvasAction  extends ICanvasAction{

	int style;
	PaintListener p;

	
	public CanvasAction(Composite parent, int style, Action a) {
		super(parent, style, a);
		this.style=style;
		// TODO Auto-generated constructor stub
	}

	
	
	
	public void draw() {

		
		this.addPaintListener(createPaintListener());
		
		this.redraw();
		this.addMenuDetectListener(new MenuContentAction(this));
		resizeParent();
	
		

	}
	
	public void removeListener() {	
		this.removePaintListener(getPaintListener());
	}
	
	public PaintListener getPaintListener() {
		return p;
	}
	
	public PaintListener createPaintListener() {
		

		 p = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				action.resize();
		

				int posY = 30;
				int y = 25;


				for (int i = 0; i < action.getNumPrec(); i++) {

					if (action.isShownCond()) {
						String string = action.getPrec().get(i);
						e.gc.drawLine(0, posY, (int) (action.getLengthPrec()), posY);
						e.gc.drawString(string, 2, posY - 20, false);

					} else {
						e.gc.drawLine(0, posY, (int) action.getStandardLengthPrec(), posY);
					}

					posY = posY + 30;
				}

				/* Drawing rectangle w/o name */
				Rectangle rect;
				if(action.isFett()) {
					e.gc.setLineWidth(3);
				}else {
					e.gc.setLineWidth(0);

				}
				if (action.isShownCond()) {
					rect = new Rectangle((int)(action.getLengthPrec()), y - 5,(int) action.getWidthRect(), (int)action.getHeightRect());
				} else {
					rect = new Rectangle((int)(action.getStandardLengthPrec()), y - 5, (int)action.getWidthRect(),(int) action.getHeightRect());	
				}

				
				if (action.isForm()) {
					if(action.isFillColor()) {
						e.gc.setBackground(getColorSWT());
						if(action.isRectRound()) {
							e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);
						}else {
							e.gc.fillRectangle(rect);
						}
					}else {
						if(action.isRectRound()) {
							e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);

						}else {
							e.gc.drawRectangle(rect);	
						}
					}
					

				}
				
				if (action.isShownName()) {
					int l = rect.x + rect.width / 6;
					e.gc.drawString(action.getName(), l, rect.y + rect.height / 3);
				}

				posY = rect.y + 10;				
				for (int i = 0; i < action.getEffect().size(); i++) {
					int x = rect.x + rect.width;

					if (action.isShownCond()) {
						String string = action.getEffect().get(i);
						e.gc.drawLine(x, posY, (int) (x + action.getLengthEff()), posY);
						e.gc.drawString(string, x + 2, posY - 20, false);

					} else {

						e.gc.drawLine(x, posY, (int) (x + action.getStandardLengthEff()), posY);

					}

					posY = posY + 30;

				}

				resizeParent();
				e.gc.dispose();
			}
		};
		
		return p;
	}
	
	public void resizeParent() {
		if(action.isShownCond()) {
			double x1=action.getLengthPrec()+action.getLengthEff()+action.getWidthRect();
			if(action.getPrec().size()==0 || action.getEffect().size()==0) {
				x1=x1+3;
			}
			double y1=action.getHeightRect()+40;
			parent.setSize((int)x1,(int)y1);
			
		}else {
			double x1=action.getStandardLengthPrec()+action.getStandardLengthEff()+action.getWidthRect();
			if(action.getPrec().size()==0 || action.getEffect().size()==0) {
				x1=x1+3;
			}
			double y1=action.getHeightRect()+40;
			parent.setSize((int)x1,(int)y1);
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


