import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


public class Snippet188 {

public static void main(String[] args) {
	Display display=new Display();
	Shell shell1=new Shell(display);
	shell1.setText("wlqewòh");
	Shell shell2=new  Shell(display);
	
	shell1.open();
	while(!shell1.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	
	shell2.open();
	while(!shell2.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
