package DragDrop;

/*
 * Drag and Drop example snippet: drag text between two labels
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class DragDrop4 {

public static void main (String [] args) {

	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	final Label label1 = new Label (shell, SWT.BORDER);
	label1.setText ("TEXT");
	final Label label2 = new Label (shell, SWT.BORDER);
	setDragDrop (label1);
	setDragDrop (label2);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
public static void setDragDrop (final Label label) {

	Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

	final DragSource source = new DragSource (label, operations);
	source.setTransfer(types);
	source.addDragListener (new DragSourceListener () {
		@Override
		public void dragStart(DragSourceEvent event) {
			event.doit = (label.getText ().length () != 0);
		}
		@Override
		public void dragSetData (DragSourceEvent event) {
			event.data = label.getText ();
		}
		@Override
		public void dragFinished(DragSourceEvent event) {
			if (event.detail == DND.DROP_COPY)
				label.setText ("");
		}
	});

	DropTarget target = new DropTarget(label, operations);
	target.setTransfer(types);
	target.addDropListener (new DropTargetAdapter() {
		@Override
		public void drop(DropTargetEvent event) {
			if (event.data == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			label.setText ((String) event.data);
		}
	});
}
}