
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
/*
 * Sash example snippet: implement a simple splitter (with a 20 pixel limit)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class initialPage {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("tool");
	FormLayout layout = new FormLayout();
	layout.marginWidth = 5;
	layout.marginHeight = 5;
	shell.setLayout(layout);
	
	
	Group domain=new Group(shell, SWT.SCROLL_LINE);
	Font boldFont = new Font( domain.getDisplay(), new FontData( "Arial", 12, SWT.BOLD ) );
	domain.setText("Domain Graph");
	domain.setFont(boldFont);
	FormLayout ownerLayout = new FormLayout();
	ownerLayout.marginWidth = 5;
	ownerLayout.marginHeight = 5;
	domain.setLayout(ownerLayout);	
	
	
	CTabFolder folder = new CTabFolder (shell, SWT.PUSH);
	folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	folder.setSimple(false);
	folder.setUnselectedImageVisible(false);
	folder.setUnselectedCloseVisible(false);
	folder.setSize(10,30);

	CTabItem item = new CTabItem(folder, SWT.CLOSE);
	item.setText("Item ");
	Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	text.setText("vaffanculo!!!");
	item.setControl(text);
	

	Group console=new Group(shell, SWT.SCROLL_LINE);
	console.setText("Domain Graph");
	console.setFont(boldFont);
	FormLayout Layout = new FormLayout();
	Layout.marginWidth = 5;
	Layout.marginHeight = 5;
	console.setLayout(Layout);
	
	
	
	
	final Sash sash = new Sash (shell, SWT.VERTICAL);
	
	
	FormData domainData = new FormData ();
	domainData.left = new FormAttachment (0, 0);
	domainData.right = new FormAttachment (sash, 0);
	domainData.top = new FormAttachment (0, 0);
	domainData.bottom = new FormAttachment (100, 0);
	domain.setLayoutData (domainData);

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

	
	final Sash sash2 = new Sash (shell, SWT.HORIZONTAL);

	
	FormData GraphData = new FormData ();
	GraphData.left = new FormAttachment (sash, 0);
	GraphData.right = new FormAttachment (100, 0);
	GraphData.top = new FormAttachment (0, 0);
	GraphData.bottom = new FormAttachment (100, 0);
	folder.setLayoutData (GraphData);
	
	FormData ConsoleData=new FormData();
	ConsoleData.left=new FormAttachment(sash, 0);
	ConsoleData.right=new FormAttachment(0, 0);
	ConsoleData.top=new FormAttachment(folder, 0);
	ConsoleData.bottom=new FormAttachment(100,0);
	console.setLayoutData(ConsoleData);

	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
