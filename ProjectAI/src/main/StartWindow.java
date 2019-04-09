package main;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import View.PrincipalView;

public class StartWindow {

	
	public void start() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(display.getClientArea().width, display.getClientArea().height);
		shell.setText("Graphical Editor For Drawing Plan");
		shell.setLayout(new FillLayout());

		PrincipalView principalView = new PrincipalView(shell);
		principalView.draw();

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
	}
	
	public static void main(String[] args) {
		new StartWindow().start();
	}
}
