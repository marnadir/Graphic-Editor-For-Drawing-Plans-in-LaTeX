package newHope;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Main {

	public static void main(String[] args) {
	    Display display = new Display();

		Shell shell=new Shell(display);
		newIDialog dialog=new newIDialog(shell,
				SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER|SWT.RESIZE) {
			
			@Override
			public Listener getOkbtnListener() {
				Listener l;
				l=new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						
					}
				};
				
				return l;
			}
			
			@Override
			public void createContent() {
				label.setText("JLSDH");
				label.pack();
				
			}
		};

		dialog.createContent();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
	}
	

}
