package DragDrop;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class Test6 {
	
	  Display display = new Display();
	  Shell shell = new Shell(display);
	  SashForm sashForm;
	  SashForm sashForm2;

	  public Test6() {
	    
	    shell.setLayout(new FillLayout());
	    
	    sashForm = new SashForm(shell, SWT.HORIZONTAL);
	    
	    Group domain=new Group(sashForm, SWT.SCROLL_LINE);
		Font boldFont = new Font( domain.getDisplay(), new FontData( "Arial", 12, SWT.BOLD ) );
		domain.setText("Domain Graph");
		domain.setFont(boldFont);
		FormLayout ownerLayout = new FormLayout();
		ownerLayout.marginWidth = 5;
		ownerLayout.marginHeight = 5;
		domain.setLayout(ownerLayout);
		
		
		Canvas canvas=new Canvas(domain,SWT.ALL);
		canvas.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.drawString("wd", 20, 20);
				
			}
		});
		
		canvas.addDragDetectListener(new DragDetectListener() {
			
			@Override
			public void dragDetected(DragDetectEvent e) {
				e.data=canvas.getClass();
				System.out.print(e.data.toString());
			}
		});
		
		
	
	    
	    sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
	    CTabFolder folder = new CTabFolder (sashForm2, SWT.PUSH);
		folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		folder.setSimple(false);
		folder.setUnselectedImageVisible(false);
		folder.setUnselectedCloseVisible(false);
		

		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Item ");
		Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("vaffanculo!!!");
		item.setControl(text);
		

		Group console=new Group(sashForm2, SWT.SCROLL_LINE);
		console.setText("Console");
		console.setFont(boldFont);
		FormLayout Layout = new FormLayout();
		Layout.marginWidth = 5;
		Layout.marginHeight = 5;
		console.setLayout(Layout);

	    
	    
	    shell.setMaximized(false);
	    shell.open();
	    //textUser.forceFocus();

	    // Set up the event loop.
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        // If no more entries in event queue
	        display.sleep();
	      }
	    }

	    display.dispose();
	  }


	  public static void main(String[] args) {
	    new Test6();
	  }
}
