import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class RotateString {
public static void main(String[] args) {
	Display display = new Display();

	final Shell shell = new Shell(display);
	Composite composite = new Composite(shell, SWT.NONE);
	composite.setEnabled(false);
	composite.setLayout(new GridLayout());
	composite.setSize(shell.getSize());
	composite.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));

	Canvas canvas = new Canvas(composite, SWT.ALL);
	
	
	canvas.addPaintListener(new PaintListener() {

		@Override
		public void paintControl(PaintEvent e) {
			Transform t=new Transform(display);
			t.rotate(90);
			
			e.gc.setTransform(t);
			e.gc.drawString("ciao", 20, -40);

		
			
			

		}
	});

	composite.pack();
	composite.setLocation(60, 60);
	
	shell.setSize(300, 300);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}

}
