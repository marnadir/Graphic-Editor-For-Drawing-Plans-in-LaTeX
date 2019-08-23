package so_goalState;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

import Action.Node;
import PlanPart.OrderConstrain;
import PlanPart.PlanContent;

/**
 * Represents the implementation of graphic part of the initial state.
 * @author nadir
 * */

public class InitialStateCanvas extends IStateCanvas  {

	private int initialFontSize = -1;
	private  Font  font;
	public  float scale = 1;
	PlanContent planContent;


	public InitialStateCanvas(Composite parent, int style, IState state) {
		super(parent, SWT.NONE, state);
		if(parent.getParent() instanceof PlanContent) {
			planContent=(PlanContent)parent.getParent();
			scale=planContent.getScale();
		}
	}

    @Override
	public void draw() {
		super.draw();
		this.addMouseWheelListener(getMouseListener());
		this.addPaintListener(getPaintListener());
	}
    
    public  int getTextPosition(int avergWidth) {
    	  int i = 5;
    	  int stringLenght=state.getText().length()*avergWidth+6;
    	  if(stringLenght>state.getLenIn()) {
       		  state.setLenIn(stringLenght);
    		  return i;
    	  }else {
    		  i=(int) ((state.getLenIn()-stringLenght)/2);
    		  return i;
    	  }
    	  
    }
    
	private MouseWheelListener getMouseListener() {

		MouseWheelListener listener = new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				if(planContent !=null) {
					scale=planContent.getScale();

				}
				if (e.count > 0)
					scale += .2f;
				else
					scale -= .2f;

				if (scale > 1.2) {
					scale = 1.2f;
				}
				if (scale < 0.6) {
					scale = 0.6f;
				}
				scale = Math.max(scale, 0);
				if(planContent!=null) {
					 planContent.setScale(scale);
			            if( planContent.getInitialStateCanvas() != null) {
				            planContent.getInitialStateCanvas().redraw();

			            }
			            for(Node node:planContent.getActionInPlan()) {
			            	node.redraw(); 	
			            }
			            for(OrderConstrain ordering:planContent.getOrds()) {
			            	ordering.getConstrainCanvas().redraw();
			            	ordering.setLocationParent();
			            }
			            
				}
				
		        
		            

				redraw();

			}
		};

		return listener;
	}
	
	
	
	private PaintListener getPaintListener() {
		
		PaintListener paintListener=new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {

				if(planContent !=null) {
					scale=planContent.getScale();
				}
				
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

				
				int avergWidth = 6;
				//int avergWidth =e.gc.getFontMetrics().getAverageCharacterWidth();

				int startX = 0;
				int startY =0;
				
				int numCond = state.getConds().size();
				
				if(state.isText) {
					state.setLenIn(300);
				}else {
					state.setLenIn(state.getHeight());
				}
				
				state.setLenIn(state.getHeight());

				
				if(state.isText) {
					int val=getTextPosition(avergWidth);
					Rectangle rect=new Rectangle(startX, (int) (startY*scale), (int) (20*scale), (int) ((startY + state.getLenIn())*scale));
					if(state.isFillColor()) {
						e.gc.setBackground(getColorSWT());
						e.gc.fillRectangle(rect);
						e.gc.drawRectangle(rect);
					}else {
						e.gc.drawRectangle(rect);
					}
					
					Transform t=new Transform(getDisplay());
					t.rotate(90);
					
					e.gc.setTransform(t);
					
					e.gc.drawString(state.getText(), (int) (val*scale), (int) (-20*scale));
					startX=20;
					
					t.rotate(-90);
					e.gc.setTransform(t);

					e.gc.setBackground(colorNull);

				}else {
					e.gc.setLineWidth(6);
					e.gc.drawLine((int) (startX), (int) (startY), (int) (startX*scale), (int) (( state.getLenIn())*scale));
					e.gc.setLineWidth(1);
				}
				
				
				int posY=(int) (5+(state.getLenIn()/numCond)/2); 
				int incr=(int) (state.getLenIn()/numCond);
				
				state.setLengthCond(getLenght(state.getConds()) *9);

				
				for (int i = 0; i < numCond; i++) {
					String string = state.getConds().get(i);

					if(state.isShownCond()) {
						e.gc.drawLine((int) (startX*scale), (int) (posY*scale), (int) ((startX + state.getLengthCond())*scale), (int) (posY*scale));
						e.gc.drawString(string, (int) ((startX + 5)*scale), (int) ((int) (posY- 10)*scale) , false);
						if(containerState.getParent() instanceof PlanContent) {
							addOval(state,string,(int)(containerState.getLocation().x+(containerState.getBounds().width+1)),(int)(containerState.getLocation().y+ ((posY-1)*scale)));
						}
					}else {
						e.gc.drawLine((int) (startX*scale), (int) (posY*scale), (int) ((int) (startX + state.getStandardLengthCond())*scale), (int) (posY*scale));
						if(containerState.getParent() instanceof PlanContent) {
							addOval(state,string,containerState.getLocation().x+containerState.getBounds().width+1,(int) (containerState.getLocation().y+ ((posY-1)*scale)));
						}
					}
					posY = posY + incr;

				}
				
				resizeParent();
				computeSize(getParent().getSize().x,getParent().getSize().y);
			}
		};
		
		return paintListener;
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

	public void resizeParent() {
		if(state.isShownCond()) {
			int x1;
			if(state.isText()) {
				 x1=(int) ((int) ((int)state.getLengthCond()+22)*scale);

			}else {
				 x1=(int) ((state.getLengthCond()+3)*scale);

			}
			int y1=(int) (state.getLenIn()*scale+4);
			containerState.setSize(x1,y1);
			
		}else {
			double x1;
			if(state.isText()) {
				x1=(state.getStandardLengthCond()+22)*scale;
			}else {
				x1=(state.getStandardLengthCond()+3)*scale;
			}
			int y1=(int) ((state.getLenIn()+4)*scale);
			containerState.setSize((int) x1,y1);

		}
	}
	
	
}
