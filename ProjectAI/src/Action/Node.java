package Action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import LaTex.LaTexGeneratorNode;
import Menu.MenuContentAction;
import PlanPart.PlanContent;

public class Node extends ICanvasNode {
	
	public  String ID;
	String latexCode;
	


	public Node(Composite parent, int style, Action a) {
		super(parent, style, a);
	}

	@Override
	public void draw() {
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				action.resize();

				/* draw precs with their "point" */

				int y = 20;
				
				Font font = new Font(getDisplay(), "Arabic Transparent", 6, SWT.NORMAL);
				e.gc.setFont(font);
				Color colorNull=e.gc.getBackground();
				
				int posY=(int) (-5+(action.getHeightRect()/action.getNumPrec())/2)+y; 
				int incr=(int) (action.getHeightRect()/action.getNumPrec());
				
				
				
				for (int i = 0; i < action.getPrec().size(); i++) {
					String string = action.getPrec().get(i);

					if (action.isShownCond()) {
						e.gc.drawLine(0, posY, (int) (action.getLengthPrec()), posY);
						e.gc.drawString(string, 2, posY - 20, false);
						addOval(action, string,parent.getLocation().x-6, parent.getLocation().y+ posY-1);

					} else {
						e.gc.drawLine(0, posY, (int) action.getStandardLengthPrec(), posY);
						addOval(action, string,parent.getLocation().x-6, parent.getLocation().y+ posY-1);

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
					rect = new Rectangle((int)(action.getLengthPrec()), y - 5, (int)action.getWidthRect(),
							(int)action.getHeightRect());

				} else {
					rect = new Rectangle((int)(action.getStandardLengthPrec()), y - 5, (int)action.getWidthRect(),
							(int)action.getHeightRect());
				}


				if (action.Isborder()) {
					if(action.isFillColor()) {
						e.gc.setBackground(getColorSWT());
						if(!action.isBorderIsSquare()) {
							e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 10, 10);
						}else {
							e.gc.fillRectangle(rect);
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

				if (action.isShownName()) {
					int l = rect.x + rect.width / 6;
					e.gc.drawString(action.getName(), l, rect.y + rect.height / 3);
				}

				e.gc.setBackground(colorNull);

				
				posY=(int) (-5+(action.getHeightRect()/action.getNumEff())/2)+y; 
				incr=(int) (action.getHeightRect()/action.getNumEff());	
				
				resizeParent();
				for (int i = 0; i < action.getEffect().size(); i++) {
					int x = rect.x + rect.width;
					String string = action.getEffect().get(i);

					if (action.isShownCond()) {
						e.gc.drawLine(x, posY, (int) (x + action.getLengthEff()), posY);
						e.gc.drawString(string, x + 2, posY - 20, false);
						addOval(action,string,parent.getLocation().x+parent.getBounds().width+1,parent.getLocation().y+ posY-1);

					} else {

						e.gc.drawLine(x, posY, (int) (x + action.getStandardLengthEff()), posY);
						addOval(action,string,parent.getLocation().x+parent.getBounds().width+1,parent.getLocation().y+ posY-1);

					}

					posY = posY + incr;

				}
				resizeParent();
				redraw();
			}
		});

		this.addMenuDetectListener(new MenuContentAction(this));
		resizeParent();

	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void generateLatexCode(PlanContent planContent) {
		LaTexGeneratorNode generator=new LaTexGeneratorNode(planContent);
		latexCode=generator.getLatexActionCodePlan(action, this);
		
	}
	
	public String getLatexCode() {
		return latexCode;
	}
}
