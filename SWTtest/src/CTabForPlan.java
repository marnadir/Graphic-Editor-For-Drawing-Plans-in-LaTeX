
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class CTabForPlan{

	public static void main(String[] args) {
		Display display = new Display();

		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		
		final CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		folder.setSimple(false);
		folder.setUnselectedImageVisible(false);
		folder.setUnselectedCloseVisible(false);

		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Item ");
		Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("vaffanculo!!!");
		item.setControl(text);
		
		
		
		
//		ToolBar toolBar = new ToolBar (folder, SWT.FLAT | SWT.BORDER);
//		toolBar.setLocation (folder.getLocation());
//		toolBar.setBackground(display.getSystemColor (SWT.COLOR_RED));
//	
//		toolBar.pack ();


		folder.setMinimizeVisible(true);
		folder.setMaximizeVisible(true);
		

		ToolBar t = new ToolBar( folder, SWT.FLAT ); 
		ToolItem i = new ToolItem( t, SWT.PUSH ); 
		i.setToolTipText("add a new Plan");
	    Image icon = new Image(shell.getDisplay(), "add-documents.png");
		i.setImage(icon); 
		i.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent var1) {

				CTabItem item2 = new CTabItem(folder, SWT.CLOSE);
				item2.setText("Item 4 ");
				Text text2 = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
				text2.setText("vaffanculo!!!");
				item2.setControl(text2);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent var1) {
				// TODO Auto-generated method stub
				
			}
		});
		folder.setTopRight( t, SWT.RIGHT ); 
		
		folder.setTabHeight(Math.max(t.computeSize(SWT.DEFAULT, 
				SWT.DEFAULT).y, folder.getTabHeight())); 
		
		
	
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
		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}