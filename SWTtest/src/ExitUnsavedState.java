import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ExitUnsavedState {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText ("Text Editor");
		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
		fileItem.setText ("&File");
		Menu fileMenu = new Menu (shell, SWT.DROP_DOWN);
		fileItem.setMenu (fileMenu);
		MenuItem saveItem = new MenuItem (fileMenu, SWT.PUSH);
		saveItem.setText ("&Save\tCtrl+S");
		saveItem.setAccelerator (SWT.MOD1 + 'S');
		saveItem.addListener (SWT.Selection, e -> shell.setModified (false));
		MenuItem exitItem =  new MenuItem (fileMenu, SWT.PUSH);
		exitItem.setText ("Exit");
		
		
		exitItem.addListener (SWT.Selection, e -> shell.close ());
		Text text = new Text (shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		text.addListener (SWT.Modify, e -> shell.setModified (true));
		shell.addListener (SWT.Close, e -> {
			if (shell.getModified()) {
				MessageBox box = new MessageBox (shell, SWT.PRIMARY_MODAL | SWT.OK | SWT.CANCEL);
				box.setText (shell.getText ());
				box.setMessage ("You have unsaved changes, do you want to exit?");
				e.doit = box.open () == SWT.OK;
			}
		});
		
		
		shell.setLayout (new FillLayout());
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}
	

}
