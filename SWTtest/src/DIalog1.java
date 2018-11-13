
/*
 * FormLayout example snippet: create a simple OK/CANCEL dialog using form layout
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class DIalog1 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.pack ();
	shell.open ();
	Shell dialog = new Shell (shell, SWT.DIALOG_TRIM);
	Label label = new Label (dialog, SWT.NONE);
	label.setText ("Exit the application?");
	Button okButton = new Button (dialog, SWT.PUSH);
	okButton.setText ("&OK");
	Button cancelButton = new Button (dialog, SWT.PUSH);
	cancelButton.setText ("&Cancel");

	FormLayout form = new FormLayout ();
	form.marginWidth = form.marginHeight = 8;
	dialog.setLayout (form);
	FormData okData = new FormData ();
	okData.top = new FormAttachment (label, 8);
	okButton.setLayoutData (okData);
	FormData cancelData = new FormData ();
	cancelData.left = new FormAttachment (okButton, 8);
	cancelData.top = new FormAttachment (okButton, 0, SWT.TOP);
	cancelButton.setLayoutData (cancelData);

	dialog.setDefaultButton (okButton);
	dialog.pack ();
	dialog.open ();

	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
