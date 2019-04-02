package State;


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Composite;

import PlanPart.PlanContent;



public class GoalStateCanvas extends IStateCanvas {

	

	
	public GoalStateCanvas(Composite parent, int style, IState state) {
		super(parent, style, state);
	}

	
	@Override
	public void draw() {
		super.draw();
		 
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
			
				int avergWidth = 7;	
				//int avergWidth =e.gc.getFontMetrics().getAverageCharacterWidth();
				int numCond = state.getConds().size();

				int startX = parent.getClientArea().width;
				int startY = 0;

				if(state.isText) {
					state.setLenIn(300);
				}else {
					state.setLenIn(numCond*30);
				}

				if(state.isText()) {
					int val=getTextPosition(avergWidth);

					Rectangle r=new Rectangle(startX-22, startY,20, startY + state.getLenIn());
					
					e.gc.drawRectangle(r);	  
					Transform t=new Transform(getDisplay());
					t.rotate(90);
					
					e.gc.setTransform(t);
					
					e.gc.drawString(state.getText(), val, -startX+2);
					
					
					t.rotate(-90);
					e.gc.setTransform(t);
					
					startX=parent.getClientArea().width-22;
					
				}else {
					e.gc.setLineWidth(5);
					e.gc.drawLine(startX-2, startY, startX-2, startY + state.getLenIn());
					e.gc.setLineWidth(1);
				}
				
				
				int posY=5+(state.getLenIn()/numCond)/2; 
				int incr=state.getLenIn()/numCond;
				for (int i = 0; i < numCond; i++) {
					String string = state.getConds().get(i);

					if(state.isShownCond()) {
						e.gc.drawLine(startX, posY, (int) (-2+startX - state.getLengthCond()), posY);
						e.gc.drawString(string, (int) (startX+3 - state.getLengthCond()), posY - 20, false);
						if(parent.getParent() instanceof PlanContent) {
							addOval(state,string,parent.getLocation().x-6, parent.getLocation().y+ posY-2);
						}
					}else {
						e.gc.drawLine(startX, posY, (int) (-2+startX - state.getStandardLengthCond()), posY);
						if(parent.getParent() instanceof PlanContent) {
							addOval(state,string,parent.getLocation().x-6, parent.getLocation().y+ posY-2);
						}
					}
					posY = posY + incr;

				}
			
				resizeParent();
				computeSize(getParent().getSize().x,getParent().getSize().y);
				if(state.getConds().size()==1) {
					//pack();
					computeSize(getParent().getSize().x,getParent().getSize().y);
				}
				
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
   		  i=(state.getLenIn()-stringLenght)/2;
   		  return i;
   	  }
   	  
   }
	
}
