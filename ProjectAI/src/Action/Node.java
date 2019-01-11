package Action;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import logic.MenuContentAction;

public class Node extends ICanvasAction {
	
	public  String ID;

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

				int posY = 30;
				int y = 25;

				for (int i = 0; i < action.getPrec().size(); i++) {
					String string = action.getPrec().get(i);

					if (action.isShownCond()) {
						e.gc.drawLine(0, posY, (action.getLengthPrec()), posY);
						e.gc.drawString(string, 2, posY - 20, false);
						addOval(action, string, 1, posY - 2);

					} else {
						e.gc.drawLine(0, posY, action.getStandardLengthPrec(), posY);
						addOval(action, string, 1, posY - 2);

					}

					posY = posY + 30;
				}

				/* Drawing rectangle w/o name */
				Rectangle rect;
				if (action.isShownCond()) {
					rect = new Rectangle((action.getLengthPrec()), y - 5, action.getWidthRect(),
							action.getHeightRect());
					e.gc.drawRectangle(rect);
				} else {
					rect = new Rectangle((action.getStandardLengthPrec()), y - 5, action.getWidthRect(),
							action.getHeightRect());
					e.gc.drawRectangle(rect);
				}

				if (action.isShownName()) {
					int l = rect.x + rect.width / 6;
					e.gc.drawString(action.getName(), l, rect.y + rect.height / 3);
				}

				posY = rect.y + 10;

				for (int i = 0; i < action.getEffect().size(); i++) {
					int x = rect.x + rect.width;
					String string = action.getEffect().get(i);

					if (action.isShownCond()) {
						e.gc.drawLine(x, posY, x + action.getLengthEff(), posY);
						e.gc.drawString(string, x + 2, posY - 20, false);

						addOval(action, string, x + action.getLengthEff(), posY - 2);
						// e.gc.drawOval(x + (16 + lengthEff), posY-2, 5, 5);

					} else {

						e.gc.drawLine(x, posY, x + action.getStandardLengthEff(), posY);
						addOval(action, string, x + action.getStandardLengthEff() - 3, posY - 2);

					}

					posY = posY + 30;

				}
				resizeParent();
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

	
	
	
}
