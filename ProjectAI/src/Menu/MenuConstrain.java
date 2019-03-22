package Menu;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import Action.Node;
import PlanPart.OrderConstrainCanvas;
import PlanPart.Oval;
import PlanPart.PlanContent;

public class MenuConstrain implements MenuDetectListener  {

	OrderConstrainCanvas constrain;
	
	public MenuConstrain(OrderConstrainCanvas constrain) {
		this.constrain=constrain;
	}
	
	@Override
	public void menuDetected(MenuDetectEvent e) {
		Menu m = new Menu(constrain);
		constrain.setMenu(m);

		MenuItem c = new MenuItem(m, SWT.ALL);
		c.setText("Clear");
		
		c.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if(constrain instanceof OrderConstrainCanvas) {
					if(constrain.getParent().getParent() instanceof PlanContent) {
						PlanContent plan=(PlanContent)constrain.getParent().getParent();	
						plan.getOrds().remove(constrain.getOrderConstrain());
						constrain.getParent().dispose();
						
					}
				}
				
			}
		});
		
	}

	

}
