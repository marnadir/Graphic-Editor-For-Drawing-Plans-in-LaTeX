package DragDrop;
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import javax.swing.*;

import org.eclipse.swt.*;
import org.eclipse.swt.awt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class DragDrop1 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("SWT and Swing DND Example");
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);

		Text swtText = new Text(shell, SWT.BORDER);
		swtText.setText("SWT Text");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		swtText.setLayoutData(data);
		setDragDrop(swtText);

		Composite comp = new Composite(shell, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(comp);
		JTextField swingText = new JTextField(40);
		swingText.setText("moved text");
		swingText.setDragEnabled(true);
		frame.add(swingText);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = swingText.getPreferredSize().height;
		comp.setLayoutData(data);

		shell.setSize(400, 200);
		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	public static void setDragDrop (final Text text) {
		Transfer[] types = new Transfer[] {FileTransfer.getInstance()};
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final DragSource source = new DragSource (text, operations);
		source.setTransfer(types);
		source.addDragListener (new DragSourceListener () {
			Point selection;
			@Override
			public void dragStart(DragSourceEvent e) {
				selection = text.getSelection();
				e.doit = selection.x != selection.y;
			}
			@Override
			public void dragSetData(DragSourceEvent e) {
				e.data = text.getText(selection.x, selection.y-1);
			}
			@Override
			public void dragFinished(DragSourceEvent e) {
				if (e.detail == DND.DROP_MOVE) {
					text.setSelection(selection);
					text.insert("");
				}
				selection = null;
			}
		});

		DropTarget target = new DropTarget(text, operations);
		target.setTransfer(types);
		target.addDropListener (new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				text.append((String) event.data);
			}
		});
	}
}