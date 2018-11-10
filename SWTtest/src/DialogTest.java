import static org.eclipse.swt.events.SelectionListener.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class DialogTest {
	public static void main(String[] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		Menu appMenuBar = display.getMenuBar();
		if (appMenuBar == null) {
			appMenuBar = new Menu(shell, SWT.BAR);
			shell.setMenuBar(appMenuBar);
		}
		MenuItem file = new MenuItem(appMenuBar, SWT.CASCADE);
		file.setText("File");
		Menu dropdown = new Menu(appMenuBar);
		file.setMenu(dropdown);
		MenuItem exit = new MenuItem(dropdown, SWT.PUSH);
		exit.setText("Exit");
		exit.addSelectionListener(widgetSelectedAdapter(e -> display.dispose()));
		Button b = new Button(shell, SWT.PUSH);
		b.setText("Test");
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}