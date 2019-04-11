package State;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;

public class InitialStateCanvas extends IStateCanvas  {

	private static final long serialVersionUID = -1499882871217776674L;

	public InitialStateCanvas(Composite parent, int style, IState state) {
		super(parent, SWT.BORDER, state);
	}

	// TODO method allow to draw the initial state
    @Override
	public void draw() {
		super.draw();
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				Font font = new Font(getDisplay(), "Arabic Transparent", 9, SWT.NORMAL);
				e.gc.setFont(font);
				
				int avergWidth = 7;
				//int avergWidth =e.gc.getFontMetrics().getAverageCharacterWidth();

				int startX = 0;
				int startY =0;
				
				int numCond = state.getConds().size();
				if(state.isText) {
					state.setLenIn(300);
				}else {
					state.setLenIn(state.getHeight());
				}
				if(state.isText) {
					int val=getTextPosition(avergWidth);

					e.gc.drawRectangle(startX, startY, 20, (int) (startY + state.getLenIn()));
				  
					Transform t=new Transform(getDisplay());
					t.rotate(90);
					
					e.gc.setTransform(t);
					
					e.gc.drawString(state.getText(), val, -20);
					startX=20;
					
					t.rotate(-90);
					e.gc.setTransform(t);

					
				}else {
					e.gc.setLineWidth(6);
					e.gc.drawLine(startX, startY, startX, (int) (startY + state.getLenIn()));
					e.gc.setLineWidth(1);
				}
				
				
				int posY=(int) (5+(state.getLenIn()/numCond)/2); 
				int incr=(int) (state.getLenIn()/numCond);
				
				for (int i = 0; i < numCond; i++) {
					String string = state.getConds().get(i);

					if(state.isShownCond()) {
						e.gc.drawLine(startX, posY, (int) (startX + state.getLengthCond()), posY);
						e.gc.drawString(string, startX + 5, posY - 20, false);
						if(parent.getParent() instanceof PlanContent) {
							addOval(state,string,parent.getLocation().x+parent.getBounds().width+1,parent.getLocation().y+ posY-1);
						}
					}else {
						e.gc.drawLine(startX, posY, (int) (startX + state.getStandardLengthCond()), posY);
						if(parent.getParent() instanceof PlanContent) {
							addOval(state,string,parent.getLocation().x+parent.getBounds().width+1,parent.getLocation().y+ posY-1);
						}
					}
					posY = posY + incr;

				}
				
				resizeParent();
				computeSize(getParent().getSize().x,getParent().getSize().y);
			}
		});
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
    
  

}
