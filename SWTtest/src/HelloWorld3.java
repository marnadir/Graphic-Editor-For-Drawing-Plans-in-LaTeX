import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;


public class HelloWorld3 {
	  

	  public static void main(String[] args) {
	    Display display = new Display();
	    Shell shell = new HelloWorld3().open(display);
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	  }

	  public Shell open(Display display) {
	    final Shell shell = new Shell(display);
	    final Label label = new Label(shell, SWT.CENTER);
	    label.setText("Hello_world");
	    label.pack();
	    shell.addControlListener(new ControlAdapter() {
	      public void controlResized(ControlEvent e) {
	        label.setBounds(shell.getClientArea());
	      }
	    });
	    shell.pack();
	    shell.open();
	    return shell;
	  }
	}


