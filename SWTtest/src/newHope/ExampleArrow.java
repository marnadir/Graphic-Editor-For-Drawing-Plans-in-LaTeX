package newHope;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ExampleArrow {

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
				Canvas canvas=new Canvas(shell, SWT.ALL);
				canvas.addPaintListener(new PaintListener() {
					
					@Override
					public void paintControl(PaintEvent e) {
						
					}
				});
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
