package Menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class IMenu extends Menu{
	
	

	
	public IMenu(Decorations parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	public MenuItem createItem(String nameItem, int layout) {
		MenuItem menuItem = new MenuItem(this, layout);
		menuItem.setText(nameItem);		
		return menuItem;
	}
	
	@Override
	protected void checkSubclass() {
		// TODO Auto-generated method stub
		
	}
}
