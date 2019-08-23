package Action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import LaTex.LaTexGeneratorNode;
import Menu.MenuContentAction;
import PlanPart.OrderConstrain;
import PlanPart.PlanContent;
/**
 * Represents the graphic part of an action, which is represented in the plan view.
 * @author nadir
 *
 */
public class Node extends ICanvas {
	
	public  String ID;
	String latexCode;
    private int initialFontSize = -1;
    private  Font  font;
	public static float scale = 1;
	PlanContent planContent;





	public Node(Composite parent, int style, Action a) {
		super(parent, style, a);
		if(parent.getParent() instanceof PlanContent) {
			planContent=(PlanContent)parent.getParent();
			scale=planContent.getScale();
		}
	}

	
	@Override
	public void draw() {	
		this.addPaintListener(getPaintListener());
		this.addMouseWheelListener(getMouseListener());
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
		LaTexGeneratorNode generator = new LaTexGeneratorNode(planContent);
		latexCode = generator.getLatexActionCodePlan(action, this);

	}

	public String getLatexCode() {
		return latexCode;
	}

	public PlanContent getPlan() {
		PlanContent plan = null;
		if (getParent().getParent() instanceof PlanContent) {
			plan = (PlanContent) getParent().getParent();
		}

		return plan;

	}
	

	
	private int getTextPosition(int avergWidth) {
		int i = 5;
		int stringLenght = action.getName().length() * avergWidth + 6;
		if (stringLenght > action.getWidthRect()) {
			action.setWidthRect(stringLenght);
			return i;
		} else {
			i = (int) ((action.getWidthRect() - stringLenght) / 2);
			return i;
		}

	}

	public void resizeParent() {
		if (action.isShownCond()) {
			double x1 = action.getLengthPrec()*scale + action.getLengthEff()*scale + (action.getWidthRect()*scale)+2;
			if(action.getPrec().size()==0 && action.getEffect().size()==0) {
				x1=x1+5;
			}
			double y1 = action.getHeightRect()*scale + 40;
			parent.setSize((int)x1,(int) y1);

		} else {
			double x1 ;
			x1=action.getStandardLengthPrec()*scale + action.getStandardLengthEff()*scale + action.getWidthRect()*scale+2;
			if(action.getPrec().size()==0 && action.getEffect().size()==0) {
				x1=x1+5;
			}
			double y1 = action.getHeightRect()*scale + 40;
			parent.setSize((int)x1, (int)y1);
		}
	}
	
	
	
	private PaintListener getPaintListener() {
		PaintListener listener;
		
		listener=new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {

				scale=planContent.getScale();
				
				action.resize();

				/* draw precs with their "point" */

				int y = 20;

				
				Font tempFont = new Font(getDisplay(), "Arabic Transparent", 6, SWT.NORMAL);
	            FontData data = tempFont.getFontData()[0];
	            if (initialFontSize == -1)
	                initialFontSize = tempFont.getFontData()[0].getHeight();
	            else
	            {
	                if(font != null && !font.isDisposed())
	                    font.dispose();

	                data.setHeight((int)(initialFontSize * scale));

	                font = new Font(getDisplay(), data);

	                e.gc.setFont(font);
	            }

				e.gc.setFont(font);
				Color colorNull=e.gc.getBackground();
				
				int posY=(int) (-5+((action.getHeightRect())/action.getNumPrec())/2)+y; 
				int incr=(int) ((action.getHeightRect())/action.getNumPrec());
				
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
				
				for (int i = 0; i < action.getPrec().size(); i++) {
					String string = action.getPrec().get(i);

					if (action.isShownCond()) {
						e.gc.drawLine(0, (int) (posY*scale), (int) (action.getLengthPrec()*scale), (int) (posY*scale));
						e.gc.drawString(string, (int) (2*scale), (int) ((posY- 10)*scale), false);
						addOval(action, string,parent.getLocation().x-6, (int) (parent.getLocation().y+ posY*scale-1));

					} else {
						e.gc.drawLine(0, (int) (posY*scale), (int) (action.getStandardLengthPrec()*scale), (int) (posY*scale));
						addOval(action, string,parent.getLocation().x-6, parent.getLocation().y+ (int) (posY*scale)-1);

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
					rect = new Rectangle((int)(action.getLengthPrec()*scale), y - 5, (int)(action.getWidthRect()*scale),
							(int)(action.getHeightRect()*scale));

				} else {
					rect = new Rectangle((int)(action.getStandardLengthPrec()*scale), y - 5, (int)(action.getWidthRect()*scale),
							(int)(action.getHeightRect()*scale));
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
				
				int widthSize = (int)e.gc.getFontMetrics().getAverageCharWidth();
				int val=(int) (getTextPosition(widthSize)+rect.x);
				
				
				
				if (action.isShownName()) {
					e.gc.drawString(action.getName(), (int) (val*scale), rect.y + rect.height / 3);
				}

				e.gc.setBackground(colorNull);

				
				posY=(int) (-5+((action.getHeightRect())/action.getNumEff())/2)+y; 
				incr=(int) ((action.getHeightRect())/action.getNumEff());	
				
				resizeParent();
				for (int i = 0; i < action.getEffect().size(); i++) {
					int x = rect.x + rect.width;
					String string = action.getEffect().get(i);

					if (action.isShownCond()) {
						e.gc.drawLine(x, (int) (posY*scale), (int) (x + action.getLengthEff()*scale), (int) (posY*scale));
						e.gc.drawString(string, x + 2, (int) ((posY- 10)*scale), false);
						addOval(action,string,parent.getLocation().x+parent.getBounds().width+1,(int) (parent.getLocation().y+ scale*posY-1));

					} else {

						e.gc.drawLine(x, (int)(posY*scale), (int) (x + action.getStandardLengthEff()*scale), (int)(posY*scale));
						addOval(action,string,parent.getLocation().x+parent.getBounds().width+1,(int) (parent.getLocation().y+ posY*scale-1));

					}

					posY = (int) (posY + incr);

				}
				resizeParent();
				redraw();
			}
		};
		
		return listener;
		
		
	}
	
	private MouseWheelListener getMouseListener() {

		MouseWheelListener listener = new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent event) {
				scale=planContent.getScale();
	            if (event.count > 0)
	                scale += .1f;
	            else
	                scale -= .1f;

	            if(scale>1.2) {
	            	scale=1.2f;
	            }
	            if(scale<0.6) {
	            	scale=0.6f;
	            }
	           
	            scale = Math.max(scale, 0);
	            planContent.setScale(scale);
	            if( planContent.getInitialStateCanvas() != null) {
		            planContent.getInitialStateCanvas().redraw();

	            }
	            if( planContent.getGoalStateCanvas() != null) {
		            planContent.getGoalStateCanvas().redraw();

	            }
	            for(OrderConstrain ordering:planContent.getOrds()) {
	            	ordering.getConstrainCanvas().redraw();
	            	ordering.setLocationParent();
	            }
	            
	            
	            redraw();
			}
		};

		return listener;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
