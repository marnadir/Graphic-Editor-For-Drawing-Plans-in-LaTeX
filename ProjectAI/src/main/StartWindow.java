package main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import GUI.DrawWindow;

public class StartWindow {

	
	public void start() {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(display.getClientArea().width, display.getClientArea().height);
		shell.setText("Tool Drawing Plan");
		shell.setLayout(new FillLayout());

		DrawWindow drawWindow = new DrawWindow(shell);
		drawWindow.draw();

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
