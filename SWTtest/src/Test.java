
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


public class Test {



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
		ownerLayout.marginWidth = 45;
		ownerLayout.marginHeight = 45;
		domain.setLayout(ownerLayout);	
		
		
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
		

		Group console=new Group(shell, SWT.SCROLL_LINE);
		console.setText("Console");
		console.setFont(boldFont);
		FormLayout Layout = new FormLayout();
		Layout.marginWidth = 5;
		Layout.marginHeight = 5;
		console.setLayout(Layout);
		
		Sash sash=new Sash(shell, SWT.VERTICAL);
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
		
		
		
		FormData domainData = new FormData ();
		domainData.right = new FormAttachment (sash, -5);
		domainData.left=new FormAttachment(0,0);
		domainData.top = new FormAttachment (0, 0);
		domainData.bottom = new FormAttachment (100, 0);
		domain.setLayoutData (domainData);
	
		SashForm sash2=new SashForm(shell, SWT.HORIZONTAL);
		
		final FormData sashData2 = new FormData ();
		sashData2.left = new FormAttachment (sash, 0);
		sashData2.top = new FormAttachment (folder, 0);
		sashData2.bottom = new FormAttachment (console, 0);
		sash2.setLayoutData (sashData2);
		
		sash2.addListener (SWT.Selection, e -> {
			Rectangle sashRect = sash2.getBounds ();
			Rectangle shellRect = shell.getClientArea ();
			int right = shellRect.width - sashRect.width - limit;
			e.x = Math.max (Math.min (e.x, right), limit);
			if (e.x != sashRect.x)  {
				sashData2.left = new FormAttachment (0, e.x);
				shell.layout ();
			}
		});
		
		
		
		FormData GraphData = new FormData ();
		GraphData.left = new FormAttachment (sash, 0);
		GraphData.right = new FormAttachment (100, 0);
		GraphData.bottom = new FormAttachment (console, 0);
		folder.setLayoutData (GraphData);
		
		FormData ConsoleData=new FormData();
		ConsoleData.left=new FormAttachment(sash, 0);
		ConsoleData.top=new FormAttachment(folder, 0);
		ConsoleData.bottom=new FormAttachment(100,0);
		ConsoleData.right=new FormAttachment(100,0);
		console.setLayoutData(ConsoleData);

		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
	
}
