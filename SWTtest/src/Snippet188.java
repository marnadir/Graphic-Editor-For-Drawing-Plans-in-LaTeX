import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
/*
 * Sash example snippet: implement a simple splitter (with a 20 pixel limit)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet188 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	CTabFolder folder = new CTabFolder (shell, SWT.PUSH);
	folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	folder.setSimple(false);
	folder.setUnselectedImageVisible(false);
	folder.setUnselectedCloseVisible(false);

	CTabItem item = new CTabItem(folder, SWT.CLOSE);
	item.setText("Item ");
	Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	text.setText("vaffanculo!!!");
	item.setControl(text);
	
	folder.setMinimizeVisible(true);
	folder.setMaximizeVisible(true);
	folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		@Override
		public void minimize(CTabFolderEvent event) {
			folder.setMinimized(true);
			folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			shell.layout(true);
		}

		@Override
		public void maximize(CTabFolderEvent event) {
			folder.setMaximized(true);
			folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			shell.layout(true);
		}

		@Override
		public void restore(CTabFolderEvent event) {
			folder.setMinimized(false);
			folder.setMaximized(false);
			folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			shell.layout(true);
		}
		@Override
		public void showList(CTabFolderEvent event) {
			// TODO Auto-generated method stub
			super.showList(event);
		}
	});
	
	
	
	
	//button1.setText ("Button 1");
	final Sash sash = new Sash (shell, SWT.VERTICAL);
	CTabFolder folder2 = new CTabFolder (shell, SWT.PUSH);
	folder2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	folder2.setSimple(false);
	folder2.setUnselectedImageVisible(false);
	folder2.setUnselectedCloseVisible(false);

	CTabItem item2 = new CTabItem(folder2, SWT.CLOSE);
	item2.setText("Item ");
	Text text2 = new Text(folder2, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	text2.setText("vaffanculo!!!");
	item2.setControl(text);
	
	folder2.setMinimizeVisible(true);
	folder2.setMaximizeVisible(true);
	folder2.addCTabFolder2Listener(new CTabFolder2Adapter() {
		@Override
		public void minimize(CTabFolderEvent event) {
			folder2.setMinimized(true);
			folder2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			shell.layout(true);
		}

		@Override
		public void maximize(CTabFolderEvent event) {
			folder2.setMaximized(true);
			folder2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			shell.layout(true);
		}

		@Override
		public void restore(CTabFolderEvent event) {
			folder2.setMinimized(false);
			folder2.setMaximized(false);
			folder2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			shell.layout(true);
		}
		@Override
		public void showList(CTabFolderEvent event) {
			// TODO Auto-generated method stub
			super.showList(event);
		}
	});

	final FormLayout form = new FormLayout ();
	shell.setLayout (form);

	FormData button1Data = new FormData ();
	button1Data.left = new FormAttachment (0, 0);
	button1Data.right = new FormAttachment (sash, 0);
	button1Data.top = new FormAttachment (0, 0);
	button1Data.bottom = new FormAttachment (100, 0);
	folder.setLayoutData (button1Data);

	final int limit = 20, percent = 50;
	final FormData sashData = new FormData ();
	sashData.left = new FormAttachment (percent, 0);
	sashData.top = new FormAttachment (0, 0);
	sashData.bottom = new FormAttachment (100, 0);
	sash.setLayoutData (sashData);
	sash.addListener (SWT.Selection, e -> {
		Rectangle sashRect = sash.getBounds ();
		Rectangle shellRect = shell.getClientArea ();
		int right = shellRect.width - sashRect.width - limit;
		e.x = Math.max (Math.min (e.x, right), limit);
		if (e.x != sashRect.x)  {
			sashData.left = new FormAttachment (0, e.x);
			shell.layout ();
		}
	});

	FormData button2Data = new FormData ();
	button2Data.left = new FormAttachment (sash, 0);
	button2Data.right = new FormAttachment (100, 0);
	button2Data.top = new FormAttachment (0, 0);
	button2Data.bottom = new FormAttachment (100, 0);
	folder2.setLayoutData (button2Data);

	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
