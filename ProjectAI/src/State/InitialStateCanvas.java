package State;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import GraphPart.GraphContent;

public class InitialStateCanvas extends IStateCanvas{

	
	
	
	public InitialStateCanvas(Composite parent, int style, IState state) {
		super(parent, style, state);
		name="SoState";
	}

	// TODO method allow to draw the initial state
    @Override
	public void draw() {
		super.draw();
		this.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				int avergWidth = (int) e.gc.getFontMetrics().getAverageCharacterWidth();
				lengthCond=getLenght(state.getConds())*avergWidth+6;

				int numCond = state.getConds().size();
				int startX = 0;
				int startY =0;
				
				lenIn=numCond*30;
				
				e.gc.setLineWidth(6);
				e.gc.drawLine(startX, startY, startX, startY + lenIn);
				e.gc.setLineWidth(1);

				
				int posY = startY + 20;
				for (int i = 0; i < numCond; i++) {
					String string = state.getConds().get(i);

					if(shownCond) {
						e.gc.drawLine(startX, posY, startX + lengthCond, posY);
						e.gc.drawString(string, startX + 5, posY - 20, false);
						if(parent.getParent() instanceof GraphContent) {
							addOval(string,startX+lengthCond, posY-2);
						}
					}else {
						e.gc.drawLine(startX, posY, startX + standardLength, posY);
						if(parent.getParent() instanceof GraphContent) {
							addOval(string,startX+standardLength, posY-2);
						}
					}
					posY = posY + 30;

				}
				resizeParent();

				// e.gc.drawRectangle(r);
			}
		});
		// parent.pack();

	
		
	}
    


}
