package logic;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Action {

	String name;
	ArrayList<String> prec;
	ArrayList<String> effect;
	Canvas canvas;
	Composite contentCanvas;

	
	public Action(String name,ArrayList<String> prec, ArrayList<String> eff) {
		this.name=name;
		this.prec=prec;
		this.effect=eff;
		
	}
	
	public void draw(Composite comp) {
		if (canvas != null) {
			canvas.redraw();
		} else {
			this.contentCanvas = comp;
			canvas = new Canvas(comp, SWT.ALL);
			// canvasSo.setLayout(new FillLayout());
			canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			canvas.setBackground(comp.getDisplay().getSystemColor(SWT.COLOR_DARK_CYAN));// green
			canvas.setSize(comp.getSize());

		}
		canvas.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {

				int numCond = prec.size();

				int startX = comp.getLocation().x + 40;
				int startY = comp.getLocation().y + 40;

				e.gc.setLineWidth(6);
				e.gc.drawLine(startX, startY, startX, startY + (30 * numCond));
				e.gc.setLineWidth(1);

				int posY = startY + 10;
				for (int i = 0; i < numCond; i++) {

					e.gc.drawLine(startX, posY, startX + 50, posY);
					String string = prec.get(i);
					e.gc.drawString(string, startX + 10, posY - 20, false);

					posY = posY + 30;

				}

				// e.gc.drawRectangle(r);
			}
		});
		
	}
	
	
}
